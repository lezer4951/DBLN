package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.SessionManager
import com.dubalin.app.domain.model.SeccionApuntes
import com.dubalin.app.domain.repository.ApuntesRepository
import com.dubalin.app.domain.repository.DuplicateSectionNameException
import com.dubalin.app.domain.repository.SectionNotFoundException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class SeccionesMessage {
    CREATED,
    UPDATED,
    DELETED,
    DUPLICATE_NAME,
    SECTION_NOT_FOUND,
    SESSION_REQUIRED,
    LOAD_ERROR,
    OPERATION_ERROR
}

data class SeccionesUiState(
    val secciones: List<SeccionApuntes> = emptyList(),
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val message: SeccionesMessage? = null
)

@HiltViewModel
class SeccionesViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val apuntesRepository: ApuntesRepository
) : ViewModel() {

    private val usuarioId = sessionManager.getUsuarioId()

    private val _uiState = MutableStateFlow(SeccionesUiState())
    val uiState: StateFlow<SeccionesUiState> = _uiState.asStateFlow()

    init {
        observarSecciones()
    }

    fun guardarSeccion(seccion: SeccionApuntes?, nombre: String) {
        val id = usuarioId
        if (id == null) {
            _uiState.update { it.copy(message = SeccionesMessage.SESSION_REQUIRED) }
            return
        }
        if (_uiState.value.isSaving || nombre.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, message = null) }

            val result = if (seccion == null) {
                apuntesRepository.crearSeccion(id, nombre)
            } else {
                apuntesRepository.actualizarSeccion(seccion.id, id, nombre)
            }

            result
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            message = if (seccion == null) {
                                SeccionesMessage.CREATED
                            } else {
                                SeccionesMessage.UPDATED
                            }
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            message = error.toSeccionesMessage()
                        )
                    }
                }
        }
    }

    fun eliminarSeccion(seccion: SeccionApuntes) {
        if (_uiState.value.isSaving) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, message = null) }

            apuntesRepository.eliminarSeccion(seccion)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            message = SeccionesMessage.DELETED
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            message = error.toSeccionesMessage()
                        )
                    }
                }
        }
    }

    fun messageShown() {
        _uiState.update { it.copy(message = null) }
    }

    private fun observarSecciones() {
        val id = usuarioId
        if (id == null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    message = SeccionesMessage.SESSION_REQUIRED
                )
            }
            return
        }

        viewModelScope.launch {
            apuntesRepository.observarSecciones(id)
                .catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = SeccionesMessage.LOAD_ERROR
                        )
                    }
                }
                .collect { secciones ->
                    _uiState.update {
                        it.copy(
                            secciones = secciones,
                            isLoading = false
                        )
                    }
                }
        }
    }
}

private fun Throwable.toSeccionesMessage(): SeccionesMessage = when (this) {
    is DuplicateSectionNameException -> SeccionesMessage.DUPLICATE_NAME
    is SectionNotFoundException -> SeccionesMessage.SECTION_NOT_FOUND
    else -> SeccionesMessage.OPERATION_ERROR
}
