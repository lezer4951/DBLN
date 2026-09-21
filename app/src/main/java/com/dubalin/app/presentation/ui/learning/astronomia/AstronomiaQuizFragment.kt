package com.dubalin.app.presentation.ui.learning.astronomia

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentAstronomiaQuizBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AstronomiaQuizFragment : Fragment(R.layout.fragment_astronomia_quiz) {

    private var _binding: FragmentAstronomiaQuizBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AstronomiaQuizViewModel by viewModels()
    private var rendering = false
    private var defaultOptionTints: List<ColorStateList?> = emptyList()

    private val optionButtons: List<RadioButton>
        get() = listOf(binding.optionA, binding.optionB, binding.optionC, binding.optionD)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAstronomiaQuizBinding.bind(view)
        defaultOptionTints = optionButtons.map { it.buttonTintList }

        binding.quizToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.quizOptions.setOnCheckedChangeListener { _, checkedId ->
            if (!rendering) {
                optionButtons.indexOfFirst { it.id == checkedId }
                    .takeIf { it >= 0 }
                    ?.let(viewModel::seleccionarOpcion)
            }
        }
        binding.buttonQuizAction.setOnClickListener { viewModel.accionPrincipal() }
        binding.buttonQuizRetry.setOnClickListener {
            val weakTopics = viewModel.uiState.value.resultado?.temasDebiles.orEmpty()
            if (weakTopics.isNotEmpty()) {
                findNavController().previousBackStackEntry?.savedStateHandle
                    ?.set("astronomia_temas_repaso", weakTopics.map { it - 1 }.toIntArray())
                findNavController().navigateUp()
            } else {
                viewModel.reiniciar()
            }
        }
        binding.buttonQuizFinish.setOnClickListener {
            findNavController().popBackStack(R.id.astronomiaRutaFragment, false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::render)
            }
        }
    }

    private fun render(state: AstronomyQuizUiState) {
        state.error?.let { error ->
            val message = when (error) {
                AstronomyQuizError.ANSWER_REQUIRED -> R.string.astronomy_answer_required
                AstronomyQuizError.SESSION_REQUIRED -> R.string.session_required_error
                AstronomyQuizError.SAVE_FAILED -> R.string.astronomy_quiz_save_error
            }
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
            viewModel.errorMostrado()
        }

        val result = state.resultado
        binding.quizContent.isVisible = result == null
        binding.quizResult.isVisible = result != null
        if (result != null) {
            binding.resultIcon.text = if (result.aprobado) "✨" else "🔭"
            binding.resultTitle.setText(
                if (result.aprobado) R.string.astronomy_quiz_passed else R.string.astronomy_quiz_retry_title
            )
            binding.resultScore.text = getString(
                R.string.astronomy_quiz_score,
                result.aciertos,
                result.total,
                (result.aciertos * 100) / result.total
            )
            binding.resultDescription.text = if (result.aprobado) {
                getString(R.string.astronomy_quiz_passed_description)
            } else {
                getString(
                    R.string.astronomy_quiz_weak_topics,
                    result.temasDebiles.joinToString { getString(R.string.astronomy_topic_short, it) }
                )
            }
            binding.buttonQuizRetry.isVisible = !result.aprobado
            binding.buttonQuizRetry.setText(R.string.astronomy_review_weak_topics)
            return
        }

        val pregunta = state.preguntas[state.preguntaActual]
        binding.quizProgress.setProgressCompat(
            ((state.preguntaActual + 1) * 100) / state.preguntas.size,
            true
        )
        binding.quizCounter.text = getString(
            R.string.astronomy_quiz_counter,
            state.preguntaActual + 1,
            state.preguntas.size
        )
        binding.quizQuestion.text = pregunta.enunciado

        rendering = true
        optionButtons.forEachIndexed { index, button ->
            button.text = pregunta.opciones[index]
            button.isEnabled = !state.mostrandoRetroalimentacion
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.dubalin_ink))
            button.buttonTintList = defaultOptionTints.getOrNull(index)
        }
        binding.quizOptions.clearCheck()
        state.opcionSeleccionada?.let { optionButtons[it].isChecked = true }
        rendering = false

        binding.quizFeedback.isVisible = state.mostrandoRetroalimentacion
        if (state.mostrandoRetroalimentacion) {
            val correcta = pregunta.respuestaCorrecta
            val seleccion = requireNotNull(state.opcionSeleccionada)
            val success = ContextCompat.getColor(requireContext(), R.color.feedback_success)
            val error = ContextCompat.getColor(requireContext(), R.color.md_error)
            optionButtons[correcta].setTextColor(success)
            optionButtons[correcta].buttonTintList = ColorStateList.valueOf(success)
            if (seleccion != correcta) {
                optionButtons[seleccion].setTextColor(error)
                optionButtons[seleccion].buttonTintList = ColorStateList.valueOf(error)
            }
            binding.quizFeedback.text = getString(
                if (seleccion == correcta) R.string.astronomy_feedback_correct
                else R.string.astronomy_feedback_incorrect,
                pregunta.explicacion
            )
        }

        binding.quizSaving.isVisible = state.isSaving
        binding.buttonQuizAction.isEnabled = !state.isSaving && state.opcionSeleccionada != null
        binding.buttonQuizAction.setText(
            when {
                !state.mostrandoRetroalimentacion -> R.string.astronomy_check_answer
                state.preguntaActual == state.preguntas.lastIndex -> R.string.astronomy_finish_exam
                else -> R.string.action_next
            }
        )
    }

    override fun onDestroyView() {
        defaultOptionTints = emptyList()
        super.onDestroyView()
        _binding = null
    }
}
