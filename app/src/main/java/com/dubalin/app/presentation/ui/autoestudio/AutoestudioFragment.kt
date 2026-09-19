package com.dubalin.app.presentation.ui.autoestudio

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentAutoestudioBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Menú de Autoestudio. Solo "Mis Apuntes" es funcional por ahora;
 * Laboratorio de Flashcards, Generador con IA y Repaso Inteligente
 * quedan como tarjetas "próximamente" hasta sus respectivos pasos.
 */
@AndroidEntryPoint
class AutoestudioFragment : Fragment(R.layout.fragment_autoestudio) {

    private var _binding: FragmentAutoestudioBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAutoestudioBinding.bind(view)

        binding.cardMisApuntes.setOnClickListener {
            findNavController().navigate(R.id.action_autoestudio_to_secciones)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
