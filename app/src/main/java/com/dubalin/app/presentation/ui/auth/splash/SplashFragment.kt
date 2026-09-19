package com.dubalin.app.presentation.ui.auth.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.data.local.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Pantalla inicial: muestra la marca brevemente mientras decide a dónde
 * navegar según si hay una sesión guardada localmente.
 */
@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    @Inject
    lateinit var sessionManager: SessionManager

    private companion object {
        const val SPLASH_DELAY_MS = 800L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(SPLASH_DELAY_MS)

            val destino = if (sessionManager.getUsuarioId() != null) {
                R.id.action_splash_to_home
            } else {
                R.id.action_splash_to_login
            }

            findNavController().navigate(destino)
        }
    }
}
