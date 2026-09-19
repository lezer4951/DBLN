package com.dubalin.app.presentation.ui.auth.login

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
import com.dubalin.app.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            val correo = binding.etCorreo.text?.toString().orEmpty()
            val password = binding.etPassword.text?.toString().orEmpty()
            viewModel.login(correo, password)
        }

        binding.tvIrRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registro)
        }

        observarEstado()
    }

    private fun observarEstado() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressLogin.isVisible = state.isLoading
                    binding.btnLogin.isEnabled = !state.isLoading
                    binding.tvIrRegistro.isEnabled = !state.isLoading

                    state.errorMessage?.let { mensaje ->
                        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                        viewModel.errorMostrado()
                    }

                    if (state.loginExitoso) {
                        viewModel.loginConsumido()
                        findNavController().navigate(R.id.action_login_to_home)
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
