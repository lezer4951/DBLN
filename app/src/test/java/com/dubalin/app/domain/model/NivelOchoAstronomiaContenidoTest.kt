package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelOchoAstronomiaContenidoTest {
    @Test fun `nivel ocho contiene catorce sesiones practica y examen`() {
        assertEquals(14, NivelOchoAstronomiaContenido.sesiones.size)
        assertEquals(7, NivelOchoAstronomiaContenido.practica.size)
        assertEquals(10, NivelOchoAstronomiaContenido.examen.size)
        NivelOchoAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero); assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.explicacion.isNotBlank() && sesion.analogia.isNotBlank())
        }
    }
    @Test fun `mascara completa representa catorce temas`() {
        assertEquals(16383, NivelOchoAstronomiaContenido.mascaraCompleta)
        repeat(14) { assertTrue(NivelOchoAstronomiaContenido.completado(16383, it)) }
    }
}
