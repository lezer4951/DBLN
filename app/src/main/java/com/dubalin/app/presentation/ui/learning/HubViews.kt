package com.dubalin.app.presentation.ui.learning
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Typeface
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.google.android.material.R as MR

fun LinearLayout.hubCard(title: String, detail: String, accent: Boolean = false, action: (() -> Unit)? = null) {
    val card = MaterialCardView(context).apply {
        radius = 20 * resources.displayMetrics.density
        cardElevation = 0f
        strokeWidth = if (accent) 0 else 1
        strokeColor = MaterialColors.getColor(this, MR.attr.colorOutlineVariant)
        setCardBackgroundColor(MaterialColors.getColor(this, if (accent) MR.attr.colorPrimaryContainer else MR.attr.colorSurface))
        layoutParams = LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = (14 * resources.displayMetrics.density).toInt() }
    }
    val color = MaterialColors.getColor(card, if (accent) MR.attr.colorOnPrimaryContainer else MR.attr.colorOnSurface)
    val body = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        val p = (20 * resources.displayMetrics.density).toInt()
        setPadding(p, p, p, p)
    }
    body.addView(TextView(context).apply { text = title; textSize = 20f; setTypeface(null, Typeface.BOLD); setTextColor(color) })
    body.addView(TextView(context).apply { text = detail; textSize = 15f; setTextColor(color); setPadding(0, 12, 0, 0) })
    card.addView(body)
    if (action != null) {
        card.isClickable = true; card.isFocusable = true
        card.contentDescription = "$title. $detail"
        card.setOnClickListener { action() }
    }
    addView(card)
}
