package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.ViewModel
import com.dubalin.app.domain.model.mezclarAutoevaluacion
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.FormatoAprendizaje
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.NivelCeroAstronomiaContenido
import com.dubalin.app.domain.model.PreguntaAutoevaluacion
import com.dubalin.app.domain.model.SesionAstronomia
import com.dubalin.app.domain.repository.ProgresoAcademicoRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class FaseSesion { CONTENIDO, AUTOEVALUACION }

data class AstronomiaNivelCeroUiState(
    val sesiones: List<SesionAstronomia> = NivelCeroAstronomiaContenido.sesiones.map { it.mezclarAutoevaluacion() },
    val temaActual: Int = 0,
    val temasCompletados: Int = 0,
    val formato: FormatoAprendizaje = FormatoAprendizaje.EQUILIBRADO,
    val fase: FaseSesion = FaseSesion.CONTENIDO,
    val preguntaActual: Int = 0,
    val opcionSeleccionada: Int? = null,
    val respuestaCorrecta: Boolean? = null,
    val explicacionPropia: String = "",
    val isSaving: Boolean = false,
    val temasRepaso: List<Int> = emptyList(),
    val indiceRepaso: Int = 0,
    val temaCompletadoReciente: Boolean = false,
    val isLoading: Boolean = true,
    val error: Boolean = false
) {
    val sesionActual get() = sesiones[temaActual]
    val pregunta: PreguntaAutoevaluacion? get() = sesionActual.autoevaluacion.getOrNull(preguntaActual)
    val temaActualCompletado get() = NivelCeroAstronomiaContenido.temaCompletado(temasCompletados, temaActual)
    val todosCompletados get() = NivelCeroAstronomiaContenido.todosLosTemasCompletados(temasCompletados)
    val enRepaso get() = temasRepaso.isNotEmpty()
    val ultimoTemaDeRepaso get() = enRepaso && indiceRepaso == temasRepaso.lastIndex
}

