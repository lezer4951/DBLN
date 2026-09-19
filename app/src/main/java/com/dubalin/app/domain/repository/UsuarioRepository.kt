package com.dubalin.app.domain.repository

import com.dubalin.app.domain.model.EstadisticasUsuario
import com.dubalin.app.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

/**
 * Lectura del perfil del usuario logueado. Separado de AuthRepository
 * porque ese es específicamente sobre acciones de autenticación
 * (login/registro); este es sobre observar datos del usuario ya
 * autenticado (Home, Perfil, etc.).
 */
interface UsuarioRepository {

    fun observarUsuario(usuarioId: Int): Flow<Usuario?>

    fun observarEstadisticas(usuarioId: Int): Flow<EstadisticasUsuario?>
}
