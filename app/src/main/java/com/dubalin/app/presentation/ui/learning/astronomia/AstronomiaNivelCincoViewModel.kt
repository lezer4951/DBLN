package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.ViewModel
import com.dubalin.app.domain.model.mezclarAutoevaluacion
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.NivelCincoAstronomiaContenido
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

data class AstronomiaNivelCincoUiState(
    val sesiones: List<SesionAstronomia> = NivelCincoAstronomiaContenido.sesiones.map { it.mezclarAutoevaluacion() },
    val temaActual: Int = 0, val temasCompletados: Int = 0, val practicaCompletada: Boolean = false,
    val fase: FaseSesion = FaseSesion.CONTENIDO, val preguntaActual: Int = 0,
    val opcionSeleccionada: Int? = null, val respuestaCorrecta: Boolean? = null,
    val explicacionPropia: String = "", val isLoading: Boolean = true, val isSaving: Boolean = false,
    val temasRepaso: List<Int> = emptyList(), val indiceRepaso: Int = 0,
    val temaCompletadoReciente: Boolean = false, val error: Boolean = false
) {
    val sesion get() = sesiones[temaActual]
    val pregunta: PreguntaAutoevaluacion? get() = sesion.autoevaluacion.getOrNull(preguntaActual)
    val temaCompletado get() = NivelCincoAstronomiaContenido.completado(temasCompletados, temaActual)
    val enRepaso get() = temasRepaso.isNotEmpty()
    val ultimoRepaso get() = enRepaso && indiceRepaso == temasRepaso.lastIndex
}

@HiltViewModel
class AstronomiaNivelCincoViewModel @Inject constructor(
    private val session: SessionRepository,
    private val repository: NivelAstronomiaRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AstronomiaNivelCincoUiState())
    val state: StateFlow<AstronomiaNivelCincoUiState> = _state.asStateFlow()
    init { observar() }
    fun iniciarAutoevaluacion() = _state.update { it.copy(fase = FaseSesion.AUTOEVALUACION,
        preguntaActual = 0, opcionSeleccionada = null, respuestaCorrecta = null, explicacionPropia = "") }
    fun seleccionar(i: Int) { if (_state.value.respuestaCorrecta == null) _state.update { it.copy(opcionSeleccionada = i) } }
    fun actualizarExplicacion(texto: String) = _state.update { it.copy(explicacionPropia = texto) }
    fun accionAutoevaluacion() {
        val s = _state.value; val q = s.pregunta ?: return
        when {
            s.respuestaCorrecta == null -> { val opcion = s.opcionSeleccionada ?: return
                _state.update { it.copy(respuestaCorrecta = opcion == q.respuestaCorrecta) } }
            s.respuestaCorrecta == false -> _state.update { it.copy(opcionSeleccionada = null, respuestaCorrecta = null) }
            s.preguntaActual < s.sesion.autoevaluacion.lastIndex -> _state.update { it.copy(
                preguntaActual = it.preguntaActual + 1, opcionSeleccionada = null, respuestaCorrecta = null, explicacionPropia = "") }
            s.explicacionPropia.trim().length >= 12 -> guardarTema()
        }
    }
    fun anterior() = _state.update { s -> if (s.fase == FaseSesion.AUTOEVALUACION) s.copy(
        fase = FaseSesion.CONTENIDO, opcionSeleccionada = null, respuestaCorrecta = null, explicacionPropia = "")
        else s.copy(temaActual = (s.temaActual - 1).coerceAtLeast(0)) }
    fun siguiente() = _state.update { s -> s.copy(temaActual = (s.temaActual + 1).coerceAtMost(s.sesiones.lastIndex),
        fase = FaseSesion.CONTENIDO, preguntaActual = 0, opcionSeleccionada = null,
        respuestaCorrecta = null, explicacionPropia = "", temaCompletadoReciente = false) }
    fun iniciarRepaso(temas: List<Int>) {
        val validos = temas.distinct().filter { it in _state.value.sesiones.indices }; if (validos.isEmpty()) return
        _state.update { it.copy(temaActual = validos.first(), temasRepaso = validos, indiceRepaso = 0,
            fase = FaseSesion.CONTENIDO, explicacionPropia = "") }
    }
    fun siguienteRepaso() = _state.update { s -> val next = (s.indiceRepaso + 1).coerceAtMost(s.temasRepaso.lastIndex)
        s.copy(indiceRepaso = next, temaActual = s.temasRepaso[next], fase = FaseSesion.CONTENIDO) }
    fun consumirEvento() = _state.update { it.copy(temaCompletadoReciente = false) }
    fun errorMostrado() = _state.update { it.copy(error = false) }
    private fun observar() {
        val user = session.getUsuarioId() ?: run { _state.update { it.copy(isLoading = false, error = true) }; return }
        repository.observarNivel(user, 5).onEach { progreso -> _state.update { s ->
            val pendiente = s.sesiones.indices.firstOrNull { !NivelCincoAstronomiaContenido.completado(progreso.temasCompletados, it) }
                ?: s.sesiones.lastIndex
            s.copy(temaActual = if (s.isLoading) pendiente else s.temaActual, temasCompletados = progreso.temasCompletados,
                practicaCompletada = progreso.practicaCompletada, isLoading = false)
        } }.catch { _state.update { it.copy(isLoading = false, error = true) } }.launchIn(viewModelScope)
    }
    private fun guardarTema() {
        val user = session.getUsuarioId() ?: return; val tema = _state.value.temaActual
        viewModelScope.launch { _state.update { it.copy(isSaving = true) }
            repository.completarTema(user, 5, tema).onSuccess { _state.update { s -> s.copy(
                temasCompletados = s.temasCompletados or (1 shl tema), fase = FaseSesion.CONTENIDO,
                isSaving = false, temaCompletadoReciente = true, opcionSeleccionada = null,
                respuestaCorrecta = null, explicacionPropia = "") } }
                .onFailure { _state.update { it.copy(isSaving = false, error = true) } }
        }
    }
}
