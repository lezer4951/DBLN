package com.dubalin.app.presentation.ui.study

import android.os.Bundle
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
        b.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.combine(model.store.changes) { s, _ -> s }.collect { s ->
                    val lessons = ShortLessons.forSubject(model.subject)
                    val completed = model.store.completed(model.user)
                    val next = lessons.firstOrNull { it.id !in completed }
                    b.summary.text = if(!s.ready) s.feedback.ifEmpty { "Cargando tu avance…" } else
                        "Lecciones breves · 5–7 min estimados\n${lessons.count { it.id in completed }} de ${lessons.size} completadas"
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
                            val button = layoutInflater.inflate(R.layout.item_study_button, b.levels, false) as MaterialButton
                            button.text = "${if(done) "✓ " else if(!unlocked) "🔒 " else ""}${index + 1}. ${lesson.title}"
                            button.isEnabled = s.ready && unlocked
                            button.setOnClickListener { open(lesson) }; b.levels.addView(button)
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
