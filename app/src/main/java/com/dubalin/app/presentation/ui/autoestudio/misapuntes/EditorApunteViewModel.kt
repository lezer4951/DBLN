package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.Apunte
import com.dubalin.app.domain.repository.ApuntesRepository
import com.dubalin.app.domain.repository.InvalidNoteException
import com.dubalin.app.domain.repository.NoteNotFoundException
import com.dubalin.app.domain.repository.SectionNotFoundException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class EditorApunteMessage {
    SAVED,
    INVALID_NOTE,
    NOTE_NOT_FOUND,
    SECTION_NOT_FOUND,
    OPERATION_ERROR
}

data class EditorApunteUiState(
    val apunte: Apunte? = null,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val message: EditorApunteMessage? = null
)

@HiltViewModel
class EditorApunteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ApuntesRepository
) : ViewModel() {

    private val seccionId: Int = savedStateHandle["seccionId"] ?: 0
    private val apunteId: Int = savedStateHandle["apunteId"] ?: 0

    private val _uiState = MutableStateFlow(
        EditorApunteUiState(
            isEditing = apunteId > 0,
            isLoading = apunteId > 0
        )
    )
    val uiState: StateFlow<EditorApunteUiState> = _uiState.asStateFlow()

    init {
        if (apunteId > 0) cargarApunte()
    }

    fun guardar(titulo: String, contenido: String) {
        if (_uiState.value.isSaving) return
        if (titulo.isBlank() || contenido.isBlank()) {
            _uiState.update { it.copy(message = EditorApunteMessage.INVALID_NOTE) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, message = null) }

            repository.guardarApunte(
                seccionId = seccionId,
                apunteId = apunteId.takeIf { it > 0 },
                titulo = titulo,
                contenido = contenido
            )
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            message = EditorApunteMessage.SAVED
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            message = error.toEditorMessage()
                        )
                    }
                }
        }
    }

    fun messageShown() {
        _uiState.update { it.copy(message = null) }
    }

    private fun cargarApunte() {
        viewModelScope.launch {
            repository.obtenerApunte(apunteId)
                .onSuccess { apunte ->
                    if (apunte.seccionId != seccionId) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                message = EditorApunteMessage.NOTE_NOT_FOUND
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(apunte = apunte, isLoading = false)
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = error.toEditorMessage()
                        )
                    }
                }
        }
    }
}

private fun Throwable.toEditorMessage(): EditorApunteMessage = when (this) {
    is InvalidNoteException -> EditorApunteMessage.INVALID_NOTE
    is NoteNotFoundException -> EditorApunteMessage.NOTE_NOT_FOUND
    is SectionNotFoundException -> EditorApunteMessage.SECTION_NOT_FOUND
    else -> EditorApunteMessage.OPERATION_ERROR
}