@HiltViewModel
class AstronomiaNivelCeroViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val progresoRepository: ProgresoAcademicoRepository,
    private val borradores: BorradorSesionStore? = null
) : ViewModel() {
    private val _uiState = MutableStateFlow(AstronomiaNivelCeroUiState())
    val uiState: StateFlow<AstronomiaNivelCeroUiState> = _uiState.asStateFlow()

    override fun onCleared() {
        val current = _uiState.value
        if (!current.isLoading && !current.error && !current.enRepaso) borradores?.guardar(
            sessionRepository.getUsuarioId(), 0, current.temaActual, current.fase,
            current.preguntaActual, current.opcionSeleccionada, current.respuestaCorrecta, current.explicacionPropia)
        super.onCleared()
    }

    init {
        val draft = borradores?.leer(sessionRepository.getUsuarioId(), 0)
        if (draft != null) _uiState.update { it.copy(
            temaActual = draft.optInt("tema").coerceIn(it.sesiones.indices),
            fase = runCatching { FaseSesion.valueOf(draft.optString("fase")) }.getOrDefault(FaseSesion.CONTENIDO),
            preguntaActual = draft.optInt("pregunta").coerceAtLeast(0),
            opcionSeleccionada = draft.optInt("seleccion", -1).takeIf { value -> value in 0..2 },
            respuestaCorrecta = draft.optString("correcta").toBooleanStrictOrNull(),
            explicacionPropia = draft.optString("explicacion")
        ) }
        cargarProgreso()
        viewModelScope.launch {
            uiState.collect { state ->
                if (!state.isLoading && !state.error && !state.enRepaso) borradores?.guardar(
                    sessionRepository.getUsuarioId(), 0, state.temaActual, state.fase,
                    state.preguntaActual, state.opcionSeleccionada, state.respuestaCorrecta, state.explicacionPropia)
            }
        }
    }

    fun iniciarAutoevaluacion() = _uiState.update {
        it.copy(fase = FaseSesion.AUTOEVALUACION, preguntaActual = 0, opcionSeleccionada = null,
            respuestaCorrecta = null, explicacionPropia = "")
    }

    fun seleccionarOpcion(indice: Int) {
        if (_uiState.value.respuestaCorrecta == null) {
            _uiState.update { it.copy(opcionSeleccionada = indice) }
        }
    }

    fun actualizarExplicacionPropia(texto: String) = _uiState.update {
        it.copy(explicacionPropia = texto)
    }

    fun comprobarOContinuar() {
        val state = _uiState.value
        if (state.isSaving) return
        val pregunta = state.pregunta ?: return
        if (state.respuestaCorrecta == null) {
            val seleccion = state.opcionSeleccionada ?: return
            _uiState.update { it.copy(respuestaCorrecta = seleccion == pregunta.respuestaCorrecta) }
            return
        }
        if (state.respuestaCorrecta == false) {
            _uiState.update { it.copy(opcionSeleccionada = null, respuestaCorrecta = null) }
            return
        }
        if (state.preguntaActual < state.sesionActual.autoevaluacion.lastIndex) {
            _uiState.update {
                it.copy(preguntaActual = it.preguntaActual + 1, opcionSeleccionada = null,
                    respuestaCorrecta = null, explicacionPropia = "")
            }
        } else {
            if (state.explicacionPropia.trim().length < 12) return
            guardarTemaDominado()
        }
    }

    fun paginaAnterior() = _uiState.update { state ->
        if (state.fase == FaseSesion.AUTOEVALUACION) {
            state.copy(fase = FaseSesion.CONTENIDO, preguntaActual = 0, opcionSeleccionada = null,
                respuestaCorrecta = null, explicacionPropia = "")
        } else {
            state.copy(temaActual = (state.temaActual - 1).coerceAtLeast(0))
        }
    }

    fun paginaSiguiente() {
        val next = (_uiState.value.temaActual + 1).coerceAtMost(_uiState.value.sesiones.lastIndex)
        _uiState.update {
            it.copy(temaActual = next, fase = FaseSesion.CONTENIDO, preguntaActual = 0,
                opcionSeleccionada = null, respuestaCorrecta = null, explicacionPropia = "",
                temaCompletadoReciente = false)
        }
    }

    fun iniciarRepaso(indices: List<Int>) {
        val validos = indices.distinct().filter { it in _uiState.value.sesiones.indices }
        if (validos.isEmpty()) return
        _uiState.update {
            it.copy(temaActual = validos.first(), temasRepaso = validos, indiceRepaso = 0,
                fase = FaseSesion.CONTENIDO, preguntaActual = 0, opcionSeleccionada = null,
                respuestaCorrecta = null, explicacionPropia = "")
        }
    }

    fun avanzarRepaso() = _uiState.update { state ->
        val siguiente = (state.indiceRepaso + 1).coerceAtMost(state.temasRepaso.lastIndex)
        state.copy(temaActual = state.temasRepaso[siguiente], indiceRepaso = siguiente, fase = FaseSesion.CONTENIDO)
    }

    fun consumirTemaCompletado() = _uiState.update { it.copy(temaCompletadoReciente = false) }
    fun errorMostrado() = _uiState.update { it.copy(error = false) }

    private fun cargarProgreso() {
        val usuarioId = sessionRepository.getUsuarioId()
        if (usuarioId == null) {
            _uiState.update { it.copy(isLoading = false, error = true) }
            return
        }
        viewModelScope.launch {
            runCatching { progresoRepository.observarProgreso(usuarioId, MateriaId.ASTRONOMIA).first() }
                .onSuccess { progreso ->
                    val firstPending = NivelCeroAstronomiaContenido.sesiones.indices.firstOrNull {
                        !NivelCeroAstronomiaContenido.temaCompletado(progreso.temasCompletados, it)
                    } ?: NivelCeroAstronomiaContenido.sesiones.lastIndex
                    _uiState.update {
                        it.copy(temaActual = if (borradores?.leer(usuarioId, 0) == null) firstPending else it.temaActual, temasCompletados = progreso.temasCompletados,
                            formato = progreso.formatoAprendizaje, isLoading = false)
                    }
                }.onFailure { _uiState.update { it.copy(isLoading = false, error = true) } }
        }
    }

    private fun guardarTemaDominado() {
        val usuarioId = sessionRepository.getUsuarioId() ?: return
        val indice = _uiState.value.temaActual
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            progresoRepository.completarTemaNivelCero(usuarioId, indice)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            temasCompletados = NivelCeroAstronomiaContenido.completarTema(it.temasCompletados, indice),
                            fase = FaseSesion.CONTENIDO,
                            isSaving = false,
                            temaCompletadoReciente = true,
                            respuestaCorrecta = null,
                            opcionSeleccionada = null,
                            explicacionPropia = ""
                        )
                    }
                }.onFailure { _uiState.update { it.copy(isSaving = false, error = true) } }
        }
    }
}
