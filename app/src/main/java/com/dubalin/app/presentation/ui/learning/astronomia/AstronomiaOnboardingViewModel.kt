package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.ExperienciaAstronomia
import com.dubalin.app.domain.model.FormatoAprendizaje
import com.dubalin.app.domain.model.PlanEstudioAstronomia
import com.dubalin.app.domain.model.PlanificadorAstronomia
import com.dubalin.app.domain.repository.ProgresoAcademicoRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AstronomiaOnboardingUiState(
    val experiencia: ExperienciaAstronomia = ExperienciaAstronomia.PRIMERA_VEZ,
    val minutosDiarios: Int = 30,
    val formato: FormatoAprendizaje = FormatoAprendizaje.EQUILIBRADO,
    val plan: PlanEstudioAstronomia = PlanificadorAstronomia.crearPlan(30),
    val isSaving: Boolean = false,
    val guardado: Boolean = false,
    val error: Boolean = false
)

@HiltViewModel
class AstronomiaOnboardingViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val progresoRepository: ProgresoAcademicoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AstronomiaOnboardingUiState())
    val uiState: StateFlow<AstronomiaOnboardingUiState> = _uiState.asStateFlow()

    fun seleccionarExperiencia(valor: ExperienciaAstronomia) = _uiState.update { it.copy(experiencia = valor) }

    fun seleccionarTiempo(minutos: Int) = _uiState.update {
        it.copy(minutosDiarios = minutos, plan = PlanificadorAstronomia.crearPlan(minutos))
    }

    fun seleccionarFormato(valor: FormatoAprendizaje) = _uiState.update { it.copy(formato = valor) }

    fun guardar() {
        val usuarioId = sessionRepository.getUsuarioId()
        if (usuarioId == null) {
            _uiState.update { it.copy(error = true) }
            return
        }
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = false) }
            progresoRepository.configurarPlanAstronomia(
                usuarioId, state.experiencia, state.minutosDiarios, state.formato
            ).onSuccess {
                _uiState.update { it.copy(isSaving = false, guardado = true) }
            }.onFailure {
                _uiState.update { it.copy(isSaving = false, error = true) }
            }
        }
    }

    fun errorMostrado() = _uiState.update { it.copy(error = false) }
}
