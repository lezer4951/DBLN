package com.dubalin.app.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Sesión local simple: guarda el id del usuario logueado en
 * SharedPreferences. No requiere módulo Hilt explícito -- el constructor
 * @Inject con @ApplicationContext alcanza (mismo patrón que AppInfoProvider
 * del Paso 0.3).
 */
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUsuarioId(id: Int) {
        prefs.edit().putInt(KEY_USUARIO_ID, id).apply()
    }

    fun getUsuarioId(): Int? {
        val id = prefs.getInt(KEY_USUARIO_ID, NO_SESSION)
        return if (id == NO_SESSION) null else id
    }

    fun clearSession() {
        prefs.edit().remove(KEY_USUARIO_ID).apply()
    }

    companion object {
        private const val PREFS_NAME = "dubalin_session"
        private const val KEY_USUARIO_ID = "usuario_id"
        private const val NO_SESSION = -1
    }
}
