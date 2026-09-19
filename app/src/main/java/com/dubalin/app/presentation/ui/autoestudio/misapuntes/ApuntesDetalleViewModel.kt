package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.Apunte
import com.dubalin.app.domain.repository.ApuntesRepository
import com.dubalin.app.domain.repository.NoteNotFoundException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ApuntesMessage {
    DELETED,
    NOTE_NOT_FOUND,
    LOAD_ERROR,
    OPERATION_ERROR
}

data class ApuntesUiState(
    val seccionId: Int = 0,
    val seccionNombre: String = "",
    val apuntes: List<Apunte> = emptyList(),
    val isLoading: Boolean = true,
    val isDeleting: Boolean = false,
    val message: ApuntesMessage? = null
)

@HiltViewModel
class ApuntesDetalleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ApuntesRepository
) : ViewModel() {

    private val seccionId: Int = savedStateHandle["seccionId"] ?: 0
    private val seccionNombre: String = savedStateHandle.get<String>("seccionNombre").orEmpty()

    private val _uiState = MutableStateFlow(
        ApuntesUiState(
            seccionId = seccionId,
            seccionNombre = seccionNombre
        )
    )
    val uiState: StateFlow<ApuntesUiState> = _uiState.asStateFlow()

    init {
        observarApuntes()
    }

    fun eliminarApunte(apunte: Apunte) {
        if (_uiState.value.isDeleting) return

        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true, message = null) }

            repository.eliminarApunte(apunte)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isDeleting = false,
                            message = ApuntesMessage.DELETED
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isDeleting = false,
                            message = if (error is NoteNotFoundException) {
                                ApuntesMessage.NOTE_NOT_FOUND
                            } else {
                                ApuntesMessage.OPERATION_ERROR
                            }
                        )
                    }
                }
        }
    }

    fun messageShown() {
        _uiState.update { it.copy(message = null) }
    }

    private fun observarApuntes() {
        if (seccionId <= 0) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    message = ApuntesMessage.LOAD_ERROR
                )
            }
            return
        }

        viewModelScope.launch {
            repository.observarApuntes(seccionId)
                .catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = ApuntesMessage.LOAD_ERROR
                        )
                    }
                }
                .collect { apuntes ->
                    _uiState.update {
                        it.copy(
                            apuntes = apuntes,
                            isLoading = false
                        )
                    }
                }
        }
    }
}
