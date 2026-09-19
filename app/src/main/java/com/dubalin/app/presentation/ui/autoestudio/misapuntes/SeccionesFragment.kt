package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import android.os.Bundle
import android.view.View
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
import com.dubalin.app.databinding.DialogSeccionBinding
import com.dubalin.app.databinding.FragmentSeccionesBinding
import com.dubalin.app.domain.model.SeccionApuntes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
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
                bundleOf(
                    "seccionId" to seccion.id,
                    "seccionNombre" to seccion.nombre
                )
            )
        },
        onActionsClick = ::mostrarAcciones
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSeccionesBinding.bind(view)

        binding.rvSecciones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSecciones.adapter = adapter
        binding.fabCrearSeccion.setOnClickListener {
            mostrarDialogoSeccion()
        }

        observarEstado()
    }

    private fun observarEstado() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    adapter.submitList(state.secciones)

                    binding.progressSecciones.isVisible = state.isLoading
                    binding.rvSecciones.isVisible =
                        !state.isLoading && state.secciones.isNotEmpty()
                    binding.tvSeccionesVacio.isVisible =
                        !state.isLoading && state.secciones.isEmpty()
                    binding.fabCrearSeccion.isEnabled =
                        !state.isLoading && !state.isSaving

                    state.message?.let(::mostrarMensaje)
                }
            }
        }
    }

    private fun mostrarAcciones(seccion: SeccionApuntes) {
        val acciones = arrayOf(
            getString(R.string.action_edit),
            getString(R.string.action_delete)
        )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(seccion.nombre)
            .setItems(acciones) { _, index ->
                when (index) {
                    0 -> mostrarDialogoSeccion(seccion)
                    1 -> confirmarEliminacion(seccion)
                }
            }
            .show()
    }

    private fun mostrarDialogoSeccion(seccion: SeccionApuntes? = null) {
        val dialogBinding = DialogSeccionBinding.inflate(layoutInflater)
        dialogBinding.etNombreSeccion.setText(seccion?.nombre)
        dialogBinding.etNombreSeccion.setSelection(
            dialogBinding.etNombreSeccion.text?.length ?: 0
        )

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(
                if (seccion == null) {
                    R.string.seccion_new_title
                } else {
                    R.string.seccion_edit_title
                }
            )
            .setView(dialogBinding.root)
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(
                if (seccion == null) R.string.action_create else R.string.action_save,
                null
            )
            .create()

        dialog.setOnShowListener {
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {
                    val nombre = dialogBinding.etNombreSeccion.text
                        ?.toString()
                        .orEmpty()

                    if (nombre.isBlank()) {
                        dialogBinding.tilNombreSeccion.error =
                            getString(R.string.seccion_name_required)
                    } else {
                        dialogBinding.tilNombreSeccion.error = null
                        viewModel.guardarSeccion(seccion, nombre)
                        dialog.dismiss()
                    }
                }
        }

        dialog.show()
    }

    private fun confirmarEliminacion(seccion: SeccionApuntes) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.seccion_delete_title)
            .setMessage(getString(R.string.seccion_delete_message, seccion.nombre))
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.action_delete) { _, _ ->
                viewModel.eliminarSeccion(seccion)
            }
            .show()
    }

    private fun mostrarMensaje(message: SeccionesMessage) {
        val textRes = when (message) {
            SeccionesMessage.CREATED -> R.string.seccion_created
            SeccionesMessage.UPDATED -> R.string.seccion_updated
            SeccionesMessage.DELETED -> R.string.seccion_deleted
            SeccionesMessage.DUPLICATE_NAME -> R.string.seccion_duplicate_error
            SeccionesMessage.SECTION_NOT_FOUND -> R.string.seccion_not_found_error
            SeccionesMessage.SESSION_REQUIRED -> R.string.session_required_error
            SeccionesMessage.LOAD_ERROR -> R.string.secciones_load_error
            SeccionesMessage.OPERATION_ERROR -> R.string.secciones_operation_error
        }

        Snackbar.make(binding.root, textRes, Snackbar.LENGTH_SHORT).show()
        viewModel.messageShown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
