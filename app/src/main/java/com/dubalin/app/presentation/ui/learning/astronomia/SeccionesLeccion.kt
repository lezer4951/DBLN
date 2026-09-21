package com.dubalin.app.presentation.ui.learning.astronomia

import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import androidx.core.view.isVisible
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentAstronomiaNivelCeroBinding

/** Paginación visual; no concede puntos ni marca temas como completados. */
class SeccionesLeccion(private val b: FragmentAstronomiaNivelCeroBinding, private val usuario: Int?, private val nivel: Int) {
    private val prefs = b.root.context.getSharedPreferences("secciones_astronomia_v1", Context.MODE_PRIVATE)
    private val grupos = listOf(b.sectionGoal, b.sectionAnalogy, b.sectionExplanation, b.sectionExample, b.sectionSummary)
    private var tema = -1
    private var pagina = 0
    private var ready = false
    private val key get() = "$usuario:$nivel:$tema"

    init {
        b.sectionPrevious.setOnClickListener { mover(-1) }
        b.sectionNext.setOnClickListener { mover(1) }
    }

    fun render(temaActual: Int) {
        if (tema != temaActual) {
            tema = temaActual
            pagina = if (usuario == null) 0 else prefs.getInt(key, 0).coerceIn(grupos.indices)
            ready = false
        }
        mostrar(false)
        ready = true
    }

    private fun mover(delta: Int) {
        if (!ready) return
        pagina = (pagina + delta).coerceIn(grupos.indices)
        if (usuario != null) prefs.edit().putInt(key, pagina).apply()
        mostrar(true)
        b.lessonContent.smoothScrollTo(0, 0)
    }

    private fun mostrar(animar: Boolean) {
        grupos.forEachIndexed { i, view ->
            view.animate().cancel()
            view.alpha = 1f
            view.isVisible = i == pagina
        }
        b.sectionCounter.text = b.root.context.getString(R.string.lesson_section_counter, pagina + 1, grupos.size)
        b.sectionPrevious.isEnabled = pagina > 0
        b.sectionNext.isVisible = pagina < grupos.lastIndex
        b.buttonNext.isVisible = pagina == grupos.lastIndex
        b.sectionSaved.setText(R.string.lesson_pause_hint)
        if (animar && (Build.VERSION.SDK_INT < 26 || ValueAnimator.areAnimatorsEnabled())) {
            grupos[pagina].alpha = 0f
            grupos[pagina].animate().alpha(1f).setDuration(180).start()
        }
    }
}
