package com.dubalin.app.presentation.ui.perfil

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentPerfilBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Placeholder del Paso 3.1, con logout funcional (heredado del
 * HomePlaceholderFragment del Módulo 2). Contenido real (estadísticas,
 * logros, configuración) se construye en el Módulo 7.
 *
 * IMPORTANTE: navega usando el NavController del grafo RAÍZ
 * (requireActivity().findNavController(R.id.nav_host_fragment)), no el
 * de este NavHostFragment anidado -- la acción action_home_to_login vive
 * en nav_graph.xml, no en nav_graph_home.xml.
 */
@AndroidEntryPoint
class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PerfilViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPerfilBinding.bind(view)

        binding.btnCerrarSesion.setOnClickListener {
            viewModel.cerrarSesion()
            requireActivity()
                .findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_home_to_login)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
