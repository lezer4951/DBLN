package com.dubalin.app.presentation.ui.study

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.Cosmetic
import com.dubalin.app.data.local.LearningStore
import com.dubalin.app.data.local.dao.ProgresoMateriaDao
import com.dubalin.app.data.local.dao.ProgresoNivelDao
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.ShortLessons
import com.dubalin.app.domain.model.normalizedAnswer
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class LessonScreen(val page: Int = 0, val feedback: String = "", val correct: Boolean = false,
    val matched: Set<Int> = emptySet(), val busy: Boolean = false, val ready: Boolean = false)

@HiltViewModel
class LearningViewModel @Inject constructor(
    val store: LearningStore, session: SessionRepository, private val saved: SavedStateHandle,
    private val materiaDao: ProgresoMateriaDao, private val nivelDao: ProgresoNivelDao
) : ViewModel() {
    val user = session.getUsuarioId() ?: 0
    val subject = runCatching { MateriaId.valueOf(saved.get<String>("subject") ?: "ASTRONOMIA") }.getOrDefault(MateriaId.ASTRONOMIA)
    val lesson = saved.get<String>("lesson")?.let(ShortLessons::find)
    private val mutable = MutableStateFlow(LessonScreen())
    val state = mutable.asStateFlow()
    init {
        viewModelScope.launch {
            runCatching {
                check(user > 0) { "Inicia sesión para guardar tu avance." }
                withContext(Dispatchers.IO) {
                    val old = materiaDao.get(user, "ASTRONOMIA")
                    val imported = mutableSetOf<String>()
                    ShortLessons.forSubject(MateriaId.ASTRONOMIA).groupBy { it.level }.forEach { (level, lessons) ->
                        val mask = if (level == 0) old?.temasCompletados ?: 0 else nivelDao.get(user, "ASTRONOMIA", level)?.temasCompletados ?: 0
                        lessons.forEachIndexed { index, l ->
                            if ((mask and (1 shl index)) != 0 || (old?.nivelActual ?: 0) > level) imported += l.id
                        }
                    }
                    store.importLessons(user, imported)
                }
            }.onSuccess {
                val page = saved.get<Int>("page") ?: lesson?.let { store.draft(user, it.id) } ?: 0
                mutable.value = LessonScreen(page.coerceIn(0, 6), saved["feedback"] ?: "", saved["correct"] ?: false,
                    saved.get<IntArray>("matched")?.toSet().orEmpty(), ready = true)
            }.onFailure { mutable.value = LessonScreen(feedback = it.message ?: "No se pudo cargar el avance.") }
        }
    }
    private fun publish(s: LessonScreen) {
        saved["page"] = s.page; saved["feedback"] = s.feedback; saved["correct"] = s.correct
        saved["matched"] = s.matched.toIntArray(); mutable.value = s
    }
    fun verify(text: String, choice: Int?) {
        val l = lesson ?: return
        val s = state.value
        if (s.busy || !s.ready || s.correct) return
        val answer: String
        val ok: Boolean
        when (l.activityAt(s.page)) {
            2 -> { answer = l.sentence; ok = normalizedAnswer(text) == normalizedAnswer(l.missingWord) }
            4 -> if (l.spoken != null) {
                answer = l.spoken; ok = normalizedAnswer(text) == normalizedAnswer(l.spoken)
            } else {
                val q = l.questions.first(); answer = q.explanation; ok = choice == q.correct
            }
            5 -> { val q = l.questions.last(); answer = q.explanation; ok = choice == q.correct }
            else -> return
        }
        publish(s.copy(correct = ok, feedback = if (ok) "Correcto. $answer" else "Revisa y vuelve a intentarlo. $answer"))
    }
    fun match(left: Int, right: Int) {
        val s = state.value
        if (lesson?.activityAt(s.page) != 3 || !s.ready || s.busy) return
        if (left != right) publish(s.copy(feedback = "Esa relación no corresponde. Revisa las definiciones."))
        else {
            val matched = s.matched + left
            publish(s.copy(matched = matched, correct = matched.size == lesson?.pairs?.size,
                feedback = if (matched.size == lesson?.pairs?.size) "Relaciones correctas." else "Correcto. Relaciona el siguiente concepto."))
        }
    }
    fun advance() {
        val l = lesson ?: return
        val s = state.value
        if (!s.ready || s.busy || s.page >= 6 || (s.page >= 2 && !s.correct)) return
        publish(s.copy(busy = true))
        viewModelScope.launch {
            runCatching { withContext(Dispatchers.IO) {
                if (s.page == 5) store.complete(user, l.id) else store.saveStep(user, l.id, s.page + 1)
            } }.onSuccess { publish(LessonScreen(page = s.page + 1, ready = true)) }
                .onFailure { publish(s.copy(feedback = it.message ?: "No se pudo guardar. Inténtalo de nuevo.")) }
        }
    }
    fun buy(item: Cosmetic) {
        if (state.value.busy || !state.value.ready) return
        publish(state.value.copy(busy = true))
        viewModelScope.launch {
            runCatching { withContext(Dispatchers.IO) { store.purchaseOrEquip(user, item) } }
                .onSuccess { publish(state.value.copy(busy = false, feedback = "${item.name} equipado.")) }
                .onFailure { publish(state.value.copy(busy = false, feedback = it.message.orEmpty())) }
        }
    }
}
