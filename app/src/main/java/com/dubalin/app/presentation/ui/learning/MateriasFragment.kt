package com.dubalin.app.presentation.ui.learning
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentLearningHubBinding
import com.dubalin.app.domain.model.Materias
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
        val box = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            val p = (20 * resources.displayMetrics.density).toInt()
            setPadding(p, p, p, p)
        }
        box.addView(TextView(requireContext()).apply { text = "$materia · $tema\n\n¿Cuánto crees dominarlo?" })
        val dominio = Spinner(requireContext()).apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                listOf("Estoy empezando", "Conozco lo básico", "Quiero ponerme a prueba"))
        }
        box.addView(dominio)
        val layout = TextInputLayout(requireContext()).apply { hint = "¿Qué se te dificulta? (opcional)" }
        val dificultad = TextInputEditText(layout.context).apply {
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
            filters = arrayOf(android.text.InputFilter.LengthFilter(500))
        }
        layout.addView(dificultad); box.addView(layout)
        box.addView(TextView(requireContext()).apply { text = "\nVista previa: generación de preguntas y calificación próximamente." })
        MaterialAlertDialogBuilder(requireContext()).setTitle("Preparar examen").setView(box)
            .setNegativeButton(R.string.action_cancel, null).setPositiveButton("Ver resumen") { _, _ ->
                MaterialAlertDialogBuilder(requireContext()).setTitle("Tu práctica")
                    .setMessage("$materia · $tema\nDominio declarado: ${dominio.selectedItem}\nDificultad: ${dificultad.text.toString().ifBlank { "Sin especificar" }}\n\nVista previa. No se genera un examen ni se guarda esta configuración todavía.")
                    .setPositiveButton("Entendido", null).show()
            }.show()
    }
}
