package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.ViewModel
import com.dubalin.app.domain.model.mezclarAutoevaluacion
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.NivelUnoAstronomiaContenido
import com.dubalin.app.domain.model.PreguntaAutoevaluacion
import com.dubalin.app.domain.model.SesionAstronomia
import com.dubalin.app.domain.repository.NivelAstronomiaRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AstronomiaNivelUnoUiState(
    val sesiones: List<SesionAstronomia> = NivelUnoAstronomiaContenido.sesiones.map { it.mezclarAutoevaluacion() },
    val temaActual: Int = 0,
    val temasCompletados: Int = 0,
    val practicaCompletada: Boolean = false,
    val fase: FaseSesion = FaseSesion.CONTENIDO,
    val preguntaActual: Int = 0,
    val opcionSeleccionada: Int? = null,
    val respuestaCorrecta: Boolean? = null,
    val explicacionPropia: String = "",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val temasRepaso: List<Int> = emptyList(),
    val indiceRepaso: Int = 0,
    val temaCompletadoReciente: Boolean = false,
    val error: Boolean = false
) {
    val sesion get() = sesiones[temaActual]
    val pregunta: PreguntaAutoevaluacion? get() = sesion.autoevaluacion.getOrNull(preguntaActual)
    val temaCompletado get() = NivelUnoAstronomiaContenido.completado(temasCompletados, temaActual)
    val enRepaso get() = temasRepaso.isNotEmpty()
    val ultimoRepaso get() = enRepaso && indiceRepaso == temasRepaso.lastIndex
}

@HiltViewModel
class AstronomiaNivelUnoViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val repository: NivelAstronomiaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AstronomiaNivelUnoUiState())
    val uiState: StateFlow<AstronomiaNivelUnoUiState> = _uiState.asStateFlow()

    init { cargar() }

    fun iniciarAutoevaluacion() = _uiState.update {
        it.copy(fase = FaseSesion.AUTOEVALUACION, preguntaActual = 0, opcionSeleccionada = null,
            respuestaCorrecta = null, explicacionPropia = "")
    }
    fun seleccionarOpcion(indice: Int) {
        if (_uiState.value.respuestaCorrecta == null) _uiState.update { it.copy(opcionSeleccionada = indice) }
    }
    fun actualizarExplicacion(texto: String) = _uiState.update { it.copy(explicacionPropia = texto) }
    fun accionAutoevaluacion() {
        val state = _uiState.value
        val pregunta = state.pregunta ?: return
        if (state.respuestaCorrecta == null) {
            val seleccion = state.opcionSeleccionada ?: return
            _uiState.update { it.copy(respuestaCorrecta = seleccion == pregunta.respuestaCorrecta) }
        } else if (state.respuestaCorrecta == false) {
            _uiState.update { it.copy(opcionSeleccionada = null, respuestaCorrecta = null) }
        } else if (state.preguntaActual < state.sesion.autoevaluacion.lastIndex) {
            _uiState.update { it.copy(preguntaActual = it.preguntaActual + 1, opcionSeleccionada = null,
                respuestaCorrecta = null, explicacionPropia = "") }
        } else if (state.explicacionPropia.trim().length >= 12) {
            guardarTema()
        }
    }
    fun anterior() = _uiState.update { state ->
        if (state.fase == FaseSesion.AUTOEVALUACION) state.copy(fase = FaseSesion.CONTENIDO,
            opcionSeleccionada = null, respuestaCorrecta = null, explicacionPropia = "")
        else state.copy(temaActual = (state.temaActual - 1).coerceAtLeast(0))
    }
    fun siguiente() = _uiState.update { state ->
        state.copy(temaActual = (state.temaActual + 1).coerceAtMost(state.sesiones.lastIndex),
            fase = FaseSesion.CONTENIDO, preguntaActual = 0, opcionSeleccionada = null,
            respuestaCorrecta = null, explicacionPropia = "", temaCompletadoReciente = false)
    }
    fun iniciarRepaso(temas: List<Int>) {
        val validos = temas.distinct().filter { it in _uiState.value.sesiones.indices }
        if (validos.isEmpty()) return
        _uiState.update { it.copy(temaActual = validos.first(), temasRepaso = validos, indiceRepaso = 0,
            fase = FaseSesion.CONTENIDO, explicacionPropia = "") }
    }
    fun siguienteRepaso() = _uiState.update { state ->
        val next = (state.indiceRepaso + 1).coerceAtMost(state.temasRepaso.lastIndex)
        state.copy(indiceRepaso = next, temaActual = state.temasRepaso[next], fase = FaseSesion.CONTENIDO)
    }
    fun consumirEvento() = _uiState.update { it.copy(temaCompletadoReciente = false) }
    fun errorMostrado() = _uiState.update { it.copy(error = false) }

    private fun cargar() {
        val user = sessionRepository.getUsuarioId()
        if (user == null) { _uiState.update { it.copy(isLoading = false, error = true) }; return }
        repository.observarNivel(user, 1).onEach { progreso ->
            _uiState.update { state ->
                val pendiente = state.sesiones.indices.firstOrNull {
                    !NivelUnoAstronomiaContenido.completado(progreso.temasCompletados, it)
                } ?: state.sesiones.lastIndex
                state.copy(
                    temaActual = if (state.isLoading) pendiente else state.temaActual,
                    temasCompletados = progreso.temasCompletados,
                    practicaCompletada = progreso.practicaCompletada,
                    isLoading = false
                )
            }
        }.catch {
            _uiState.update { it.copy(isLoading = false, error = true) }
        }.launchIn(viewModelScope)
    }

    private fun guardarTema() {
        val user = sessionRepository.getUsuarioId() ?: return
        val tema = _uiState.value.temaActual
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            repository.completarTema(user, 1, tema).onSuccess {
                _uiState.update { state -> state.copy(temasCompletados = state.temasCompletados or (1 shl tema),
                    fase = FaseSesion.CONTENIDO, isSaving = false, temaCompletadoReciente = true,
                    opcionSeleccionada = null, respuestaCorrecta = null, explicacionPropia = "") }
            }.onFailure { _uiState.update { it.copy(isSaving = false, error = true) } }
        }
    }
}
