package com.dubalin.app.presentation.ui.auth.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.core.util.EmailValidator
import com.dubalin.app.domain.repository.AuthRepository
import com.dubalin.app.domain.repository.EmailAlreadyRegisteredException
import com.dubalin.app.domain.repository.SessionRepository
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
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun registrar(nombre: String, correo: String, password: String, confirmarPassword: String) {
        if (_uiState.value.isLoading) return

        val error = validar(nombre, correo, password, confirmarPassword)
        if (error != null) {
            _uiState.update { it.copy(errorMessage = error) }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    registroExitoso = false,
                    errorMessage = null
                )
            }

            authRepository.registrar(nombre, correo, password)
                .onSuccess { usuario ->
                    sessionRepository.saveUsuarioId(usuario.id)
                    _uiState.update {
                        it.copy(isLoading = false, registroExitoso = true)
                    }
                }
                .onFailure { errorRegistro ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = errorRegistro.toRegistroMessage()
                        )
                    }
                }
        }
    }

    fun errorMostrado() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun registroConsumido() {
        _uiState.update { it.copy(registroExitoso = false) }
    }

    private fun validar(
        nombre: String,
        correo: String,
        password: String,
        confirmarPassword: String
    ): String? = when {
        nombre.isBlank() -> "Ingresa tu nombre."
        !EmailValidator.isValid(correo) -> "Ingresa un correo válido."
        password.length < 6 -> "La contraseña debe tener al menos 6 caracteres."
        password != confirmarPassword -> "Las contraseñas no coinciden."
        else -> null
    }
}

private fun Throwable.toRegistroMessage(): String = when (this) {
    is EmailAlreadyRegisteredException -> "Ya existe una cuenta con ese correo."
    else -> "No se pudo crear la cuenta. Inténtalo de nuevo."
}
