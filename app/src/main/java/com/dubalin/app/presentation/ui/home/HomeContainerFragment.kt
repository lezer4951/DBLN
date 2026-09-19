package com.dubalin.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentHomeContainerBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Contenedor del Home Dashboard: aloja el BottomNavigationView y el
 * NavHostFragment anidado que controla las 5 pestañas (nav_graph_home.xml).
 * Esta clase reemplaza al HomePlaceholderFragment del Módulo 2.
 */
@AndroidEntryPoint
class HomeContainerFragment : Fragment(R.layout.fragment_home_container) {

    private var _binding: FragmentHomeContainerBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeContainerBinding.bind(view)

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.nav_host_home) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
