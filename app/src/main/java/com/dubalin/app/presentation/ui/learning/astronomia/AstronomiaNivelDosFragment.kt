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
class AstronomiaNivelDosFragment : Fragment(R.layout.fragment_astronomia_nivel_cero) {
    private var _binding: FragmentAstronomiaNivelCeroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AstronomiaNivelDosViewModel by viewModels()
    @javax.inject.Inject lateinit var sessionRepository: com.dubalin.app.domain.repository.SessionRepository
    private var secciones: SeccionesLeccion? = null
    private var rendering = false
    private var tints: List<ColorStateList?> = emptyList()
    private val options: List<RadioButton> get() = listOf(binding.selfOptionA, binding.selfOptionB, binding.selfOptionC)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState); _binding = FragmentAstronomiaNivelCeroBinding.bind(view)
        secciones = SeccionesLeccion(binding, sessionRepository.getUsuarioId(), 2)
        tints = options.map { it.buttonTintList }
        binding.lessonToolbar.title = getString(R.string.astronomy_level_two_title)
        binding.lessonToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.buttonPrevious.setOnClickListener { viewModel.anterior() }
        binding.buttonBackToContent.setOnClickListener { viewModel.anterior() }
        binding.selfOptions.setOnCheckedChangeListener { _, id -> if (!rendering)
            options.indexOfFirst { it.id == id }.takeIf { it >= 0 }?.let(viewModel::seleccionar) }
        binding.selfOwnWords.doOnTextChanged { text, _, _, _ -> viewModel.actualizarExplicacion(text?.toString().orEmpty()) }
        binding.buttonSelfAction.setOnClickListener { viewModel.accionAutoevaluacion() }
        binding.buttonNext.setOnClickListener {
            val s = viewModel.state.value
            when {
                s.enRepaso && s.ultimoRepaso -> findNavController().navigate(R.id.action_nivel_dos_to_examen)
                s.enRepaso -> viewModel.siguienteRepaso()
                !s.temaCompletado -> viewModel.iniciarAutoevaluacion()
                s.temaActual < s.sesiones.lastIndex -> viewModel.siguiente()
                !s.practicaCompletada -> findNavController().navigate(R.id.action_nivel_dos_to_practica)
                else -> findNavController().navigate(R.id.action_nivel_dos_to_examen)
            }
        }
        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<IntArray>("astronomia_nivel_dos_repaso")?.observe(viewLifecycleOwner) { temas ->
                viewModel.iniciarRepaso(temas.toList())
                findNavController().currentBackStackEntry?.savedStateHandle?.remove<IntArray>("astronomia_nivel_dos_repaso")
            }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { viewModel.state.collect(::render) }
        }
    }

    private fun render(state: AstronomiaNivelDosUiState) {
        binding.lessonLoading.isVisible = state.isLoading
        binding.lessonContent.isVisible = !state.isLoading && state.fase == FaseSesion.CONTENIDO
        binding.selfAssessment.isVisible = !state.isLoading && state.fase == FaseSesion.AUTOEVALUACION
        binding.lessonFooter.isVisible = !state.isLoading && state.fase == FaseSesion.CONTENIDO
        if (state.isLoading) return
        if (state.error) { Snackbar.make(binding.root, R.string.astronomy_progress_error, Snackbar.LENGTH_LONG).show(); viewModel.errorMostrado() }
        if (state.temaCompletadoReciente) { Snackbar.make(binding.root, R.string.astronomy_topic_mastered, Snackbar.LENGTH_SHORT).show(); viewModel.consumirEvento() }
        binding.lessonProgress.setProgressCompat((state.temaActual + 1) * 100 / state.sesiones.size, true)
        if (state.fase == FaseSesion.CONTENIDO) contenido(state) else pregunta(state)
        if (state.fase == FaseSesion.CONTENIDO) secciones?.render(state.temaActual)
    }

    private fun contenido(state: AstronomiaNivelDosUiState) = with(binding) {
        val s = state.sesion
        lessonSection.text = getString(R.string.astronomy_level_two_topic, s.numero); lessonTitle.text = s.titulo
        lessonCounter.text = getString(R.string.astronomy_lesson_counter, s.numero, state.sesiones.size)
        lessonDuration.text = getString(R.string.astronomy_session_duration, s.duracionMinutos)
        lessonFormat.text = getString(R.string.astronomy_aster_guided); lessonFormatHint.setText(R.string.astronomy_level_two_hint)
        lessonObjective.text = s.objetivo; lessonImportance.text = s.porQueImporta; lessonAnalogy.text = s.analogia
        lessonBody.text = s.explicacion; lessonExample.text = s.ejemploVisual; lessonKeyIdea.text = s.ideaClave
        buttonPrevious.isEnabled = !state.enRepaso && state.temaActual > 0
        buttonNext.setText(when {
            state.enRepaso && state.ultimoRepaso -> R.string.astronomy_retry_exam
            state.enRepaso -> R.string.astronomy_next_review_topic
            !state.temaCompletado -> R.string.astronomy_start_self_check
            state.temaActual < state.sesiones.lastIndex -> R.string.astronomy_next_topic
            !state.practicaCompletada -> R.string.astronomy_go_to_cumulative_practice
            else -> R.string.astronomy_go_to_exam
        })
    }

    private fun pregunta(state: AstronomiaNivelDosUiState) {
        val q = state.pregunta ?: return
        binding.selfCounter.text = getString(R.string.astronomy_self_counter, state.preguntaActual + 1, state.sesion.autoevaluacion.size)
        binding.selfQuestion.text = q.enunciado; rendering = true; binding.selfOptions.clearCheck()
        options.forEachIndexed { i, b -> b.text = q.opciones[i]; b.isEnabled = state.respuestaCorrecta == null
            b.setTextColor(ContextCompat.getColor(requireContext(), R.color.dubalin_ink)); b.buttonTintList = tints[i] }
        state.opcionSeleccionada?.let { options[it].isChecked = true }; rendering = false
        binding.selfFeedback.isVisible = state.respuestaCorrecta != null
        state.respuestaCorrecta?.let { ok -> binding.selfFeedback.text = getString(
            if (ok) R.string.astronomy_feedback_correct else R.string.astronomy_feedback_reframed,
            if (ok) q.explicacion else q.explicacionAlternativa) }
        val palabras = state.respuestaCorrecta == true && state.preguntaActual == state.sesion.autoevaluacion.lastIndex
        binding.selfOwnWordsLayout.isVisible = palabras
        if (binding.selfOwnWords.text?.toString() != state.explicacionPropia) binding.selfOwnWords.setText(state.explicacionPropia)
        binding.selfSaving.isVisible = state.isSaving
        binding.buttonSelfAction.isEnabled = !state.isSaving && state.opcionSeleccionada != null && (!palabras || state.explicacionPropia.trim().length >= 12)
        binding.buttonSelfAction.setText(when { state.respuestaCorrecta == false -> R.string.astronomy_try_again
            palabras -> R.string.astronomy_complete_topic; state.respuestaCorrecta == true -> R.string.action_continue
            else -> R.string.astronomy_check_answer })
    }

    override fun onDestroyView() { secciones = null; tints = emptyList(); super.onDestroyView(); _binding = null }
}
