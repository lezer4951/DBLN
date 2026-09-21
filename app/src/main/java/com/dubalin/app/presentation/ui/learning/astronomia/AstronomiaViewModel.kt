package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.repository.ProgresoAcademicoRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class AstronomiaUiState(
    val nivelActual: Int = 0,
    val nivelCeroCompletado: Boolean = false,
    val mejorPuntaje: Int = 0
)

@HiltViewModel
class AstronomiaViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    progresoRepository: ProgresoAcademicoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AstronomiaUiState())
    val uiState: StateFlow<AstronomiaUiState> = _uiState.asStateFlow()

    init {
        sessionRepository.getUsuarioId()?.let { usuarioId ->
            progresoRepository.observarProgreso(usuarioId, MateriaId.ASTRONOMIA)
                .onEach { progreso ->
                    _uiState.value = AstronomiaUiState(
                        nivelActual = progreso.nivelActual,
                        nivelCeroCompletado = progreso.nivelCeroCompletado,
                        mejorPuntaje = progreso.mejorPuntaje
                    )
                }
                .launchIn(viewModelScope)
        }
    }
}
