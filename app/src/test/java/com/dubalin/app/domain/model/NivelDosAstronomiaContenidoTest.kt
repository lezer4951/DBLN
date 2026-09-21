package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelDosAstronomiaContenidoTest {
    @Test fun `nivel dos contiene catorce sesiones y repaso acumulativo`() {
        assertEquals(14, NivelDosAstronomiaContenido.sesiones.size)
        assertEquals(7, NivelDosAstronomiaContenido.practica.size)
        assertEquals(10, NivelDosAstronomiaContenido.examen.size)
        assertTrue(NivelDosAstronomiaContenido.practica.any { it.tema == 1 })
        assertTrue(NivelDosAstronomiaContenido.practica.any { it.tema == 12 })
        NivelDosAstronomiaContenido.sesiones.forEach { assertEquals(2, it.autoevaluacion.size) }
    }

    @Test fun `mascara completa representa catorce temas`() {
        assertEquals(16383, NivelDosAstronomiaContenido.mascaraCompleta)
        repeat(14) { assertTrue(NivelDosAstronomiaContenido.completado(16383, it)) }
    }
}
