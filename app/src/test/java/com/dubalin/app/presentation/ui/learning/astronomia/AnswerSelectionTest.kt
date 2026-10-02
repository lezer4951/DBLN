package com.dubalin.app.presentation.ui.learning.astronomia

import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.test.core.app.ApplicationProvider
import com.dubalin.app.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class AnswerSelectionTest {
    @Test
    fun examAndSelfAssessmentKeepOneAnswerAndClearForNextQuestion() {
        val context = ContextThemeWrapper(ApplicationProvider.getApplicationContext(), R.style.Theme_Dubalin)
        listOf(
            R.layout.fragment_astronomia_quiz to R.id.quiz_options,
            R.layout.fragment_astronomia_nivel_cero to R.id.self_options
        ).forEach { (layout, groupId) ->
            val root = LayoutInflater.from(context).inflate(layout, null)
            val group = root.findViewById<RadioGroup>(groupId)
            val options = (0 until group.childCount).map { group.getChildAt(it) as RadioButton }
            var notifications = 0
            group.onAnswerSelected { index ->
                notifications++
                // StateFlow can render immediately in response to a user's selection.
                group.clearCheck()
                group.check(options[index].id)
            }
            options.forEach { option ->
                option.performClick()
                assertEquals(listOf(option), options.filter { it.isChecked })
                assertEquals(option.id, group.checkedRadioButtonId)
            }
            assertEquals(options.size, notifications)
            group.clearCheck()
            group.check(View.NO_ID)
            assertEquals(0, options.count { it.isChecked })
            options.first().performClick()
            assertEquals(listOf(options.first()), options.filter { it.isChecked })
        }
    }
}
