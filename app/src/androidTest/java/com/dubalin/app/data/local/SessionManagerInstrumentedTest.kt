package com.dubalin.app.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionManagerInstrumentedTest {

    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        sessionManager = SessionManager(context)
        sessionManager.clearSession()
    }

    @After
    fun tearDown() {
        sessionManager.clearSession()
    }

    @Test
    fun guardar_y_limpiar_actualiza_el_estado_observable() {
        assertNull(sessionManager.usuarioId.value)

        sessionManager.saveUsuarioId(42)
        assertEquals(42, sessionManager.usuarioId.value)
        assertEquals(42, sessionManager.getUsuarioId())

        sessionManager.clearSession()
        assertNull(sessionManager.usuarioId.value)
        assertNull(sessionManager.getUsuarioId())
    }

    @Test
    fun la_sesion_se_recupera_desde_shared_preferences() {
        sessionManager.saveUsuarioId(7)

        val nuevaInstancia = SessionManager(context)

        assertEquals(7, nuevaInstancia.getUsuarioId())
        assertEquals(7, nuevaInstancia.usuarioId.value)
    }
}
