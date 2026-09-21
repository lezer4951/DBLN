package com.dubalin.app.presentation.ui.learning.astronomia

import android.app.Application
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import androidx.test.core.app.ApplicationProvider
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentAstronomiaNivelCeroBinding
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = Application::class)
class SeccionesLeccionTest {
    private lateinit var context: ContextThemeWrapper

    @Before fun prepare() {
        context = ContextThemeWrapper(ApplicationProvider.getApplicationContext<Application>(), R.style.Theme_Dubalin)
        context.getSharedPreferences("secciones_astronomia_v1", 0).edit().clear().commit()
    }

    private fun binding() = FragmentAstronomiaNivelCeroBinding.inflate(LayoutInflater.from(context))

    @Test fun salirYVolverRecuperaLaSeccionSinMezclarUsuarios() {
        val first = binding()
        SeccionesLeccion(first, 9, 2).render(0)
        first.sectionNext.performClick()
        assertEquals(View.VISIBLE, first.sectionAnalogy.visibility)
        val reopened = binding()
        SeccionesLeccion(reopened, 9, 2).render(0)
        assertEquals(View.VISIBLE, reopened.sectionAnalogy.visibility)
        assertEquals(View.GONE, reopened.sectionGoal.visibility)
        val other = binding()
        SeccionesLeccion(other, 10, 2).render(0)
        assertEquals(View.VISIBLE, other.sectionGoal.visibility)
    }

    @Test fun autoevaluacionSoloApareceAlLlegarAlResumen() {
        val b = binding()
        val presenter = SeccionesLeccion(b, 9, 2)
        presenter.render(0)
        assertEquals(View.GONE, b.buttonNext.visibility)
        repeat(4) { b.sectionNext.performClick() }
        assertEquals(View.VISIBLE, b.sectionSummary.visibility)
        assertEquals(View.VISIBLE, b.buttonNext.visibility)
        presenter.render(1)
        assertEquals(View.VISIBLE, b.sectionGoal.visibility)
        assertEquals(View.GONE, b.buttonNext.visibility)
    }
}
