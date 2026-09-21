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
class AstronomiaNivelSieteExamenFragment : Fragment(R.layout.fragment_astronomia_quiz) {
    private var _binding: FragmentAstronomiaQuizBinding? = null; private val binding get() = _binding!!
    private val viewModel: AstronomiaNivelSieteExamenViewModel by viewModels(); private var rendering = false
    private var tints: List<ColorStateList?> = emptyList(); private val options: List<RadioButton> get() = listOf(binding.optionA, binding.optionB, binding.optionC, binding.optionD)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAstronomiaQuizBinding.bind(view); tints = options.map { it.buttonTintList }
        binding.quizToolbar.title = getString(R.string.astronomy_level_seven_exam); binding.quizToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.quizOptions.setOnCheckedChangeListener { _, id -> if (!rendering) options.indexOfFirst { it.id == id }.takeIf { it >= 0 }?.let(viewModel::seleccionar) }
        binding.buttonQuizAction.setOnClickListener { viewModel.accion() }; binding.buttonQuizFinish.setOnClickListener { findNavController().popBackStack(R.id.astronomiaRutaFragment, false) }
        binding.buttonQuizRetry.setOnClickListener { val weak = viewModel.state.value.resultado?.temasDebiles.orEmpty()
            if (weak.isEmpty()) viewModel.reiniciar() else { findNavController().getBackStackEntry(R.id.astronomiaNivelSieteFragment).savedStateHandle
                .set("astronomia_nivel_siete_repaso", weak.map { it - 1 }.toIntArray()); findNavController().popBackStack(R.id.astronomiaNivelSieteFragment, false) } }
        viewLifecycleOwner.lifecycleScope.launch { viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { viewModel.state.collect(::render) } }
    }
    private fun render(s: NivelSieteExamenUiState) {
        if (s.error) { Snackbar.make(binding.root, R.string.astronomy_quiz_save_error, Snackbar.LENGTH_LONG).show(); viewModel.errorMostrado() }
        val result = s.resultado; binding.quizContent.isVisible = result == null; binding.quizResult.isVisible = result != null
        if (result != null) { binding.resultIcon.text = if (result.aprobado) "⭐" else "🔬"
            binding.resultTitle.setText(if (result.aprobado) R.string.astronomy_level_seven_passed else R.string.astronomy_quiz_retry_title)
            binding.resultScore.text = getString(R.string.astronomy_quiz_score, result.aciertos, result.total, result.aciertos * 100 / result.total)
            binding.resultDescription.text = if (result.aprobado) getString(R.string.astronomy_level_seven_passed_description)
                else getString(R.string.astronomy_quiz_weak_topics, result.temasDebiles.joinToString { getString(R.string.astronomy_topic_short, it) })
            binding.buttonQuizFinish.setText(R.string.astronomy_back_to_route); binding.buttonQuizRetry.isVisible = !result.aprobado
            binding.buttonQuizRetry.setText(R.string.astronomy_review_weak_topics); return }
        val q = s.preguntas[s.actual]; binding.quizProgress.setProgressCompat((s.actual + 1) * 100 / s.preguntas.size, true)
        binding.quizCounter.text = getString(R.string.astronomy_quiz_counter, s.actual + 1, s.preguntas.size); binding.quizQuestion.text = q.enunciado
        rendering = true; binding.quizOptions.clearCheck(); options.forEachIndexed { i, b -> b.text = q.opciones[i]; b.isEnabled = s.feedback == null
            b.setTextColor(ContextCompat.getColor(requireContext(), R.color.dubalin_ink)); b.buttonTintList = tints[i] }
        s.seleccion?.let { options[it].isChecked = true }; rendering = false; binding.quizFeedback.isVisible = s.feedback != null
        s.feedback?.let { ok -> binding.quizFeedback.text = getString(if (ok) R.string.astronomy_feedback_correct else R.string.astronomy_feedback_incorrect, q.explicacion) }
        binding.quizSaving.isVisible = s.guardando; binding.buttonQuizAction.isEnabled = !s.guardando && s.seleccion != null
        binding.buttonQuizAction.setText(if (s.feedback == null) R.string.astronomy_check_answer else if (s.actual == s.preguntas.lastIndex) R.string.astronomy_finish_exam else R.string.action_next)
    }
    override fun onDestroyView() { tints = emptyList(); super.onDestroyView(); _binding = null }
}
