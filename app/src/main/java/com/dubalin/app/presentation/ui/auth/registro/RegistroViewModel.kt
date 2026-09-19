package com.dubalin.app.presentation.ui.auth.registro

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

data class RegistroUiState(
    val isLoading: Boolean = false,
    val registroExitoso: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun registrar(nombre: String, correo: String, password: String, confirmarPassword: String) {
        val error = validar(nombre, correo, password, confirmarPassword)
        if (error != null) {
            _uiState.update { it.copy(errorMessage = error) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            authRepository.registrar(nombre.trim(), correo.trim(), password)
                .onSuccess { usuario ->
                    // Auto-login tras registrarse: se guarda la sesión de una vez,
                    // sin obligar al usuario a volver a escribir sus credenciales.
                    sessionManager.saveUsuarioId(usuario.id)
                    _uiState.update { it.copy(isLoading = false, registroExitoso = true) }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "No se pudo crear la cuenta."
                        )
                    }
                }
        }
    }

    fun errorMostrado() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun validar(
        nombre: String,
        correo: String,
        password: String,
        confirmarPassword: String
    ): String? = when {
        nombre.isBlank() -> "Ingresa tu nombre."
        correo.isBlank() || !correo.contains("@") -> "Ingresa un correo válido."
        password.length < 6 -> "La contraseña debe tener al menos 6 caracteres."
        password != confirmarPassword -> "Las contraseñas no coinciden."
        else -> null
    }
}
