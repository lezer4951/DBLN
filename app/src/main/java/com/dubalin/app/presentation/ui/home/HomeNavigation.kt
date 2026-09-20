package com.dubalin.app.presentation.ui.home

import androidx.navigation.NavController
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dubalin.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/** Inicio siempre vuelve al dashboard; las demás pestañas conservan su historial. */
internal fun BottomNavigationView.bindHomeNavigation(controller: NavController) {
    setupWithNavController(controller)
    setOnItemSelectedListener { item ->
        if (item.itemId == R.id.homeFragment) {
            controller.returnToHome()
            true
        } else {
            NavigationUI.onNavDestinationSelected(item, controller)
        }
    }
    setOnItemReselectedListener { item ->
        if (item.itemId == R.id.homeFragment) controller.returnToHome()
    }
}

private fun NavController.returnToHome() {
    if (currentDestination?.id == R.id.homeFragment) return
    navigate(R.id.homeFragment, null, navOptions {
        launchSingleTop = true
        // Restaurar el estado asociado al inicio puede reabrir una pila de otra pestaña.
        restoreState = false
        popUpTo(R.id.homeFragment) {
            inclusive = false
            saveState = true
        }
    })
}
