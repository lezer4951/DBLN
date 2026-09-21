package com.dubalin.app.presentation.ui.learning.astronomia

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentAstronomiaQuizBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PracticaLibreFragment : Fragment(R.layout.fragment_astronomia_quiz) {
    private val model: PracticaLibreViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentAstronomiaQuizBinding.bind(view)
        val opciones = listOf(b.optionA, b.optionB, b.optionC, b.optionD)
        var dibujando = false
        b.quizToolbar.setTitle(if (model.supervivencia) R.string.challenge_survival else R.string.astronomy_free_practice)
        b.quizToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        b.quizOptions.setOnCheckedChangeListener { _, id ->
            if (!dibujando) model.seleccionar(opciones.indexOfFirst { it.id == id })
        }
        b.buttonQuizAction.setOnClickListener { model.accion() }
        b.buttonQuizRetry.setOnClickListener { model.reiniciar() }
        b.buttonQuizFinish.setOnClickListener { findNavController().navigateUp() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.collect { s ->
                    b.quizContent.isVisible = s.resultado == null
                    b.quizResult.isVisible = s.resultado != null
                    val resultado = s.resultado
                    if (resultado != null) {
                        b.resultIcon.text = "🔭"
                        b.resultTitle.setText(if (model.supervivencia) R.string.challenge_finished else R.string.astronomy_practice_complete)
                        b.resultScore.text = getString(R.string.astronomy_quiz_score, s.aciertos, s.preguntas.size, s.aciertos * 100 / s.preguntas.size)
                        b.resultDescription.setText(if (model.supervivencia) R.string.challenge_survival_description else R.string.astronomy_free_practice_description)
                        b.buttonQuizRetry.isVisible = true
                        b.buttonQuizRetry.setText(R.string.astronomy_free_retry)
                        b.buttonQuizFinish.setText(R.string.astronomy_free_back)
                    } else {
                        val q = s.preguntas[s.actual]
                        b.quizProgress.setProgressCompat(s.actual * 100 / s.preguntas.size, true)
                        b.quizCounter.text = getString(R.string.astronomy_quiz_counter, s.actual + 1, s.preguntas.size)
                        b.quizQuestion.text = q.enunciado
                        dibujando = true
                        b.quizOptions.clearCheck()
                        opciones.forEachIndexed { i, boton ->
                            boton.text = q.opciones[i]
                            boton.isEnabled = s.feedback == null
                        }
                        s.seleccion?.let { opciones[it].isChecked = true }
                        dibujando = false
                        b.quizFeedback.isVisible = s.feedback != null
                        b.quizFeedback.text = getString(if (s.feedback == true) R.string.astronomy_feedback_correct else R.string.astronomy_feedback_incorrect, q.explicacion)
                        b.quizSaving.isVisible = s.guardando
                        b.buttonQuizAction.isEnabled = s.seleccion != null && !s.guardando
                        b.buttonQuizAction.setText(if (s.feedback == null) R.string.astronomy_check_answer
                            else if (s.actual == s.preguntas.lastIndex) R.string.astronomy_finish_exam else R.string.action_next)
                    }
                }
            }
        }
    }
}
