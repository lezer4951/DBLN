package com.dubalin.app.poc.wordle

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

/**
 * Pruebas unitarias JUnit — reflejan exactamente los mismos casos que
 * test-engine.js (versión Web), para comprobar paridad de comportamiento
 * entre las dos plataformas.
 *
 * Ejecutar desde Android Studio o con:
 *   ./gradlew testDebugUnitTest --tests "*.WordleEngineTest"
 */
class WordleEngineTest {

    @Test
    fun `todas las letras correctas produce CORRECTA x5`() {
        val r = WordleEngine.evaluarIntento("MUNDO", "MUNDO")
        assertEquals(List(5) { EstadoLetra.CORRECTA }, r)
    }

    @Test
    fun `ninguna letra coincide produce AUSENTE x5`() {
        val r = WordleEngine.evaluarIntento("MUNDO", "PLAZA")
        assertEquals(List(5) { EstadoLetra.AUSENTE }, r)
    }

    @Test
    fun `letras repetidas se reparten correctamente`() {
        // secreta: LLAMA  intento: AULAS
        val r = WordleEngine.evaluarIntento("LLAMA", "AULAS")
        assertEquals(EstadoLetra.PRESENTE, r[0]) // A
        assertEquals(EstadoLetra.PRESENTE, r[2]) // L
        assertEquals(EstadoLetra.PRESENTE, r[3]) // A
    }

    @Test
    fun `palabraDelDia es deterministica para la misma fecha`() {
        val fecha = LocalDate.of(2026, 9, 24)
        val lista = listOf("MUNDO", "TIERRA", "LUNA", "ORBITA", "COMETA")
        val a = WordleEngine.palabraDelDia(fecha, lista)
        val b = WordleEngine.palabraDelDia(fecha, lista)
        assertEquals(a, b)
    }

    @Test
    fun `esVictoria detecta triunfo y rechaza intento incompleto`() {
        assertTrue(WordleEngine.esVictoria(WordleEngine.evaluarIntento("MUNDO", "MUNDO")))
        assertFalse(WordleEngine.esVictoria(WordleEngine.evaluarIntento("MUNDO", "PLAZA")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `intento de longitud distinta lanza excepcion`() {
        WordleEngine.evaluarIntento("MUNDO", "SOL")
    }
}
