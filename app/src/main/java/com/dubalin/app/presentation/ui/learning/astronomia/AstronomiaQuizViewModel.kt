package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.CatalogoAstronomia
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.PreguntaAstronomia
import com.dubalin.app.domain.repository.ProgresoAcademicoRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

enum class AstronomyQuizError { ANSWER_REQUIRED, SESSION_REQUIRED, SAVE_FAILED }
typealias AstronomyQuizResult = EvaluacionResultado
data class AstronomyQuizUiState(
    val preguntas: List<PreguntaAstronomia>, val preguntaActual: Int,
    val opcionSeleccionada: Int?, val aciertos: Int, val temasDebiles: Set<Int>,
    val mostrandoRetroalimentacion: Boolean, val isSaving: Boolean,
    val resultado: AstronomyQuizResult?, val error: AstronomyQuizError?
)
private fun EvaluacionUiState.paraNivelCero() = AstronomyQuizUiState(
    preguntas, actual, seleccion, aciertos, temasDebiles, feedback != null,
    guardando, resultado, if (error) AstronomyQuizError.SAVE_FAILED else null
)
@HiltViewModel
class AstronomiaQuizViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    progresoRepository: ProgresoAcademicoRepository,
    saved: SavedStateHandle = SavedStateHandle()
) : EvaluacionAstronomiaViewModel(CatalogoAstronomia.examenes[0], false, saved, { aciertos, total ->
    val user = sessionRepository.getUsuarioId()
    if (user == null) Result.failure(IllegalStateException("Sesión requerida"))
    else progresoRepository.registrarEvaluacionNivelCero(user, MateriaId.ASTRONOMIA, aciertos, total)
}) {
    val uiState = state.map { it.paraNivelCero() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, state.value.paraNivelCero())
    fun seleccionarOpcion(indice: Int) = seleccionar(indice)
    fun accionPrincipal() = accion()
}
