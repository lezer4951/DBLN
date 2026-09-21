package com.dubalin.app.presentation.ui.learning.astronomia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentAstronomiaOnboardingBinding
import com.dubalin.app.domain.model.ExperienciaAstronomia
import com.dubalin.app.domain.model.FormatoAprendizaje
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AstronomiaOnboardingFragment : Fragment(R.layout.fragment_astronomia_onboarding) {
    private var _binding: FragmentAstronomiaOnboardingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AstronomiaOnboardingViewModel by viewModels()
    private var navigated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAstronomiaOnboardingBinding.bind(view)
        binding.onboardingToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.experienceGroup.setOnCheckedChangeListener { _, id ->
            viewModel.seleccionarExperiencia(
                if (id == R.id.experience_some) ExperienciaAstronomia.CONOCIMIENTOS_PREVIOS
                else ExperienciaAstronomia.PRIMERA_VEZ
            )
        }
        binding.timeGroup.setOnCheckedChangeListener { _, id ->
            val minutes = when (id) {
                R.id.time_15 -> 15
                R.id.time_45 -> 45
                R.id.time_60 -> 60
                else -> 30
            }
            viewModel.seleccionarTiempo(minutes)
        }
        binding.formatGroup.setOnCheckedChangeListener { _, id ->
            val format = when (id) {
                R.id.format_text -> FormatoAprendizaje.TEXTO
                R.id.format_visual -> FormatoAprendizaje.VISUAL
                R.id.format_quiz -> FormatoAprendizaje.QUIZ
                else -> FormatoAprendizaje.EQUILIBRADO
            }
            viewModel.seleccionarFormato(format)
        }
        binding.buttonCreatePlan.setOnClickListener { viewModel.guardar() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::render)
            }
        }
    }

    private fun render(state: AstronomiaOnboardingUiState) {
        binding.planSummary.text = getString(
            R.string.astronomy_plan_summary,
            state.plan.sesionesEstimadas,
            state.plan.diasEstimados,
            state.plan.temasPorDia
        )
        binding.buttonCreatePlan.isEnabled = !state.isSaving
        binding.onboardingSaving.visibility = if (state.isSaving) View.VISIBLE else View.GONE
        if (state.error) {
            Snackbar.make(binding.root, R.string.astronomy_plan_save_error, Snackbar.LENGTH_LONG).show()
            viewModel.errorMostrado()
        }
        if (state.guardado && !navigated) {
            navigated = true
            findNavController().navigate(R.id.action_onboarding_to_nivel_cero)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
