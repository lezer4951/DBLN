package com.dubalin.app.poc.wordle

import java.time.LocalDate
import java.time.ZoneOffset

/**
 * Motor de "Palabra del Día" — lógica pura (sin Android Framework),
 * para que sea testeable con JUnit puro y reutilizable desde el ViewModel real.
 * Debe producir EXACTAMENTE el mismo resultado que wordle-engine.js (versión Web)
 * dada la misma fecha y el mismo intento, ya que ambos implementan el mismo algoritmo.
 */

enum class EstadoLetra { CORRECTA, PRESENTE, AUSENTE }

object WordleEngine {

    private val ANCLA: LocalDate = LocalDate.of(2026, 1, 1)

    /** Selecciona la palabra del día de forma determinística a partir de la fecha. */
    fun palabraDelDia(fecha: LocalDate, listaPalabras: List<String>): String {
        val dias = ANCLA.until(fecha, java.time.temporal.ChronoUnit.DAYS).toInt()
        val indice = ((dias % listaPalabras.size) + listaPalabras.size) % listaPalabras.size
        return listaPalabras[indice]
    }

    /**
     * Evalúa un intento contra la palabra secreta, manejando correctamente
     * letras repetidas mediante un algoritmo de dos pasadas.
     */
    fun evaluarIntento(secreta: String, intento: String): List<EstadoLetra> {
        require(intento.length == secreta.length) {
            "El intento debe tener ${secreta.length} letras"
        }
        val s = secreta.uppercase().toCharArray()
        val t = intento.uppercase().toCharArray()
        val resultado = MutableList(s.size) { EstadoLetra.AUSENTE }
        val disponibles = HashMap<Char, Int>()

        // Pasada 1: correctas exactas
        for (i in s.indices) {
            if (t[i] == s[i]) {
                resultado[i] = EstadoLetra.CORRECTA
            } else {
                disponibles[s[i]] = (disponibles[s[i]] ?: 0) + 1
            }
        }
        // Pasada 2: presentes, usando el remanente no consumido
        for (i in s.indices) {
            if (resultado[i] == EstadoLetra.CORRECTA) continue
            val letra = t[i]
            val restantes = disponibles[letra] ?: 0
            if (restantes > 0) {
                resultado[i] = EstadoLetra.PRESENTE
                disponibles[letra] = restantes - 1
            }
        }
        return resultado
    }

    fun esVictoria(resultado: List<EstadoLetra>): Boolean =
        resultado.all { it == EstadoLetra.CORRECTA }
}
