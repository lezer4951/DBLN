package com.dubalin.app.presentation.ui.learning
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentLearningHubBinding
import com.dubalin.app.domain.model.Materias
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MateriasFragment : Fragment(R.layout.fragment_learning_hub) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentLearningHubBinding.bind(view)
        b.hubToolbar.setTitle(R.string.subjects_title)
        b.hubToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        b.hubToolbar.setNavigationContentDescription(R.string.action_back)
        b.hubToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        b.hubContent.hubCard("Tu siguiente logro empieza aquí", "Explora temas y prepara tu práctica. Las materias son iguales para todas las carreras.", true)
        Materias.todas.forEach { m ->
            b.hubContent.hubCard(m.nombre, "${m.temas.size} temas iniciales · Sin evaluar\nExplorar →") {
                MaterialAlertDialogBuilder(requireContext()).setTitle(m.nombre)
                    .setItems(m.temas.toTypedArray()) { _, i -> preparar(m.nombre, m.temas[i]) }
                    .setNegativeButton(R.string.action_cancel, null).show()
            }
        }
    }
    private fun preparar(materia: String, tema: String) {
        val form = com.dubalin.app.databinding.DialogExamPreviewBinding.inflate(layoutInflater)
        form.topic.text = "$materia · $tema"
        val dominio = form.mastery
        val dificultad = form.difficulty
        MaterialAlertDialogBuilder(requireContext()).setTitle("Preparar examen").setView(form.root)
            .setNegativeButton(R.string.action_cancel, null).setPositiveButton("Ver resumen") { _, _ ->
                MaterialAlertDialogBuilder(requireContext()).setTitle("Tu práctica")
                    .setMessage("$materia · $tema\nDominio declarado: ${dominio.selectedItem}\nDificultad: ${dificultad.text.toString().ifBlank { "Sin especificar" }}\n\nVista previa. No se genera un examen ni se guarda esta configuración todavía.")
                    .setPositiveButton("Entendido", null).show()
            }.show()
    }
}
