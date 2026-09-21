package com.dubalin.app.domain.model

import kotlin.random.Random
import org.junit.Assert.*
import org.junit.Test

class CatalogoAstronomiaTest {
    @Test fun porcentajeSoloLlegaAlCienTrasGraduacion() {
        assertEquals(0, CatalogoAstronomia.porcentaje(0))
        assertTrue(CatalogoAstronomia.porcentaje(10) < 100)
        assertEquals(100, CatalogoAstronomia.porcentaje(11))
    }
    @Test fun mezclaConservaRespuestaYUsaVariasPosiciones() {
        val random = Random(123)
        val originales = CatalogoAstronomia.examenes.flatten()
        val mezcladas = originales.map { it.mezclar(random) }
        originales.zip(mezcladas).forEach { (a, b) ->
            assertEquals(a.opciones[a.respuestaCorrecta], b.opciones[b.respuestaCorrecta])
            assertEquals(a.opciones.sorted(), b.opciones.sorted())
        }
        assertEquals(4, mezcladas.map { it.respuestaCorrecta }.toSet().size)
    }
    @Test fun repasoFinalIncluyeTodosLosNiveles() {
        val preguntas = CatalogoAstronomia.practica(10)
        for (nivel in 0..10) assertTrue(preguntas.any { it.enunciado.startsWith("Repaso del nivel $nivel ·") })
        assertEquals(30, preguntas.size)
    }
    @Test fun todosLosTemasDelExamenTienenLeccion() {
        CatalogoAstronomia.examenes.forEachIndexed { nivel, preguntas ->
            preguntas.forEach { q ->
                assertTrue(q.tema in 1..CatalogoAstronomia.sesiones[nivel].size)
                assertTrue(q.respuestaCorrecta in q.opciones.indices)
            }
        }
    }
}
