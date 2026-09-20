package com.dubalin.app.presentation.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.ProfileDetails
import com.dubalin.app.data.local.ProfileDetailsStore
import com.dubalin.app.domain.repository.SessionRepository
import com.dubalin.app.domain.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

data class PerfilHeaderState(val nombre: String = "Dubalin", val nivel: Int = 1, val xp: Int = 0)

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val detailsStore: ProfileDetailsStore,
    usuarioRepository: UsuarioRepository
) : ViewModel() {
    private val _header = MutableStateFlow(PerfilHeaderState())
    val header: StateFlow<PerfilHeaderState> = _header.asStateFlow()

    init {
        sessionRepository.getUsuarioId()?.let { id ->
            usuarioRepository.observarUsuario(id)
                .combine(usuarioRepository.observarEstadisticas(id)) { usuario, stats ->
                    val xp = (stats?.totalAciertos ?: 0) * 5
                    PerfilHeaderState(usuario?.nombre.orEmpty().ifBlank { "Dubalin" }, xp / 100 + 1, xp)
                }
                .onEach { _header.value = it }
                .launchIn(viewModelScope)
        }
    }

    fun detalles(): ProfileDetails =
        sessionRepository.getUsuarioId()?.let(detailsStore::read) ?: ProfileDetails()

    fun guardarDetalles(details: ProfileDetails) {
        sessionRepository.getUsuarioId()?.let { detailsStore.save(it, details) }
    }

    val sesionCerrada: StateFlow<Boolean> = sessionRepository.usuarioId
        .map { it == null }
        .stateIn(viewModelScope, SharingStarted.Eagerly, sessionRepository.getUsuarioId() == null)

    fun cerrarSesion() = sessionRepository.clearSession()
}
