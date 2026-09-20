package com.dubalin.app.presentation.ui.learning

import android.provider.Settings
import android.view.View

object Motion {
    fun enabled(view: View): Boolean = Settings.Global.getFloat(
        view.context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) > 0f

    fun enter(view: View) {
        if (!enabled(view)) return
        view.alpha = 0f
        view.translationY = 12f * view.resources.displayMetrics.density
        view.animate().alpha(1f).translationY(0f).setDuration(220)
            .setInterpolator(android.view.animation.DecelerateInterpolator()).start()
        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) = Unit
            override fun onViewDetachedFromWindow(v: View) {
                v.animate().cancel()
                v.alpha = 1f
                v.translationY = 0f
                v.removeOnAttachStateChangeListener(this)
            }
        })
    }
}
