package com.dubalin.app.domain.usecase

import com.dubalin.app.domain.repository.SessionRepository
import com.dubalin.app.domain.repository.UsuarioRepository
import javax.inject.Inject

/**
 * Comprueba que la sesión persistida todavía pertenece a un usuario válido.
 */
class ValidarSesionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(): Boolean {
        val usuarioId = sessionRepository.getUsuarioId() ?: return false
        val usuarioExiste = usuarioRepository.obtenerUsuario(usuarioId) != null

        if (!usuarioExiste) {
            sessionRepository.clearSession()
        }

        return usuarioExiste
    }
}
