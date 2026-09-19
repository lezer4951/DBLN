package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.SessionManager
import com.dubalin.app.domain.model.SeccionApuntes
import com.dubalin.app.domain.repository.ApuntesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeccionesViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val apuntesRepository: ApuntesRepository
) : ViewModel() {

    private val usuarioId: Int? = sessionManager.getUsuarioId()

    val secciones: StateFlow<List<SeccionApuntes>> =
        if (usuarioId != null) {
            apuntesRepository.observarSecciones(usuarioId)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        } else {
            kotlinx.coroutines.flow.MutableStateFlow(emptyList())
        }

    fun crearSeccion(nombre: String) {
        val id = usuarioId ?: return
        if (nombre.isBlank()) return
        viewModelScope.launch {
            apuntesRepository.crearSeccion(id, nombre.trim())
        }
    }

    fun eliminarSeccion(seccion: SeccionApuntes) {
        viewModelScope.launch {
            apuntesRepository.eliminarSeccion(seccion)
        }
    }
}
