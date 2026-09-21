package com.dubalin.app.presentation.ui.desafios

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.repository.FlashcardsRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class MemoramaState(val textos: List<String> = emptyList(), val parejas: List<Int> = emptyList(),
    val visibles: List<Int> = emptyList(), val encontradas: Set<Int> = emptySet(), val intentos: Int = 0,
    val cargando: Boolean = false, val error: Boolean = false) {
    val completo get() = encontradas.size == 4
}

@HiltViewModel
class MemoramaViewModel @Inject constructor(
    private val repo: FlashcardsRepository, private val session: SessionRepository,
    private val saved: SavedStateHandle
) : ViewModel() {
    private val mutable = MutableStateFlow(MemoramaState(
        textos = saved.get<ArrayList<String>>("textos").orEmpty(),
        parejas = saved.get<IntArray>("parejas")?.toList().orEmpty(),
        visibles = saved.get<IntArray>("visibles")?.toList().orEmpty(),
        encontradas = saved.get<IntArray>("encontradas")?.toSet().orEmpty(),
        intentos = saved["intentos"] ?: 0
    ))
    val state = mutable.asStateFlow()
    init { if (state.value.textos.isEmpty()) cargar() }
    private fun publicar(s: MemoramaState) {
        saved["textos"] = ArrayList(s.textos)
        saved["parejas"] = s.parejas.toIntArray()
        saved["visibles"] = s.visibles.toIntArray()
        saved["encontradas"] = s.encontradas.toIntArray()
        saved["intentos"] = s.intentos
        mutable.value = s
    }
    fun cargar() {
        if (state.value.cargando) return
        publicar(MemoramaState(cargando = true))
        viewModelScope.launch {
            try {
                val user = requireNotNull(session.getUsuarioId())
                val tarjetas = mutableListOf<Pair<String, String>>()
                for (mazo in repo.mazos(user).first()) {
                    tarjetas += repo.tarjetas(mazo.id).first().take(4 - tarjetas.size).map { it.frente to it.reverso }
                    if (tarjetas.size == 4) break
                }
                if (tarjetas.size < 4) { publicar(MemoramaState()); return@launch }
                val cartas = tarjetas.flatMapIndexed { index, par -> listOf(index to par.first, index to par.second) }.shuffled()
                publicar(MemoramaState(textos = cartas.map { it.second }, parejas = cartas.map { it.first }))
            } catch (e: CancellationException) { throw e }
            catch (e: Exception) { publicar(MemoramaState(error = true)) }
        }
    }
    fun elegir(indice: Int) {
        val s = state.value
        if (indice !in s.textos.indices || s.visibles.size == 2 || indice in s.visibles || s.parejas[indice] in s.encontradas) return
        val visibles = s.visibles + indice
        val coincide = visibles.size == 2 && s.parejas[visibles[0]] == s.parejas[visibles[1]]
        publicar(s.copy(visibles = visibles, intentos = s.intentos + if (visibles.size == 2) 1 else 0,
            encontradas = if (coincide) s.encontradas + s.parejas[indice] else s.encontradas))
    }
    fun continuar() { if (state.value.visibles.size == 2) publicar(state.value.copy(visibles = emptyList())) }
}
