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
import com.dubalin.app.databinding.FragmentAstronomiaRutaBinding
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AstronomiaRutaFragment : Fragment(R.layout.fragment_astronomia_ruta) {

    private var _binding: FragmentAstronomiaRutaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AstronomiaRutaViewModel by viewModels()
    private val levelsAdapter = AstronomiaNivelesAdapter { nivel ->
        when (nivel.numero) {
            0 -> findNavController().navigate(
                if (viewModel.uiState.value.onboardingCompletado) R.id.action_ruta_to_nivel_cero
                else R.id.action_ruta_to_onboarding
            )
            1 -> findNavController().navigate(R.id.action_ruta_to_nivel_uno)
            2 -> findNavController().navigate(R.id.action_ruta_to_nivel_dos)
            3 -> findNavController().navigate(R.id.action_ruta_to_nivel_tres)
            4 -> findNavController().navigate(R.id.action_ruta_to_nivel_cuatro)
            5 -> findNavController().navigate(R.id.action_ruta_to_nivel_cinco)
            6 -> findNavController().navigate(R.id.action_ruta_to_nivel_seis)
            7 -> findNavController().navigate(R.id.action_ruta_to_nivel_siete)
            8 -> findNavController().navigate(R.id.action_ruta_to_nivel_ocho)
            9 -> findNavController().navigate(R.id.action_ruta_to_nivel_nueve)
            10 -> findNavController().navigate(R.id.action_ruta_to_nivel_diez)
            else -> Snackbar.make(requireView(), R.string.astronomy_next_level_coming, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAstronomiaRutaBinding.bind(view)

        binding.routeToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.levelsList.adapter = levelsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    levelsAdapter.submitList(state.niveles)
                    binding.routeProgress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    if (state.error) {
                        Snackbar.make(binding.root, R.string.astronomy_progress_error, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.levelsList.adapter = null
        super.onDestroyView()
        _binding = null
    }
}
