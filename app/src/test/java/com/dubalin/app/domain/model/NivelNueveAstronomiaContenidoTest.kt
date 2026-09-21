package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelNueveAstronomiaContenidoTest {
    @Test fun `nivel nueve contiene catorce sesiones practica y examen`() {
        assertEquals(14, NivelNueveAstronomiaContenido.sesiones.size)
        assertEquals(7, NivelNueveAstronomiaContenido.practica.size)
        assertEquals(10, NivelNueveAstronomiaContenido.examen.size)
        NivelNueveAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero); assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.explicacion.isNotBlank() && sesion.analogia.isNotBlank())
        }
    }
    @Test fun `mascara completa representa catorce temas`() {
        assertEquals(16383, NivelNueveAstronomiaContenido.mascaraCompleta)
        repeat(14) { assertTrue(NivelNueveAstronomiaContenido.completado(16383, it)) }
    }
}
