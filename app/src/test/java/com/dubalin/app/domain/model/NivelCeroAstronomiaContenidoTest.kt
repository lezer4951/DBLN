package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NivelCeroAstronomiaContenidoTest {

    @Test
    fun `el nivel cero contiene diez sesiones y diez preguntas finales`() {
        assertEquals(10, NivelCeroAstronomiaContenido.sesiones.size)
        assertEquals(10, NivelCeroAstronomiaContenido.preguntas.size)
        NivelCeroAstronomiaContenido.sesiones.forEach { sesion ->
            assertEquals(2, sesion.autoevaluacion.size)
            assertTrue(sesion.analogia.isNotBlank())
            assertTrue(sesion.ejemploVisual.isNotBlank())
        }
    }

    @Test
    fun `la mascara reconoce los diez temas dominados`() {
        var mascara = 0
        repeat(10) { mascara = NivelCeroAstronomiaContenido.completarTema(mascara, it) }
        assertTrue(NivelCeroAstronomiaContenido.todosLosTemasCompletados(mascara))
        assertEquals(1023, mascara)
    }

    @Test
    fun `el plan diario calcula sesiones sin truncar tiempo pendiente`() {
        assertEquals(12, PlanificadorAstronomia.crearPlan(15).diasEstimados)
        assertEquals(6, PlanificadorAstronomia.crearPlan(30).diasEstimados)
        assertEquals(3, PlanificadorAstronomia.crearPlan(60).diasEstimados)
        assertEquals(4, PlanificadorAstronomia.crearPlan(60).temasPorDia)
    }

    @Test
    fun `cada pregunta tiene cuatro opciones y una respuesta valida`() {
        NivelCeroAstronomiaContenido.preguntas.forEach { pregunta ->
            assertEquals(4, pregunta.opciones.size)
            assertTrue(pregunta.respuestaCorrecta in pregunta.opciones.indices)
            assertTrue(pregunta.explicacion.isNotBlank())
        }
    }
}
