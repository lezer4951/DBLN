package com.dubalin.app.domain.repository

import kotlinx.coroutines.flow.StateFlow

/**
 * Contrato de la sesión activa. Mantiene al dominio y a la presentación
 * independientes del mecanismo usado para persistirla.
 */
interface SessionRepository {
    val usuarioId: StateFlow<Int?>

    fun getUsuarioId(): Int?

    fun saveUsuarioId(id: Int)

    fun clearSession()
}
