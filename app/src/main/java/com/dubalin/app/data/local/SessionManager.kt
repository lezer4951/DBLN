package com.dubalin.app.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Mantiene la sesión local y expone sus cambios de forma observable.
 * La instancia es única para que toda la app comparta el mismo estado.
 */
@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _usuarioId = MutableStateFlow(readUsuarioId())
    val usuarioId: StateFlow<Int?> = _usuarioId.asStateFlow()

    fun saveUsuarioId(id: Int) {
        require(id > 0) { "El id de usuario debe ser mayor que cero." }
        prefs.edit().putInt(KEY_USUARIO_ID, id).apply()
        _usuarioId.value = id
    }

    fun getUsuarioId(): Int? = _usuarioId.value

    fun clearSession() {
        prefs.edit().remove(KEY_USUARIO_ID).apply()
        _usuarioId.value = null
    }

    private fun readUsuarioId(): Int? {
        val id = prefs.getInt(KEY_USUARIO_ID, NO_SESSION)
        return id.takeIf { it > 0 }
    }

    private companion object {
        const val PREFS_NAME = "dubalin_session"
        const val KEY_USUARIO_ID = "usuario_id"
        const val NO_SESSION = -1
    }
}
