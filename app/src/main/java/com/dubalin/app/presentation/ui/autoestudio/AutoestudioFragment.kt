package com.dubalin.app.presentation.ui.autoestudio

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentAutoestudioBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Menú de Autoestudio con apuntes y flashcards locales.
 */
@AndroidEntryPoint
class AutoestudioFragment : Fragment(R.layout.fragment_autoestudio) {

    private var _binding: FragmentAutoestudioBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAutoestudioBinding.bind(view)
        binding.btnMaterias.setOnClickListener {
            findNavController().navigate(R.id.materiasFragment)
        }

        binding.cardMisApuntes.setOnClickListener {
            findNavController().navigate(R.id.action_autoestudio_to_secciones)
        }
        binding.cardFlashcards.setOnClickListener {
            findNavController().navigate(R.id.flashcardsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
