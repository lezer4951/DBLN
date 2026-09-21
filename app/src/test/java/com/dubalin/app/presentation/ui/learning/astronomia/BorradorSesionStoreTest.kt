package com.dubalin.app.presentation.ui.learning.astronomia

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = Application::class)
class BorradorSesionStoreTest {
    @Test fun restauraBorradorEnOtraInstanciaSinMezclarUsuariosONiveles() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        context.getSharedPreferences("borradores_astronomia_v1", 0).edit().clear().commit()
        BorradorSesionStore(context).guardar(7, 3, 2, FaseSesion.AUTOEVALUACION,
            1, 2, false, "Mi explicación pendiente")
        val reopened = BorradorSesionStore(context)
        assertEquals(3, reopened.ultimoNivel(7))
        assertNull(reopened.ultimoNivel(8))
        val draft = requireNotNull(reopened.leer(7, 3))
        assertEquals(2, draft.getInt("tema"))
        assertEquals("AUTOEVALUACION", draft.getString("fase"))
        assertEquals(1, draft.getInt("pregunta"))
        assertEquals(2, draft.getInt("seleccion"))
        assertEquals("false", draft.getString("correcta"))
        assertEquals("Mi explicación pendiente", draft.getString("explicacion"))
        assertNull(reopened.leer(8, 3))
        assertNull(reopened.leer(7, 4))
        assertNull(reopened.leer(null, 3))
        reopened.guardar(7, 4, 0, FaseSesion.CONTENIDO, 0, null, null, "")
        assertEquals(4, BorradorSesionStore(context).ultimoNivel(7))
        assertNotNull(reopened.leer(7, 3))
    }
}
