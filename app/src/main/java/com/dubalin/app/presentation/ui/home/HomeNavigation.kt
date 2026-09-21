package com.dubalin.app.presentation.ui.home

import androidx.navigation.NavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.dubalin.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/** Cambiar o volver a pulsar una pestaña abre su raíz, sin restaurar pilas antiguas. */
internal fun BottomNavigationView.bindHomeNavigation(controller: NavController) {
    setupWithNavController(controller)
    setOnItemSelectedListener { item ->
        controller.openTab(item.itemId)
        true
    }
    setOnItemReselectedListener { item ->
        controller.openTab(item.itemId)
    }
}

private fun NavController.openTab(destination: Int) {
    navigate(destination, null, navOptions {
        launchSingleTop = true
        // Restaurar el estado asociado al inicio puede reabrir una pila de otra pestaña.
        restoreState = false
        popUpTo(R.id.homeFragment) {
            inclusive = false
            saveState = false
        }
    })
}
