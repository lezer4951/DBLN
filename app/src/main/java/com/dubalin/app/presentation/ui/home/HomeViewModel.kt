package com.dubalin.app.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.repository.SessionRepository
import com.dubalin.app.domain.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class HomeUiState(
    val nombre: String = "",
    val nivel: Int = 1,
    val xpTotal: Int = 0,
    val rachaDias: Int = 0
)

/**
 * XP y nivel se calculan a partir de EstadisticaUsuario.totalAciertos --
 * por ahora solo los quizzes dan XP (Módulo 5, aún no construido), así
 * que para un usuario nuevo esto siempre parte en 0. La Palabra Diaria
 * sumará XP cuando se conecte el motor de IA, que requiere su propia
 * migración de esquema más adelante.
 */
private const val XP_POR_ACIERTO = 5
private const val XP_POR_NIVEL = 100

@HiltViewModel
class HomeViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        val usuarioId = sessionRepository.getUsuarioId()
        if (usuarioId != null) {
            usuarioRepository.observarUsuario(usuarioId)
                .combine(usuarioRepository.observarEstadisticas(usuarioId)) { usuario, estadisticas ->
                    val xpTotal = (estadisticas?.totalAciertos ?: 0) * XP_POR_ACIERTO
                    HomeUiState(
                        nombre = usuario?.nombre ?: "",
                        nivel = (xpTotal / XP_POR_NIVEL) + 1,
                        xpTotal = xpTotal,
                        rachaDias = usuario?.rachaDias ?: 0
                    )
                }
                .onEach { _uiState.value = it }
                .launchIn(viewModelScope)
        }
    }
}
