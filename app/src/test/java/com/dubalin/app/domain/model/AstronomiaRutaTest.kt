package com.dubalin.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AstronomiaRutaTest {

    @Test
    fun `la ruta contiene introduccion y diez niveles`() {
        assertEquals((0..10).toList(), AstronomiaRuta.niveles.map { it.numero })
    }

    @Test
    fun `solo el nivel cero inicia desbloqueado`() {
        assertFalse(AstronomiaRuta.niveles.first().bloqueado)
        assertTrue(AstronomiaRuta.niveles.drop(1).all { it.bloqueado })
    }
}
