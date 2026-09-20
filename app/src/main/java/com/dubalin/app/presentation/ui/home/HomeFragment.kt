package com.dubalin.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentHomeTabBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home_tab) {

    private var _binding: FragmentHomeTabBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeTabBinding.bind(view)

        binding.btnHomeSubjects.setOnClickListener { findNavController().navigate(R.id.autoestudioFragment); findNavController().navigate(R.id.materiasFragment) }
        binding.btnHomeFlashcards.setOnClickListener { findNavController().navigate(R.id.autoestudioFragment); findNavController().navigate(R.id.flashcardsFragment) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvSaludo.text = getString(R.string.home_saludo, state.nombre)
                    binding.tvNivelXp.text =
                        getString(R.string.home_nivel_xp, state.nivel, state.xpTotal)
                    binding.tvRachaDias.text =
                        resources.getQuantityString(
                            R.plurals.home_racha_dias,
                            state.rachaDias,
                            state.rachaDias
                        )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
