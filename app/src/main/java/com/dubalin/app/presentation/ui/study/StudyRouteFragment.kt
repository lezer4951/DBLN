package com.dubalin.app.presentation.ui.study

import android.os.Bundle
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.dubalin.app.databinding.ItemRouteLessonBinding
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentStudyRouteBinding
import com.dubalin.app.domain.model.Materias
import com.dubalin.app.domain.model.ShortLesson
import com.dubalin.app.domain.model.ShortLessons
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudyRouteFragment : Fragment(R.layout.fragment_study_route) {
    private val model: LearningViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentStudyRouteBinding.bind(view)
        b.toolbar.title = Materias.buscar(model.subject)?.nombre
        com.dubalin.app.presentation.ui.learning.Motion.enter(b.root)
        b.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.combine(model.store.changes) { s, _ -> s }.collect { s ->
                    val lessons = ShortLessons.forSubject(model.subject)
                    val completed = model.store.completed(model.user)
                    val next = lessons.firstOrNull { it.id !in completed }
                    b.summary.text = if(!s.ready) s.feedback.ifEmpty { "Cargando tu avance…" } else
                        "Lecciones breves · 5–7 min estimados\n${lessons.count { it.id in completed }} de ${lessons.size} completadas"
                    b.progress.setProgressCompat(lessons.count { it.id in completed } * 100 / lessons.size.coerceAtLeast(1), false)
                    b.resume.isVisible = next != null; b.resume.isEnabled = s.ready
                    b.resume.text = if(next != null && model.store.draft(model.user, next.id) > 0) "Retomar: ${next.title}" else "Continuar: ${next?.title.orEmpty()}"
                    b.resume.setOnClickListener { next?.let(::open) }
                    b.levels.removeAllViews()
                    lessons.groupBy { it.level }.forEach { (level, units) ->
                        val title = layoutInflater.inflate(R.layout.item_study_heading, b.levels, false) as TextView
                        title.text = "Nivel ${level + 1}"; b.levels.addView(title)
                        units.forEachIndexed { index, lesson ->
                            val done = lesson.id in completed
                            val unlocked = done || lesson == next
                            val card = ItemRouteLessonBinding.inflate(layoutInflater, b.levels, false)
                            card.title.text = lesson.title
                            card.number.text = if(done) "✓" else (index + 1).toString()
                            card.status.text = when { done -> "Completada · repasar"; unlocked -> "Tu siguiente lección · 5–7 min"; else -> "Completa la lección anterior" }
                            val tint = ContextCompat.getColor(requireContext(), if(done) R.color.dubalin_green else if(unlocked) R.color.md_primary else R.color.dubalin_ink_soft)
                            card.number.setTextColor(tint)
                            card.number.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), if(done) R.color.dubalin_green_soft else if(unlocked) R.color.dubalin_primary_soft else R.color.dubalin_rank_bg))
                            card.root.strokeColor = ContextCompat.getColor(requireContext(), if(lesson == next) R.color.md_primary else R.color.dubalin_line)
                            card.root.strokeWidth = ((if(lesson == next) 2 else 1) * resources.displayMetrics.density).toInt()
                            card.arrow.isVisible = unlocked
                            card.root.isEnabled = s.ready && unlocked
                            card.root.isClickable = s.ready && unlocked
                            card.root.isFocusable = s.ready && unlocked
                            card.root.setOnClickListener(if(s.ready && unlocked) View.OnClickListener { open(lesson) } else null)
                            b.levels.addView(card.root)
                        }
                    }
                }
            }
        }
    }
    private fun open(lesson: ShortLesson) {
        findNavController().navigate(R.id.shortLessonFragment, Bundle().apply { putString("lesson", lesson.id); putString("subject", lesson.subject.name) })
    }
}
