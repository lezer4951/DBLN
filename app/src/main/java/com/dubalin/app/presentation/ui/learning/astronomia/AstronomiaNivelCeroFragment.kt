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
class AstronomiaNivelCeroFragment : Fragment(R.layout.fragment_astronomia_nivel_cero) {
    private var _binding: FragmentAstronomiaNivelCeroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AstronomiaNivelCeroViewModel by viewModels()
    @javax.inject.Inject lateinit var sessionRepository: com.dubalin.app.domain.repository.SessionRepository
    private var secciones: SeccionesLeccion? = null

    private var defaultTints: List<ColorStateList?> = emptyList()
    private val options: List<RadioButton> get() = listOf(binding.selfOptionA, binding.selfOptionB, binding.selfOptionC)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAstronomiaNivelCeroBinding.bind(view)
        secciones = SeccionesLeccion(binding, sessionRepository.getUsuarioId(), 0)
        defaultTints = options.map { it.buttonTintList }
        binding.lessonToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.buttonPrevious.setOnClickListener { viewModel.paginaAnterior() }
        binding.buttonBackToContent.setOnClickListener { viewModel.paginaAnterior() }
        binding.selfOptions.onAnswerSelected(viewModel::seleccionarOpcion)
        binding.buttonSelfAction.setOnClickListener { viewModel.comprobarOContinuar() }
        binding.selfOwnWords.doOnTextChanged { text, _, _, _ ->
            viewModel.actualizarExplicacionPropia(text?.toString().orEmpty())
        }
        binding.buttonNext.setOnClickListener {
            val state = viewModel.uiState.value
            when {
                state.enRepaso && state.ultimoTemaDeRepaso -> findNavController().navigate(R.id.action_nivel_cero_to_quiz)
                state.enRepaso -> viewModel.avanzarRepaso()
                !state.temaActualCompletado -> viewModel.iniciarAutoevaluacion()
                state.temaActual == state.sesiones.lastIndex -> findNavController().navigate(R.id.action_nivel_cero_to_quiz)
                else -> viewModel.paginaSiguiente()
            }
        }
        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<IntArray>("astronomia_temas_repaso")
            ?.observe(viewLifecycleOwner) { temas ->
                viewModel.iniciarRepaso(temas.toList())
                findNavController().currentBackStackEntry?.savedStateHandle?.remove<IntArray>("astronomia_temas_repaso")
            }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { viewModel.uiState.collect(::render) }
        }
    }

    private fun render(state: AstronomiaNivelCeroUiState) {
        binding.lessonLoading.isVisible = state.isLoading
        binding.lessonContent.isVisible = !state.isLoading && state.fase == FaseSesion.CONTENIDO
        binding.selfAssessment.isVisible = !state.isLoading && state.fase == FaseSesion.AUTOEVALUACION
        binding.lessonFooter.isVisible = !state.isLoading && state.fase == FaseSesion.CONTENIDO
        if (state.isLoading) return
        if (state.error) {
            Snackbar.make(binding.root, R.string.astronomy_progress_error, Snackbar.LENGTH_LONG).show()
            viewModel.errorMostrado()
        }
        if (state.temaCompletadoReciente) {
            Snackbar.make(binding.root, R.string.astronomy_topic_mastered, Snackbar.LENGTH_SHORT).show()
            viewModel.consumirTemaCompletado()
        }
        binding.lessonProgress.setProgressCompat(((state.temaActual + 1) * 100) / state.sesiones.size, true)
        if (state.fase == FaseSesion.CONTENIDO) renderContent(state) else renderAssessment(state)
        if (state.fase == FaseSesion.CONTENIDO) secciones?.render(state.temaActual)
    }

    private fun renderContent(state: AstronomiaNivelCeroUiState) {
        val session = state.sesionActual

        binding.lessonObjective.text = session.objetivo
        binding.lessonImportance.text = session.porQueImporta
        binding.lessonAnalogy.text = session.analogia
        binding.lessonBody.text = session.explicacion
        binding.lessonExample.text = session.ejemploVisual
        binding.lessonKeyIdea.text = session.ideaClave

        binding.buttonPrevious.isEnabled = !state.enRepaso && state.temaActual > 0
        binding.buttonNext.setText(when {
            state.enRepaso && state.ultimoTemaDeRepaso -> R.string.astronomy_retry_exam
            state.enRepaso -> R.string.astronomy_next_review_topic
            !state.temaActualCompletado -> R.string.astronomy_start_self_check
            state.temaActual == state.sesiones.lastIndex -> R.string.astronomy_go_to_exam
            else -> R.string.astronomy_next_topic
        })
    }

    private fun renderAssessment(state: AstronomiaNivelCeroUiState) {
        val question = state.pregunta ?: return
        binding.selfCounter.text = getString(R.string.astronomy_self_counter, state.preguntaActual + 1, state.sesionActual.autoevaluacion.size)
        binding.selfQuestion.text = question.enunciado

        binding.selfOptions.clearCheck()
        options.forEachIndexed { index, button ->
            button.text = question.opciones[index]
            button.isEnabled = state.respuestaCorrecta == null
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.dubalin_ink))
            button.buttonTintList = defaultTints.getOrNull(index)
        }
        binding.selfOptions.check(state.opcionSeleccionada?.let { options[it].id } ?: View.NO_ID)

        binding.selfFeedback.isVisible = state.respuestaCorrecta != null
        state.respuestaCorrecta?.let { correct ->
            val color = ContextCompat.getColor(requireContext(), if (correct) R.color.feedback_success else R.color.md_error)
            state.opcionSeleccionada?.let { options[it].setTextColor(color) }
            binding.selfFeedback.text = if (correct) {
                getString(R.string.astronomy_feedback_correct, question.explicacion)
            } else {
                getString(R.string.astronomy_feedback_reframed, question.explicacionAlternativa)
            }
        }
        binding.selfSaving.isVisible = state.isSaving
        val pideExplicacionPropia = state.respuestaCorrecta == true &&
            state.preguntaActual == state.sesionActual.autoevaluacion.lastIndex
        binding.selfOwnWordsLayout.isVisible = pideExplicacionPropia
        if (binding.selfOwnWords.text?.toString() != state.explicacionPropia) {
            binding.selfOwnWords.setText(state.explicacionPropia)
            binding.selfOwnWords.setSelection(state.explicacionPropia.length)
        }
        binding.buttonSelfAction.isEnabled = !state.isSaving &&
            (state.opcionSeleccionada != null || state.respuestaCorrecta != null) &&
            (!pideExplicacionPropia || state.explicacionPropia.trim().length >= 12)
        binding.buttonSelfAction.setText(when {
            state.respuestaCorrecta == false -> R.string.astronomy_try_again
            state.respuestaCorrecta == true && state.preguntaActual == state.sesionActual.autoevaluacion.lastIndex -> R.string.astronomy_complete_topic
            state.respuestaCorrecta == true -> R.string.action_continue
            else -> R.string.astronomy_check_answer
        })
    }

    override fun onDestroyView() { secciones = null;
        defaultTints = emptyList()
        super.onDestroyView()
        _binding = null
    }
}
