package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MateriasTest {

    @Test
    fun `todas las materias tienen una ruta inicial disponible`() {
        assertEquals(MateriaId.values().toList(), Materias.disponibles.map { it.id })
        assertTrue(Materias.buscar(MateriaId.ASTRONOMIA)?.disponible == true)
        assertTrue(Materias.buscar(MateriaId.FISICA)?.disponible == true)
    }

    @Test
    fun `astronomia contiene nivel introductorio y diez niveles`() {
        val astronomia = requireNotNull(Materias.buscar(MateriaId.ASTRONOMIA))

        assertEquals(11, astronomia.temas.size)
        assertEquals("Introducción a la astronomía", astronomia.temas.first())
        assertEquals("Astronomía avanzada", astronomia.temas.last())
    }
}
