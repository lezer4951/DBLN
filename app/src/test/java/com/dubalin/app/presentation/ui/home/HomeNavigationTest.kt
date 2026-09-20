package com.dubalin.app.presentation.ui.home

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.lifecycle.ViewModelStore
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.dubalin.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = Application::class)
class HomeNavigationTest {
    private lateinit var controller: TestNavHostController
    private lateinit var bottomNav: BottomNavigationView

    @Before
    fun setUp() {
        val context = ContextThemeWrapper(
            ApplicationProvider.getApplicationContext<Context>(), R.style.Theme_Dubalin)
        controller = TestNavHostController(context).apply {
            setViewModelStore(ViewModelStore())
            setGraph(R.navigation.nav_graph_home)
        }
        bottomNav = BottomNavigationView(context).apply {
            inflateMenu(R.menu.bottom_nav_menu)
            bindHomeNavigation(controller)
        }
    }

    @Test
    fun everyTabReturnsToHomeAndRepeatedTapsStayThere() {
        listOf(R.id.autoestudioFragment, R.id.desafiosFragment,
            R.id.idiomasFragment, R.id.perfilFragment).forEach { tab ->
            bottomNav.selectedItemId = tab
            bottomNav.selectedItemId = R.id.homeFragment
            assertHome()
            repeat(3) {
                bottomNav.selectedItemId = R.id.homeFragment
                assertHome()
            }
        }
    }

    @Test
    fun returnsFromNestedNotesAndPreservesTheStudyTab() {
        bottomNav.selectedItemId = R.id.autoestudioFragment
        controller.navigate(R.id.seccionesFragment)
        controller.navigate(R.id.misApuntesDetalleFragment, Bundle().apply {
            putInt("seccionId", 7)
            putString("seccionNombre", "Inglés")
        })
        bottomNav.selectedItemId = R.id.homeFragment
        assertHome()
        bottomNav.selectedItemId = R.id.autoestudioFragment
        assertEquals(R.id.misApuntesDetalleFragment, controller.currentDestination?.id)
        assertEquals(7, controller.currentBackStackEntry?.arguments?.getInt("seccionId"))
        bottomNav.selectedItemId = R.id.homeFragment
        assertHome()
    }

    @Test
    fun homeShortcutToFlashcardsCanReturnViaBottomBar() {
        controller.navigate(R.id.autoestudioFragment)
        controller.navigate(R.id.flashcardsFragment)
        assertEquals(R.id.autoestudioFragment, bottomNav.selectedItemId)
        bottomNav.selectedItemId = R.id.homeFragment
        assertHome()
    }

    @Test
    fun switchingBetweenTabsBeforeHomeDoesNotRestoreTheWrongDestination() {
        bottomNav.selectedItemId = R.id.autoestudioFragment
        controller.navigate(R.id.materiasFragment)
        bottomNav.selectedItemId = R.id.perfilFragment
        bottomNav.selectedItemId = R.id.desafiosFragment
        bottomNav.selectedItemId = R.id.homeFragment
        assertHome()
        bottomNav.selectedItemId = R.id.autoestudioFragment
        assertEquals(R.id.materiasFragment, controller.currentDestination?.id)
    }

    private fun assertHome() {
        assertEquals(R.id.homeFragment, controller.currentDestination?.id)
        assertEquals(R.id.homeFragment, bottomNav.selectedItemId)
        assertEquals(R.id.homeFragment, controller.currentBackStackEntry?.destination?.id)
    }
}
