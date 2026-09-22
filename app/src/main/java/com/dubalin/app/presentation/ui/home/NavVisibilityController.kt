package com.dubalin.app.presentation.ui.home

import android.view.View
import androidx.navigation.NavController
import com.dubalin.app.R

internal object NavVisibilityController {
    private val focusedDestinations = setOf(
        R.id.practicaLibreFragment,
        R.id.astronomiaNivelCeroFragment,
        R.id.astronomiaQuizFragment,
        R.id.astronomiaNivelUnoFragment,
        R.id.astronomiaNivelUnoPracticaFragment,
        R.id.astronomiaNivelUnoExamenFragment,
        R.id.astronomiaNivelDosFragment,
        R.id.astronomiaNivelDosPracticaFragment,
        R.id.astronomiaNivelDosExamenFragment,
        R.id.astronomiaNivelTresFragment,
        R.id.astronomiaNivelTresPracticaFragment,
        R.id.astronomiaNivelTresExamenFragment,
        R.id.astronomiaNivelCuatroFragment,
        R.id.astronomiaNivelCuatroPracticaFragment,
        R.id.astronomiaNivelCuatroExamenFragment,
        R.id.astronomiaNivelCincoFragment,
        R.id.astronomiaNivelCincoPracticaFragment,
        R.id.astronomiaNivelCincoExamenFragment,
        R.id.astronomiaNivelSeisFragment,
        R.id.astronomiaNivelSeisPracticaFragment,
        R.id.astronomiaNivelSeisExamenFragment,
        R.id.astronomiaNivelSieteFragment,
        R.id.astronomiaNivelSietePracticaFragment,
        R.id.astronomiaNivelSieteExamenFragment,
        R.id.astronomiaNivelOchoFragment,
        R.id.astronomiaNivelOchoPracticaFragment,
        R.id.astronomiaNivelOchoExamenFragment,
        R.id.astronomiaNivelNueveFragment,
        R.id.astronomiaNivelNuevePracticaFragment,
        R.id.astronomiaNivelNueveExamenFragment,
        R.id.astronomiaNivelDiezFragment,
        R.id.astronomiaNivelDiezPracticaFragment,
        R.id.astronomiaNivelDiezExamenFragment,
        R.id.supervivenciaFragment
    )

    fun attach(controller: NavController, container: View): NavController.OnDestinationChangedListener {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            container.visibility = if (destination.id in focusedDestinations) View.GONE else View.VISIBLE
        }
        controller.addOnDestinationChangedListener(listener)
        return listener
    }
}
