package com.dubalin.app.presentation.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    val sesionCerrada: StateFlow<Boolean> =
        sessionManager.usuarioId
            .map { usuarioId -> usuarioId == null }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = sessionManager.getUsuarioId() == null
            )

    fun cerrarSesion() {
        sessionManager.clearSession()
    }
}
