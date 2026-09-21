package com.dubalin.app.presentation.ui.learning

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.dubalin.app.databinding.ItemHubCardBinding

fun LinearLayout.hubCard(
    title: String,
    detail: String,
    accent: Boolean = false,
    status: String? = null,
    enabled: Boolean = true,
    action: (() -> Unit)? = null
) {
    val b = ItemHubCardBinding.inflate(LayoutInflater.from(context), this, false)
    b.hubTitle.text = title
    b.hubDetail.text = detail
    b.hubStatus.text = status
    b.hubStatus.isVisible = status != null
    androidx.core.view.ViewCompat.setAccessibilityHeading(b.hubTitle, accent)
    b.root.isEnabled = enabled
    b.root.alpha = if (enabled) 1f else 0.58f
    b.root.contentDescription = listOfNotNull(title, detail, status).joinToString(". ")
    if (enabled && action != null) {
        b.root.isClickable = true
        b.root.isFocusable = true
        b.root.setOnClickListener { action() }
        b.hubAction.isVisible = true
        b.hubAction.setOnClickListener { action() }
    } else {
        b.root.isClickable = false
        b.root.isFocusable = false
    }
    addView(b.root)
}
