package com.dubalin.app.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Local, per-account progress. A single committed edit makes purchases and rewards atomic. */
@Singleton
class LearningStore @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences("learning_v2", Context.MODE_PRIVATE)
    private val mutable = MutableStateFlow(0)
    val changes = mutable.asStateFlow()
    fun today(): String = date(Calendar.getInstance())
    private fun date(c: Calendar) = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(c.time)
    private fun set(id: Int, key: String) = prefs.getStringSet("$id.$key", emptySet()).orEmpty().toSet()
    fun completed(id: Int) = set(id, "lessons")
    fun studiedDays(id: Int) = set(id, "days")
    fun draft(id: Int, lesson: String) = prefs.getInt("$id.draft.$lesson", 0)
    fun balance(id: Int) = prefs.getInt("$id.coins", 0)
    fun owned(id: Int) = set(id, "owned")
    fun equipped(id: Int, category: String) = prefs.getString("$id.equipped.$category", "").orEmpty()
    fun streak(id: Int): Int {
        val days = studiedDays(id)
        val c = Calendar.getInstance()
        if (date(c) !in days) c.add(Calendar.DAY_OF_YEAR, -1)
        var count = 0
        while (date(c) in days) { count++; c.add(Calendar.DAY_OF_YEAR, -1) }
        return count
    }
    private fun commit(editor: android.content.SharedPreferences.Editor) {
        check(editor.commit()) { "No se pudo guardar. Inténtalo de nuevo." }
        mutable.value++
    }
    @Synchronized fun saveStep(id: Int, lesson: String, step: Int) {
        require(id > 0 && step >= 0)
        commit(prefs.edit().putInt("$id.draft.$lesson", step))
    }
    @Synchronized fun complete(id: Int, lesson: String) {
        require(id > 0)
        commit(prefs.edit().putStringSet("$id.lessons", completed(id) + lesson)
            .putStringSet("$id.days", studiedDays(id) + today()).remove("$id.draft.$lesson"))
    }
    @Synchronized fun importLessons(id: Int, lessons: Set<String>) {
        if (lessons.isNotEmpty()) commit(prefs.edit().putStringSet("$id.lessons", completed(id) + lessons))
    }
    /** One reward per game and local calendar day; games never write study days. */
    @Synchronized fun rewardGame(id: Int, game: String, amount: Int, day: String = today()): Int {
        require(id > 0 && game in setOf("wordle", "survival", "memory") && amount in 1..10)
        val key = "$game:$day"
        val rewards = set(id, "rewards")
        if (key in rewards) return 0
        commit(prefs.edit().putStringSet("$id.rewards", rewards + key).putInt("$id.coins", balance(id) + amount))
        return amount
    }
    @Synchronized fun purchaseOrEquip(id: Int, item: Cosmetic) {
        require(id > 0 && item in Cosmetics.items)
        val owned = owned(id)
        val cost = if (item.id in owned) 0 else item.price
        check(balance(id) >= cost) { "Te faltan ${cost - balance(id)} monedas." }
        commit(prefs.edit().putInt("$id.coins", balance(id) - cost)
            .putStringSet("$id.owned", owned + item.id).putString("$id.equipped.${item.category}", item.id))
    }
    fun wordGuesses(id: Int, day: String): List<String> = prefs.getString("$id.word.$day", "").orEmpty().split(',').filter { it.isNotEmpty() }
    @Synchronized fun saveWordGuess(id: Int, day: String, guess: String) {
        val old = wordGuesses(id, day)
        require(id > 0 && old.size < 6 && guess.length == 5)
        commit(prefs.edit().putString("$id.word.$day", (old + guess).joinToString(",")))
    }
}

data class Cosmetic(val id: String, val name: String, val category: String, val price: Int, val color: Int = 0)
object Cosmetics {
    val items = listOf(
        Cosmetic("planet", "Avatar Planeta", "avatar", 10),
        Cosmetic("rocket", "Avatar Cohete", "avatar", 20),
        Cosmetic("night", "Fondo Noche", "background", 20, 0xFF201A38.toInt()),
        Cosmetic("mint", "Fondo Menta", "background", 20, 0xFFE4F6EE.toInt()),
        Cosmetic("violet", "Marco Violeta", "frame", 15, 0xFF7357FF.toInt()),
        Cosmetic("gold", "Marco Dorado", "frame", 25, 0xFFD59C28.toInt())
    )
}
