package com.dubalin.app.domain.model

import java.text.Normalizer
import java.util.Locale

data class ShortQuestion(val prompt: String, val options: List<String>, val correct: Int, val explanation: String)
data class ShortLesson(
    val id: String, val subject: MateriaId, val level: Int, val title: String,
    val theory: String, val example: String, val sentence: String,
    val pairs: List<Pair<String, String>>, val questions: List<ShortQuestion>,
    val spoken: String? = null, val language: String = "es-MX", val visual: String? = null,
    val gapAnswer: String? = null
) {
    fun activityAt(page: Int): Int = if (id.hashCode() % 2 == 0 && page in 2..3) 5 - page else page
    val missingWord: String get() = gapAnswer ?: Regex("[\\p{L}]{5,}").findAll(sentence).lastOrNull()?.value
        ?: Regex("[\\p{L}]+").findAll(sentence).last().value
    val gapSentence: String get() {
        val position = sentence.lastIndexOf(missingWord)
        return sentence.replaceRange(position, position + missingWord.length, "_____")
    }
}
fun normalizedAnswer(value: String): String = Normalizer.normalize(value.lowercase(Locale.ROOT), Normalizer.Form.NFD)
    .replace(Regex("\\p{M}"), "").replace(Regex("[^\\p{L}\\p{N} ]"), " ").trim().replace(Regex("\\s+"), " ")

object ShortLessons {
    val all: List<ShortLesson> by lazy {
        val astronomy = CatalogoAstronomia.sesiones.flatMapIndexed { level, sessions ->
            sessions.mapIndexed { index, s ->
                val qs = s.autoevaluacion.map { ShortQuestion(it.enunciado, it.opciones, it.respuestaCorrecta, it.explicacion) }
                ShortLesson("ASTRONOMIA:$level:$index", MateriaId.ASTRONOMIA, level, s.titulo,
                    s.explicacion, if (level == 0 && index == 0) "El Sol es una estrella: emite luz propia. La Tierra es un planeta: orbita el Sol y refleja parte de su luz. El esquema no está a escala." else qs.first().explanation, s.ideaClave,
                    qs.take(2).map { it.prompt to it.options[it.correct] }, qs,
                    visual = if (level == 0 && index == 0) "solar" else null)
            }
        }
        astronomy + IntroLessons.all
    }
    fun forSubject(id: MateriaId) = all.filter { it.subject == id }
    fun find(id: String) = all.first { it.id == id }
}
