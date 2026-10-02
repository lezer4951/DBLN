package com.dubalin.app.presentation.ui.study

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import com.dubalin.app.R
import com.dubalin.app.data.local.Cosmetics
import com.dubalin.app.data.local.LearningStore

internal fun applyAvatar(view: TextView, store: LearningStore, user: Int, initial: String) {
    val avatar = when(store.equipped(user, "avatar")) { "planet" -> R.drawable.avatar_planet; "rocket" -> R.drawable.avatar_rocket; else -> null }
    view.text = if(avatar == null) initial else ""
    view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    if(avatar != null) {
        val drawable = androidx.core.content.ContextCompat.getDrawable(view.context, avatar)!!
        val size = (34 * view.resources.displayMetrics.density).toInt()
        drawable.setBounds(0, 0, size, size)
        view.setCompoundDrawables(drawable, null, null, null)
        view.compoundDrawablePadding = 0
        view.setPadding((12 * view.resources.displayMetrics.density).toInt(), 0, 0, 0)
    }
    val frame = Cosmetics.items.firstOrNull { it.id == store.equipped(user, "frame") }
    view.background = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(0xFF5138DE.toInt())
        if(frame != null) setStroke((3 * view.resources.displayMetrics.density).toInt(), frame.color)
    }
    view.contentDescription = if(avatar == null) "Perfil de $initial" else "Avatar ${store.equipped(user, "avatar")}"
}
