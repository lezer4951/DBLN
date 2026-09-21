package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.AstronomiaRuta
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.NivelAstronomia
import com.dubalin.app.domain.repository.ProgresoAcademicoRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class AstronomiaRutaUiState(
    val niveles: List<NivelAstronomia> = AstronomiaRuta.niveles,
    val isLoading: Boolean = true,
    val mejorPuntaje: Int = 0,
    val onboardingCompletado: Boolean = false,
    val error: Boolean = false
)

@HiltViewModel
class AstronomiaRutaViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    progresoRepository: ProgresoAcademicoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AstronomiaRutaUiState())
    val uiState: StateFlow<AstronomiaRutaUiState> = _uiState.asStateFlow()

    init {
        val usuarioId = sessionRepository.getUsuarioId()
        if (usuarioId == null) {
            _uiState.value = AstronomiaRutaUiState(isLoading = false, error = true)
        } else {
            progresoRepository.observarProgreso(usuarioId, MateriaId.ASTRONOMIA)
                .onEach { progreso ->
                    _uiState.value = AstronomiaRutaUiState(
                        niveles = AstronomiaRuta.niveles.map { nivel ->
                            nivel.copy(bloqueado = nivel.numero > progreso.nivelActual)
                        },
                        isLoading = false,
                        mejorPuntaje = progreso.mejorPuntaje,
                        onboardingCompletado = progreso.onboardingCompletado
                    )
                }
                .catch {
                    _uiState.value = AstronomiaRutaUiState(isLoading = false, error = true)
                }
                .launchIn(viewModelScope)
        }
    }
}
