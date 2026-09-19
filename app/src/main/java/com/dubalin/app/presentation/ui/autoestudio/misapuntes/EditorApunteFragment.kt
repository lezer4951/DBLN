package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentEditorApunteBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditorApunteFragment : Fragment(R.layout.fragment_editor_apunte) {

    private var _binding: FragmentEditorApunteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditorApunteViewModel by viewModels()
    private var formInitialized = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditorApunteBinding.bind(view)

        binding.toolbarEditorApunte.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnGuardarApunte.setOnClickListener {
            guardar()
        }

        observarEstado()
    }

    private fun observarEstado() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.toolbarEditorApunte.setTitle(
                        if (state.isEditing) {
                            R.string.apunte_edit_title
                        } else {
                            R.string.apunte_new_title
                        }
                    )
                    binding.progressEditorApunte.isVisible = state.isLoading
                    binding.formEditorApunte.isVisible = !state.isLoading
                    binding.btnGuardarApunte.isEnabled =
                        !state.isLoading && !state.isSaving
                    binding.progressGuardarApunte.isVisible = state.isSaving

                    if (!formInitialized && state.apunte != null) {
                        binding.etTituloApunte.setText(state.apunte.titulo)
                        binding.etContenidoApunte.setText(state.apunte.contenido)
                        formInitialized = true
                    }

                    state.message?.let(::mostrarMensaje)
                }
            }
        }
    }

    private fun guardar() {
        val titulo = binding.etTituloApunte.text?.toString().orEmpty()
        val contenido = binding.etContenidoApunte.text?.toString().orEmpty()

        binding.tilTituloApunte.error = if (titulo.isBlank()) {
            getString(R.string.apunte_title_required)
        } else {
            null
        }
        binding.tilContenidoApunte.error = if (contenido.isBlank()) {
            getString(R.string.apunte_content_required)
        } else {
            null
        }

        if (titulo.isNotBlank() && contenido.isNotBlank()) {
            viewModel.guardar(titulo, contenido)
        }
    }

    private fun mostrarMensaje(message: EditorApunteMessage) {
        if (message == EditorApunteMessage.SAVED) {
            viewModel.messageShown()
            findNavController().navigateUp()
            return
        }

        val textRes = when (message) {
            EditorApunteMessage.INVALID_NOTE -> R.string.apunte_invalid_error
            EditorApunteMessage.NOTE_NOT_FOUND -> R.string.apunte_not_found_error
            EditorApunteMessage.SECTION_NOT_FOUND -> R.string.seccion_not_found_error
            EditorApunteMessage.OPERATION_ERROR -> R.string.apuntes_operation_error
            EditorApunteMessage.SAVED -> return
        }
        Snackbar.make(binding.root, textRes, Snackbar.LENGTH_SHORT).show()
        viewModel.messageShown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
