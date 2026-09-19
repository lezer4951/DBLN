package com.dubalin.app.presentation.ui.perfil

import androidx.lifecycle.ViewModel
import com.dubalin.app.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel mínimo de Perfil, solo con logout por ahora. El contenido
 * real (estadísticas, logros, configuración de tema/idioma) se
 * construye en el Módulo 7.
 */
@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    fun cerrarSesion() {
        sessionManager.clearSession()
    }
}
