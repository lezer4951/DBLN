package com.dubalin.app.presentation.ui.autoestudio.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.repository.FlashcardsRepository
import com.dubalin.app.domain.repository.SessionRepository
import com.dubalin.app.domain.model.Mazo
import com.dubalin.app.domain.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FlashcardsState(
    val mazos: List<Mazo> = emptyList(), val mazo: Mazo? = null,
    val tarjetas: List<Flashcard> = emptyList(), val loading: Boolean = true,
    val saving: Boolean = false, val error: String? = null,
    val estudio: List<Flashcard>? = null, val indice: Int = 0,
    val revelada: Boolean = false, val invertido: Boolean = false, val aciertos: Int = 0
)

@HiltViewModel
class FlashcardsViewModel @Inject constructor(
    private val repository: FlashcardsRepository, private val session: SessionRepository
) : ViewModel() {
    private val state = MutableStateFlow(FlashcardsState())
    val uiState = state.asStateFlow()
    private var tarjetasJob: Job? = null
    private fun usuario() = requireNotNull(session.getUsuarioId()) { "Inicia sesión nuevamente." }

    init { cargar() }
    fun cargar() {
        viewModelScope.launch {
            try {
                repository.mazos(usuario()).collect { rows -> state.update { it.copy(mazos = rows, loading = false) } }
            } catch (e: CancellationException) { throw e }
            catch (e: Exception) { state.update { it.copy(loading = false, error = "No se pudieron cargar los mazos.") } }
        }
    }
    fun abrir(mazo: Mazo) {
        tarjetasJob?.cancel()
        state.update { it.copy(mazo = mazo, tarjetas = emptyList(), loading = true, estudio = null) }
        tarjetasJob = viewModelScope.launch {
            try {
                repository.tarjetas(mazo.id).collect { rows -> state.update { it.copy(tarjetas = rows, loading = false) } }
            } catch (e: CancellationException) { throw e }
            catch (e: Exception) { state.update { it.copy(loading = false, error = "No se pudieron cargar las tarjetas.") } }
        }
    }
    fun volver() {
        if (state.value.estudio != null) state.update { it.copy(estudio = null) }
        else { tarjetasJob?.cancel(); state.update { it.copy(mazo = null, tarjetas = emptyList(), loading = false) } }
    }
    private fun operar(block: suspend () -> Unit) {
        if (state.value.saving) return
        state.update { it.copy(saving = true) }
        viewModelScope.launch {
            try { block() }
            catch (e: CancellationException) { throw e }
            catch (e: Exception) { state.update { it.copy(error = if (e is IllegalArgumentException) e.message else "No se pudo guardar el cambio. Inténtalo nuevamente.") } }
            finally { state.update { it.copy(saving = false) } }
        }
    }
    fun guardarMazo(mazo: Mazo?, titulo: String, descripcion: String) = operar {
        repository.guardarMazo(usuario(), mazo?.id ?: 0, titulo, descripcion)
    }
    fun borrarMazo(mazo: Mazo) = operar { repository.borrarMazo(usuario(), mazo.id) }
    fun guardarTarjeta(tarjeta: Flashcard?, frente: String, reverso: String) {
        val mazo = state.value.mazo ?: return
        operar { repository.guardarTarjeta(usuario(), mazo.id, tarjeta?.id ?: 0, frente, reverso) }
    }
    fun borrarTarjeta(tarjeta: Flashcard) = operar { repository.borrarTarjeta(usuario(), tarjeta) }
    fun estudiar(invertido: Boolean) {
        if (state.value.tarjetas.isEmpty()) return
        state.update { it.copy(estudio = it.tarjetas.shuffled(), indice = 0, revelada = false, invertido = invertido, aciertos = 0) }
    }
    fun revelar() { state.update { it.copy(revelada = true) } }
    fun responder(acierto: Boolean) {
        val s = state.value
        if (!s.revelada || s.indice >= (s.estudio?.size ?: 0)) return
        state.update { it.copy(indice = it.indice + 1, revelada = false, aciertos = it.aciertos + if (acierto) 1 else 0) }
    }
    fun errorMostrado() { state.update { it.copy(error = null) } }
}
