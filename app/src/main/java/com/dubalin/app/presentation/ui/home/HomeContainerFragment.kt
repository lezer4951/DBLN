package com.dubalin.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
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

    private val motionCallbacks = object : androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: androidx.fragment.app.FragmentManager, f: Fragment,
            v: View, savedInstanceState: Bundle?) {
            if (f !is NavHostFragment && savedInstanceState == null) {
                com.dubalin.app.presentation.ui.learning.Motion.enter(v)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.registerFragmentLifecycleCallbacks(motionCallbacks, true)
    }

    override fun onDestroy() {
        childFragmentManager.unregisterFragmentLifecycleCallbacks(motionCallbacks)
        super.onDestroy()
    }

    private var _binding: FragmentHomeContainerBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeContainerBinding.bind(view)

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.nav_host_home) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.bindHomeNavigation(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
