package com.dubalin.app.presentation.ui.learning.astronomia

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children

/** Notify after RadioGroup finishes updating its checked ID, allowing synchronous rendering. */
internal fun RadioGroup.onAnswerSelected(onSelected: (Int) -> Unit) {
    children.filterIsInstance<RadioButton>().forEachIndexed { index, button ->
        button.setOnClickListener { onSelected(index) }
    }
}
