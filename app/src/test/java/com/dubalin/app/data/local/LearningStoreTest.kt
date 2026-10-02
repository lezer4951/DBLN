package com.dubalin.app.data.local

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = Application::class)
class LearningStoreTest {
    private val context = ApplicationProvider.getApplicationContext<Application>()
    private lateinit var store: LearningStore
    @Before fun setup() {
        context.getSharedPreferences("learning_v2", 0).edit().clear().commit()
        store = LearningStore(context)
    }
    @Test fun lessonsActivateStreakOnlyOncePerDayAndSurviveRestart() {
        store.saveStep(1, "lesson", 4)
        assertEquals(0, store.streak(1))
        store.complete(1, "lesson")
        store.complete(1, "another")
        assertEquals(1, LearningStore(context).streak(1))
        assertEquals(0, store.draft(1, "lesson"))
        assertEquals(0, store.streak(2))
        assertEquals(0, store.balance(1))
    }
    @Test fun streakContinuesFromYesterdayButBreaksAfterMissingADay() {
        fun day(offset: Int): String = Calendar.getInstance().let { c ->
            c.add(Calendar.DAY_OF_YEAR, offset)
            SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(c.time)
        }
        context.getSharedPreferences("learning_v2", 0).edit().putStringSet("1.days", setOf(day(-2), day(-1))).commit()
        assertEquals(2, store.streak(1))
        store.complete(1, "new")
        assertEquals(3, store.streak(1))
        context.getSharedPreferences("learning_v2", 0).edit().putStringSet("1.days", setOf(day(-2))).commit()
        assertEquals(0, store.streak(1))
    }
    @Test fun gamesDoNotActivateStreakAndCannotPayTwice() {
        assertEquals(10, store.rewardGame(1, "wordle", 10))
        assertEquals(0, LearningStore(context).rewardGame(1, "wordle", 10))
        assertEquals(10, store.balance(1))
        assertEquals(0, store.streak(1))
        assertEquals(0, store.balance(2))
    }
    @Test fun purchaseAndEquipDoNotDoubleChargeAndRejectInsufficientFunds() {
        val item = Cosmetics.items.first()
        assertTrue(runCatching { store.purchaseOrEquip(1, item) }.isFailure)
        store.rewardGame(1, "wordle", 10)
        store.purchaseOrEquip(1, item)
        store.purchaseOrEquip(1, item)
        assertEquals(0, store.balance(1))
        assertEquals(item.id, LearningStore(context).equipped(1, "avatar"))
        assertTrue(item.id in store.owned(1))
        assertTrue(store.owned(2).isEmpty())
    }
    @Test fun importingOldLessonsDoesNotInventStudyDays() {
        store.importLessons(1, setOf("ASTRONOMIA:0:0"))
        assertEquals(setOf("ASTRONOMIA:0:0"), store.completed(1))
        assertEquals(0, store.streak(1))
    }
}
