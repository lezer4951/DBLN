package com.dubalin.app.presentation.ui.learning.astronomia

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentAstronomiaNivelCeroBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AstronomiaNivelDiezFragment : Fragment(R.layout.fragment_astronomia_nivel_cero) {
    private var _binding: FragmentAstronomiaNivelCeroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AstronomiaNivelDiezViewModel by viewModels()
    private var rendering = false; private var tints: List<ColorStateList?> = emptyList()
    private val options: List<RadioButton> get() = listOf(binding.selfOptionA, binding.selfOptionB, binding.selfOptionC)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState); _binding = FragmentAstronomiaNivelCeroBinding.bind(view)
        tints = options.map { it.buttonTintList }; binding.lessonToolbar.title = getString(R.string.astronomy_level_ten_title)
        binding.lessonToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.buttonPrevious.setOnClickListener { viewModel.anterior() }; binding.buttonBackToContent.setOnClickListener { viewModel.anterior() }
        binding.selfOptions.setOnCheckedChangeListener { _, id -> if (!rendering)
            options.indexOfFirst { it.id == id }.takeIf { it >= 0 }?.let(viewModel::seleccionar) }
        binding.selfOwnWords.doOnTextChanged { text, _, _, _ -> viewModel.actualizarExplicacion(text?.toString().orEmpty()) }
        binding.buttonSelfAction.setOnClickListener { viewModel.accionAutoevaluacion() }
        binding.buttonNext.setOnClickListener { val s = viewModel.state.value; when {
            s.enRepaso && s.ultimoRepaso -> findNavController().navigate(R.id.action_nivel_diez_to_examen)
            s.enRepaso -> viewModel.siguienteRepaso()
            !s.temaCompletado -> viewModel.iniciarAutoevaluacion()
            s.temaActual < s.sesiones.lastIndex -> viewModel.siguiente()
            !s.practicaCompletada -> findNavController().navigate(R.id.action_nivel_diez_to_practica)
            else -> findNavController().navigate(R.id.action_nivel_diez_to_examen)
        } }
        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<IntArray>("astronomia_nivel_diez_repaso")?.observe(viewLifecycleOwner) { temas ->
                viewModel.iniciarRepaso(temas.toList())
                findNavController().currentBackStackEntry?.savedStateHandle?.remove<IntArray>("astronomia_nivel_diez_repaso")
            }
        viewLifecycleOwner.lifecycleScope.launch { viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { viewModel.state.collect(::render) } }
    }
    private fun render(s: AstronomiaNivelDiezUiState) {
        binding.lessonLoading.isVisible = s.isLoading; binding.lessonContent.isVisible = !s.isLoading && s.fase == FaseSesion.CONTENIDO
        binding.selfAssessment.isVisible = !s.isLoading && s.fase == FaseSesion.AUTOEVALUACION
        binding.lessonFooter.isVisible = !s.isLoading && s.fase == FaseSesion.CONTENIDO; if (s.isLoading) return
        if (s.error) { Snackbar.make(binding.root, R.string.astronomy_progress_error, Snackbar.LENGTH_LONG).show(); viewModel.errorMostrado() }
        if (s.temaCompletadoReciente) { Snackbar.make(binding.root, R.string.astronomy_topic_mastered, Snackbar.LENGTH_SHORT).show(); viewModel.consumirEvento() }
        binding.lessonProgress.setProgressCompat((s.temaActual + 1) * 100 / s.sesiones.size, true)
        if (s.fase == FaseSesion.CONTENIDO) contenido(s) else pregunta(s)
    }
    private fun contenido(state: AstronomiaNivelDiezUiState) = with(binding) {
        val s = state.sesion; lessonSection.text = getString(R.string.astronomy_level_ten_topic, s.numero); lessonTitle.text = s.titulo
        lessonCounter.text = getString(R.string.astronomy_lesson_counter, s.numero, state.sesiones.size)
        lessonDuration.text = getString(R.string.astronomy_session_duration, s.duracionMinutos)
        lessonFormat.text = getString(R.string.astronomy_aster_guided); lessonFormatHint.setText(R.string.astronomy_level_ten_hint)
        lessonObjective.text = s.objetivo; lessonImportance.text = s.porQueImporta; lessonAnalogy.text = s.analogia
        lessonBody.text = s.explicacion; lessonExample.text = s.ejemploVisual; lessonKeyIdea.text = s.ideaClave
        buttonPrevious.isEnabled = !state.enRepaso && state.temaActual > 0
        buttonNext.setText(when { state.enRepaso && state.ultimoRepaso -> R.string.astronomy_retry_exam
            state.enRepaso -> R.string.astronomy_next_review_topic; !state.temaCompletado -> R.string.astronomy_start_self_check
            state.temaActual < state.sesiones.lastIndex -> R.string.astronomy_next_topic
            !state.practicaCompletada -> R.string.astronomy_go_to_cumulative_practice; else -> R.string.astronomy_go_to_exam })
    }
    private fun pregunta(s: AstronomiaNivelDiezUiState) {
        val q = s.pregunta ?: return; binding.selfCounter.text = getString(R.string.astronomy_self_counter, s.preguntaActual + 1, s.sesion.autoevaluacion.size)
        binding.selfQuestion.text = q.enunciado; rendering = true; binding.selfOptions.clearCheck()
        options.forEachIndexed { i, b -> b.text = q.opciones[i]; b.isEnabled = s.respuestaCorrecta == null
            b.setTextColor(ContextCompat.getColor(requireContext(), R.color.dubalin_ink)); b.buttonTintList = tints[i] }
        s.opcionSeleccionada?.let { options[it].isChecked = true }; rendering = false; binding.selfFeedback.isVisible = s.respuestaCorrecta != null
        s.respuestaCorrecta?.let { ok -> binding.selfFeedback.text = getString(if (ok) R.string.astronomy_feedback_correct else R.string.astronomy_feedback_reframed,
            if (ok) q.explicacion else q.explicacionAlternativa) }
        val palabras = s.respuestaCorrecta == true && s.preguntaActual == s.sesion.autoevaluacion.lastIndex
        binding.selfOwnWordsLayout.isVisible = palabras; if (binding.selfOwnWords.text?.toString() != s.explicacionPropia) binding.selfOwnWords.setText(s.explicacionPropia)
        binding.selfSaving.isVisible = s.isSaving; binding.buttonSelfAction.isEnabled = !s.isSaving && s.opcionSeleccionada != null && (!palabras || s.explicacionPropia.trim().length >= 12)
        binding.buttonSelfAction.setText(when { s.respuestaCorrecta == false -> R.string.astronomy_try_again; palabras -> R.string.astronomy_complete_topic
            s.respuestaCorrecta == true -> R.string.action_continue; else -> R.string.astronomy_check_answer })
    }
    override fun onDestroyView() { tints = emptyList(); super.onDestroyView(); _binding = null }
}
