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
import com.dubalin.app.databinding.FragmentApuntesDetalleBinding
import com.dubalin.app.domain.model.Apunte
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApuntesDetalleFragment : Fragment(R.layout.fragment_apuntes_detalle) {

    private var _binding: FragmentApuntesDetalleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ApuntesDetalleViewModel by viewModels()

    private val adapter = ApuntesAdapter(
        onClick = ::abrirEditor,
        onActionsClick = ::mostrarAcciones
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentApuntesDetalleBinding.bind(view)

        binding.toolbarApuntes.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.rvApuntes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvApuntes.adapter = adapter
        binding.fabCrearApunte.setOnClickListener {
            abrirEditor(null)
        }

        observarEstado()
    }

    private fun observarEstado() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.toolbarApuntes.title = state.seccionNombre
                    adapter.submitList(state.apuntes)
                    binding.progressApuntes.isVisible = state.isLoading
                    binding.rvApuntes.isVisible =
                        !state.isLoading && state.apuntes.isNotEmpty()
                    binding.tvApuntesVacio.isVisible =
                        !state.isLoading && state.apuntes.isEmpty()
                    binding.fabCrearApunte.isEnabled =
                        !state.isLoading && !state.isDeleting

                    state.message?.let(::mostrarMensaje)
                }
            }
        }
    }

    private fun abrirEditor(apunte: Apunte?) {
        val state = viewModel.uiState.value
        findNavController().navigate(
            R.id.action_apuntes_to_editor,
            bundleOf(
                "seccionId" to state.seccionId,
                "apunteId" to (apunte?.id ?: 0)
            )
        )
    }

    private fun mostrarAcciones(apunte: Apunte) {
        val acciones = arrayOf(
            getString(R.string.action_edit),
            getString(R.string.action_delete)
        )
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(apunte.titulo)
            .setItems(acciones) { _, index ->
                when (index) {
                    0 -> abrirEditor(apunte)
                    1 -> confirmarEliminacion(apunte)
                }
            }
            .show()
    }

    private fun confirmarEliminacion(apunte: Apunte) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.apunte_delete_title)
            .setMessage(getString(R.string.apunte_delete_message, apunte.titulo))
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.action_delete) { _, _ ->
                viewModel.eliminarApunte(apunte)
            }
            .show()
    }

    private fun mostrarMensaje(message: ApuntesMessage) {
        val textRes = when (message) {
            ApuntesMessage.DELETED -> R.string.apunte_deleted
            ApuntesMessage.NOTE_NOT_FOUND -> R.string.apunte_not_found_error
            ApuntesMessage.LOAD_ERROR -> R.string.apuntes_load_error
            ApuntesMessage.OPERATION_ERROR -> R.string.apuntes_operation_error
        }
        Snackbar.make(binding.root, textRes, Snackbar.LENGTH_SHORT).show()
        viewModel.messageShown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
