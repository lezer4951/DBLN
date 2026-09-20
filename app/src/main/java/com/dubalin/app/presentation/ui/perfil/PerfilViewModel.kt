package com.dubalin.app.presentation.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val detailsStore: com.dubalin.app.data.local.ProfileDetailsStore
) : ViewModel() {

    fun detalles(): com.dubalin.app.data.local.ProfileDetails =
        sessionRepository.getUsuarioId()?.let(detailsStore::read) ?: com.dubalin.app.data.local.ProfileDetails()

    fun guardarDetalles(details: com.dubalin.app.data.local.ProfileDetails) {
        sessionRepository.getUsuarioId()?.let { detailsStore.save(it, details) }
    }

    val sesionCerrada: StateFlow<Boolean> =
        sessionRepository.usuarioId
            .map { usuarioId -> usuarioId == null }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = sessionRepository.getUsuarioId() == null
            )

    fun cerrarSesion() {
        sessionRepository.clearSession()
    }
}
