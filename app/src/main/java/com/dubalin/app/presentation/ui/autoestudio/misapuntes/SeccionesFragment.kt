package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentSeccionesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeccionesFragment : Fragment(R.layout.fragment_secciones) {

    private var _binding: FragmentSeccionesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SeccionesViewModel by viewModels()

    private val adapter = SeccionesAdapter(
        onClick = { seccion ->
            findNavController().navigate(
                R.id.action_secciones_to_apuntes,
                bundleOf("seccionId" to seccion.id, "seccionNombre" to seccion.nombre)
            )
        },
        onLongClick = { seccion ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Eliminar sección")
                .setMessage("Se eliminará \"${seccion.nombre}\" y todos sus apuntes. ¿Continuar?")
                .setPositiveButton("Eliminar") { _, _ -> viewModel.eliminarSeccion(seccion) }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSeccionesBinding.bind(view)

        binding.rvSecciones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSecciones.adapter = adapter

        binding.fabCrearSeccion.setOnClickListener { mostrarDialogoCrear() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.secciones.collect { lista ->
                    adapter.submitList(lista)
                    binding.tvSeccionesVacio.isVisible = lista.isEmpty()
                }
            }
        }
    }

    private fun mostrarDialogoCrear() {
        val input = EditText(requireContext())
        input.hint = "Nombre de la sección (ej. Inglés)"

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Nueva sección")
            .setView(input)
            .setPositiveButton("Crear") { _, _ ->
                viewModel.crearSeccion(input.text?.toString().orEmpty())
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
