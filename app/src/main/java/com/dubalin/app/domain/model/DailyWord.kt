package com.dubalin.app.domain.model

enum class LetterMark { EXACT, PRESENT, ABSENT }
object DailyWord {
    private val words = listOf("ASTRO", "LUNAR", "SOLAR", "ATOMO", "CELDA", "RADIO", "PLANO", "DATOS", "TEXTO", "VERBO", "LIBRO", "IDEAS", "MAPAS", "NUBES", "CALOR", "MASAS", "METRO", "ACERO", "COBRE", "GASES", "RESTO", "RECTA", "PUNTO", "SERIE", "RAZON", "REINO", "VALOR", "FLUJO", "CICLO", "FRASE")
    fun answer(day: String) = words[Math.floorMod(day.hashCode(), words.size)]
    fun evaluate(secret: String, guess: String): List<LetterMark> {
        require(secret.length == 5 && guess.length == 5)
        val result = MutableList(5) { LetterMark.ABSENT }
        val remaining = mutableMapOf<Char, Int>()
        for (i in secret.indices) if(secret[i] == guess[i]) result[i] = LetterMark.EXACT
            else remaining[secret[i]] = (remaining[secret[i]] ?: 0) + 1
        for (i in guess.indices) if(result[i] != LetterMark.EXACT && (remaining[guess[i]] ?: 0) > 0) {
            result[i] = LetterMark.PRESENT; remaining[guess[i]] = remaining.getValue(guess[i]) - 1
        }
        return result
    }
}
