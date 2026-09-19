package com.dubalin.app.presentation.ui.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.SessionManager
import com.dubalin.app.domain.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class SplashDestination {
    LOGIN,
    HOME
}

data class SplashUiState(
    val destination: SplashDestination? = null
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        resolverDestino()
    }

    fun destinoConsumido() {
        _uiState.update { it.copy(destination = null) }
    }

    private fun resolverDestino() {
        viewModelScope.launch {
            delay(SPLASH_DELAY_MS)

            val usuarioId = sessionManager.getUsuarioId()
            val usuarioExiste = usuarioId != null &&
                usuarioRepository.obtenerUsuario(usuarioId) != null

            if (!usuarioExiste) {
                sessionManager.clearSession()
            }

            _uiState.update {
                it.copy(
                    destination = if (usuarioExiste) {
                        SplashDestination.HOME
                    } else {
                        SplashDestination.LOGIN
                    }
                )
            }
        }
    }

    private companion object {
        const val SPLASH_DELAY_MS = 800L
    }
}
