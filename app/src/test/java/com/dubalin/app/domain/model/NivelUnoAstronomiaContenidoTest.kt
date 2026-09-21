package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelUnoAstronomiaContenidoTest {
    @Test fun `nivel uno contiene trece temas practica y examen`() {
        assertEquals(13, NivelUnoAstronomiaContenido.sesiones.size)
        assertEquals(5, NivelUnoAstronomiaContenido.practica.size)
        assertEquals(10, NivelUnoAstronomiaContenido.examen.size)
        NivelUnoAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero)
            assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.analogia.isNotBlank())
        }
    }

    @Test fun `mascara completa representa trece temas`() {
        assertEquals(8191, NivelUnoAstronomiaContenido.mascaraCompleta)
        repeat(13) { assertTrue(NivelUnoAstronomiaContenido.completado(8191, it)) }
    }
}
