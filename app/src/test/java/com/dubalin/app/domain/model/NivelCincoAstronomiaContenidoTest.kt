package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelCincoAstronomiaContenidoTest {
    @Test fun `nivel cinco contiene catorce sesiones practica y examen`() {
        assertEquals(14, NivelCincoAstronomiaContenido.sesiones.size)
        assertEquals(7, NivelCincoAstronomiaContenido.practica.size)
        assertEquals(10, NivelCincoAstronomiaContenido.examen.size)
        NivelCincoAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero); assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.explicacion.isNotBlank() && sesion.analogia.isNotBlank())
        }
    }
    @Test fun `mascara completa representa catorce temas`() {
        assertEquals(16383, NivelCincoAstronomiaContenido.mascaraCompleta)
        repeat(14) { assertTrue(NivelCincoAstronomiaContenido.completado(16383, it)) }
    }
}
