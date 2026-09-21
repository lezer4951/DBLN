package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelTresAstronomiaContenidoTest {
    @Test fun `nivel tres contiene quince sesiones practica y examen`() {
        assertEquals(15, NivelTresAstronomiaContenido.sesiones.size)
        assertEquals(6, NivelTresAstronomiaContenido.practica.size)
        assertEquals(10, NivelTresAstronomiaContenido.examen.size)
        NivelTresAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero); assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.explicacion.isNotBlank() && sesion.analogia.isNotBlank())
        }
    }
    @Test fun `mascara completa representa quince temas`() {
        assertEquals(32767, NivelTresAstronomiaContenido.mascaraCompleta)
        repeat(15) { assertTrue(NivelTresAstronomiaContenido.completado(32767, it)) }
    }
}
