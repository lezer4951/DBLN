package com.dubalin.app.presentation.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.SessionManager
import com.dubalin.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val loginExitoso: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(correo: String, password: String) {
        if (correo.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Completa correo y contraseña.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            authRepository.login(correo.trim(), password)
                .onSuccess { usuario ->
                    sessionManager.saveUsuarioId(usuario.id)
                    _uiState.update { it.copy(isLoading = false, loginExitoso = true) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "No se pudo iniciar sesión."
                        )
                    }
                }
        }
    }

    /** Limpia el error tras mostrarlo, para no repetirlo en recomposiciones. */
    fun errorMostrado() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
