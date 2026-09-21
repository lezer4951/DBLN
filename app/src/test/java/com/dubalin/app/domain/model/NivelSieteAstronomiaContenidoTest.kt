package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelSieteAstronomiaContenidoTest {
    @Test fun `nivel siete contiene catorce sesiones practica y examen`() {
        assertEquals(14, NivelSieteAstronomiaContenido.sesiones.size)
        assertEquals(7, NivelSieteAstronomiaContenido.practica.size)
        assertEquals(10, NivelSieteAstronomiaContenido.examen.size)
        NivelSieteAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero); assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.explicacion.isNotBlank() && sesion.analogia.isNotBlank())
        }
    }
    @Test fun `mascara completa representa catorce temas`() {
        assertEquals(16383, NivelSieteAstronomiaContenido.mascaraCompleta)
        repeat(14) { assertTrue(NivelSieteAstronomiaContenido.completado(16383, it)) }
    }
}
