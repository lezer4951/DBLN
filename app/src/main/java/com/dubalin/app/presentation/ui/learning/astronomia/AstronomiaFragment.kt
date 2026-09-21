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
import com.dubalin.app.databinding.FragmentAstronomiaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AstronomiaFragment : Fragment(R.layout.fragment_astronomia) {

    private var _binding: FragmentAstronomiaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AstronomiaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAstronomiaBinding.bind(view)

        binding.astronomyToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.cardStudy.setOnClickListener {
            findNavController().navigate(R.id.action_astronomia_to_ruta)
        }

        binding.cardCuriosity.setOnClickListener { findNavController().navigate(R.id.action_astronomia_to_curiosidades) }
        binding.cardPractice.setOnClickListener { findNavController().navigate(R.id.action_astronomia_to_practica_libre) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.astronomyProgress.setProgressCompat(com.dubalin.app.domain.model.CatalogoAstronomia.porcentaje(state.nivelActual), true)
                    binding.astronomyProgressText.text = when {
                        state.nivelActual > 10 -> getString(R.string.astronomy_progress_completed, state.mejorPuntaje)
                        state.nivelCeroCompletado -> getString(R.string.astronomy_progress_unlocked, state.nivelActual, state.mejorPuntaje)
                        else -> getString(R.string.astronomy_progress_initial)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
