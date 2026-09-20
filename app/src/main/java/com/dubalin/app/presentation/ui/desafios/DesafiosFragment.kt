package com.dubalin.app.presentation.ui.desafios

import androidx.fragment.app.Fragment
import com.dubalin.app.R
import com.dubalin.app.presentation.ui.learning.hubCard

/**
 * Placeholder del Paso 3.1. Contenido real (Modo Supervivencia,
 * Cuestionarios Temáticos, Ranking Global) se construye en el Módulo 5.
 */
class DesafiosFragment : Fragment(R.layout.fragment_learning_hub) {
    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        val b = com.dubalin.app.databinding.FragmentLearningHubBinding.bind(view)
        b.hubToolbar.setTitle(R.string.nav_desafios)
        b.hubContent.hubCard("Aprende. Juega. Supérate.", "Compite y mejora a tu ritmo. Estos modos están en preparación.", true)
        listOf(
            "Memorama" to "Encuentra parejas usando tus flashcards.",
            "Duelo PvP" to "Desafía a otra persona en una o varias materias.",
            "Reto entre amigos" to "Responde un reto cuando tengas tiempo.",
            "Supervivencia" to "Pon a prueba tus conocimientos pregunta a pregunta.",
            "Ranking por materia" to "Ligas por materia. Todavía no hay clasificación ni rivales conectados."
        ).forEach { (title, detail) ->
            b.hubContent.hubCard(title, "$detail\nPróximamente") {
                com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                    .setTitle(title).setMessage("$detail\n\nVista previa: no inicia partidas ni otorga puntos.")
                    .setPositiveButton("Entendido", null).show()
            }
        }
    }
}
