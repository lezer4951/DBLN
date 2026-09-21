package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelDiezAstronomiaContenidoTest {
    @Test fun `nivel diez contiene catorce sesiones practica y examen`() {
        assertEquals(14, NivelDiezAstronomiaContenido.sesiones.size)
        assertEquals(10, NivelDiezAstronomiaContenido.practica.size)
        assertEquals(10, NivelDiezAstronomiaContenido.examen.size)
        NivelDiezAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero); assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.explicacion.isNotBlank() && sesion.analogia.isNotBlank())
        }
    }
    @Test fun `mascara completa representa catorce temas`() {
        assertEquals(16383, NivelDiezAstronomiaContenido.mascaraCompleta)
        repeat(14) { assertTrue(NivelDiezAstronomiaContenido.completado(16383, it)) }
    }
}
