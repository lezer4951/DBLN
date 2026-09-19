package com.dubalin.app.presentation.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.core.util.EmailValidator
import com.dubalin.app.data.local.SessionManager
import com.dubalin.app.domain.repository.AuthRepository
import com.dubalin.app.domain.repository.InvalidCredentialsException
import com.dubalin.app.domain.repository.UserNotFoundException
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
        if (_uiState.value.isLoading) return

        val errorValidacion = when {
            correo.isBlank() || password.isBlank() ->
                "Completa correo y contraseña."
            !EmailValidator.isValid(correo) ->
                "Ingresa un correo válido."
            else -> null
        }

        if (errorValidacion != null) {
            _uiState.update { it.copy(errorMessage = errorValidacion) }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    loginExitoso = false,
                    errorMessage = null
                )
            }

            authRepository.login(correo, password)
                .onSuccess { usuario ->
                    sessionManager.saveUsuarioId(usuario.id)
                    _uiState.update {
                        it.copy(isLoading = false, loginExitoso = true)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toLoginMessage()
                        )
                    }
                }
        }
    }

    fun errorMostrado() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun loginConsumido() {
        _uiState.update { it.copy(loginExitoso = false) }
    }
}

private fun Throwable.toLoginMessage(): String = when (this) {
    is UserNotFoundException,
    is InvalidCredentialsException -> "Correo o contraseña incorrectos."
    else -> "No se pudo iniciar sesión. Inténtalo de nuevo."
}
