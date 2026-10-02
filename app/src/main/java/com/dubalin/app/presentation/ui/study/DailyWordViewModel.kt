package com.dubalin.app.presentation.ui.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.LearningStore
import com.dubalin.app.domain.model.DailyWord
import com.dubalin.app.domain.model.normalizedAnswer
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class DailyWordState(val day: String, val guesses: List<String> = emptyList(), val message: String = "", val busy: Boolean = false) {
    val won get() = guesses.lastOrNull() == DailyWord.answer(day)
    val finished get() = won || guesses.size >= 6
}
@HiltViewModel
class DailyWordViewModel @Inject constructor(private val store: LearningStore, session: SessionRepository) : ViewModel() {
    private val user = session.getUsuarioId() ?: 0
    private val mutable = MutableStateFlow(DailyWordState(store.today(), store.wordGuesses(user, store.today())))
    val state = mutable.asStateFlow()
    init { if(state.value.won) reward() }
    private fun reward() {
        viewModelScope.launch { runCatching { withContext(Dispatchers.IO) { store.rewardGame(user, "wordle", 10, state.value.day) } }
            .onSuccess { mutable.value = state.value.copy(message = "Palabra resuelta · recompensa diaria: 10 monedas para la tienda.", busy = false) }
            .onFailure { mutable.value = state.value.copy(message = "No se pudo guardar la recompensa. Vuelve a abrir el juego para reintentar.", busy = false) }
        }
    }
    fun guess(input: String) {
        var s = state.value
        if(s.busy) return
        if(s.day != store.today()) {
            s = DailyWordState(store.today(), store.wordGuesses(user, store.today()), "Ya está disponible una nueva palabra.")
            mutable.value = s
        }
        if(s.finished) return
        val guess = normalizedAnswer(input).uppercase(java.util.Locale.ROOT)
        if(!guess.matches(Regex("[A-Z]{5}"))) { mutable.value = s.copy(message = "Escribe cinco letras, sin espacios. No se usan tildes."); return }
        mutable.value = s.copy(busy = true, message = "")
        viewModelScope.launch {
            runCatching { withContext(Dispatchers.IO) { store.saveWordGuess(user, s.day, guess) } }
                .onSuccess {
                    val next = s.copy(guesses = s.guesses + guess)
                    mutable.value = next.copy(busy = next.won, message = if(next.finished && !next.won) "La palabra era ${DailyWord.answer(s.day)}. Mañana habrá otra." else "")
                    if(next.won) reward()
                }.onFailure { mutable.value = s.copy(message = it.message ?: "No se pudo guardar el intento.") }
        }
    }
}
