package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelCuatroAstronomiaContenidoTest {
    @Test fun `nivel cuatro contiene dieciseis sesiones practica y examen`() {
        assertEquals(16, NivelCuatroAstronomiaContenido.sesiones.size)
        assertEquals(6, NivelCuatroAstronomiaContenido.practica.size)
        assertEquals(10, NivelCuatroAstronomiaContenido.examen.size)
        NivelCuatroAstronomiaContenido.sesiones.forEachIndexed { index, sesion ->
            assertEquals(index + 1, sesion.numero); assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.explicacion.isNotBlank() && sesion.analogia.isNotBlank())
        }
    }
    @Test fun `mascara completa representa dieciseis temas`() {
        assertEquals(65535, NivelCuatroAstronomiaContenido.mascaraCompleta)
        repeat(16) { assertTrue(NivelCuatroAstronomiaContenido.completado(65535, it)) }
    }
}
