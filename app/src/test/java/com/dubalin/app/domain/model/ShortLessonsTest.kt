package com.dubalin.app.domain.model

import org.junit.Assert.*
import org.junit.Test

class ShortLessonsTest {
    @Test fun everySubjectHasLevelsAndLessonsWithValidExercises() {
        assertEquals(ShortLessons.all.size, ShortLessons.all.map { it.id }.toSet().size)
        MateriaId.values().forEach { subject ->
            val lessons = ShortLessons.forSubject(subject)
            assertTrue(lessons.map { it.level }.toSet().size >= 2)
            lessons.groupBy { it.level }.values.forEach { assertTrue(it.size >= 2) }
            lessons.forEach { lesson ->
                assertTrue(lesson.theory.isNotBlank())
                assertTrue(lesson.example.isNotBlank())
                assertTrue(lesson.gapSentence.contains("_____"))
                assertTrue(lesson.missingWord.isNotBlank())
                assertTrue(lesson.pairs.size >= 2)
                assertTrue(lesson.questions.size >= 2)
                lesson.questions.forEach { assertTrue(it.correct in it.options.indices) }
                assertEquals(setOf(2, 3), setOf(lesson.activityAt(2), lesson.activityAt(3)))
            }
        }
    }
    @Test fun wordleDoesNotOvercountDuplicateLetters() {
        assertEquals(listOf(LetterMark.EXACT, LetterMark.ABSENT, LetterMark.ABSENT, LetterMark.ABSENT, LetterMark.ABSENT), DailyWord.evaluate("ASTRO", "AAAAA"))
        assertEquals(List(5) { LetterMark.EXACT }, DailyWord.evaluate("LUNAR", "LUNAR"))
        assertEquals(DailyWord.answer("2026-10-02"), DailyWord.answer("2026-10-02"))
    }
}
