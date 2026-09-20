package com.dubalin.app.presentation.ui.learning

import android.view.LayoutInflater
import android.widget.LinearLayout
import com.dubalin.app.databinding.ItemHubCardBinding

fun LinearLayout.hubCard(title: String, detail: String, accent: Boolean = false, action: (() -> Unit)? = null) {
    val b = ItemHubCardBinding.inflate(LayoutInflater.from(context), this, false)
    b.hubTitle.text = title
    b.hubDetail.text = detail
    androidx.core.view.ViewCompat.setAccessibilityHeading(b.hubTitle, accent)
    if (action != null) {
        b.root.isClickable = true
        b.root.isFocusable = true
        b.root.setOnClickListener { action() }
    }
    addView(b.root)
}
