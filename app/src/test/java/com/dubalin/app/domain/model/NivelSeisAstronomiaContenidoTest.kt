package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelSeisAstronomiaContenidoTest {
    @Test fun `nivel seis contiene catorce sesiones practica y examen`() {
        assertEquals(14, NivelSeisAstronomiaContenido.sesiones.size)
        assertEquals(7, NivelSeisAstronomiaContenido.practica.size)
        assertEquals(10, NivelSeisAstronomiaContenido.examen.size)
        NivelSeisAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero); assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.explicacion.isNotBlank() && sesion.analogia.isNotBlank())
        }
    }
    @Test fun `mascara completa representa catorce temas`() {
        assertEquals(16383, NivelSeisAstronomiaContenido.mascaraCompleta)
        repeat(14) { assertTrue(NivelSeisAstronomiaContenido.completado(16383, it)) }
    }
}
