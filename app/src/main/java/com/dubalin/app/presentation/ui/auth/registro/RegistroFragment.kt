package com.dubalin.app.presentation.ui.auth.registro

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
import com.dubalin.app.databinding.FragmentRegistroBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistroFragment : Fragment(R.layout.fragment_registro) {

    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistroViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistroBinding.bind(view)

        binding.btnRegistrar.setOnClickListener {
            viewModel.registrar(
                nombre = binding.etNombre.text?.toString().orEmpty(),
                correo = binding.etCorreo.text?.toString().orEmpty(),
                password = binding.etPassword.text?.toString().orEmpty(),
                confirmarPassword = binding.etConfirmarPassword.text?.toString().orEmpty()
            )
        }

        binding.tvIrLogin.setOnClickListener {
            findNavController().navigateUp()
        }

        observarEstado()
    }

    private fun observarEstado() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressRegistro.isVisible = state.isLoading
                    binding.btnRegistrar.isEnabled = !state.isLoading

                    state.errorMessage?.let { mensaje ->
                        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                        viewModel.errorMostrado()
                    }

                    if (state.registroExitoso) {
                        findNavController().navigate(R.id.action_registro_to_home)
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
