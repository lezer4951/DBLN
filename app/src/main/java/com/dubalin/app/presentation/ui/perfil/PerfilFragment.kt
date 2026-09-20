package com.dubalin.app.presentation.ui.perfil

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentPerfilBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dubalin.app.presentation.ui.learning.hubCard

/**
 * Perfil usa el NavController raíz para cerrar la sesión porque la acción
 * de retorno al login vive en nav_graph.xml, fuera del grafo anidado del Home.
 */
@AndroidEntryPoint
class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PerfilViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPerfilBinding.bind(view)

        binding.btnCerrarSesion.setOnClickListener {
            confirmarCierreDeSesion()
        }

        mostrarPerfil()

        observarSesion()
    }

    private fun mostrarPerfil() {
        val content = binding.profileContent
        content.removeAllViews()
        val details = viewModel.detalles()
        content.hubCard("Tu camino, tus logros", "Estudia a tu ritmo. Tus rangos se mostrarán cuando existan evaluaciones.", true)
        content.hubCard("Sobre mí", "Carrera: ${details.carrera.ifBlank { "Sin especificar" }}\nGrado: ${details.grado.ifBlank { "Sin especificar" }}\nIntereses: ${details.intereses.ifBlank { "Sin especificar" }}\n\nEditar información →") { editarPerfil() }
        content.hubCard("La carrera no limita tu aprendizaje", "Es un dato descriptivo y opcional. No cambia tus materias, preguntas ni dificultad.")
        content.hubCard("Rangos por materia", "Sin evaluar. No mostramos IQ ni asignamos rangos sin resultados.")
        com.dubalin.app.domain.model.Materias.todas.forEach { materia ->
            content.hubCard(materia.nombre, "Sin evaluar · Progreso disponible próximamente")
        }
    }

    private fun editarPerfil() {
        val current = viewModel.detalles()
        val box = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            val p = (20 * resources.displayMetrics.density).toInt()
            setPadding(p, p, p, p)
        }
        fun field(label: String, value: String): com.google.android.material.textfield.TextInputEditText {
            val layout = com.google.android.material.textfield.TextInputLayout(requireContext()).apply { hint = label }
            val input = com.google.android.material.textfield.TextInputEditText(layout.context).apply {
                setText(value)
                inputType = android.text.InputType.TYPE_CLASS_TEXT
                filters = arrayOf(android.text.InputFilter.LengthFilter(200))
            }
            layout.addView(input); box.addView(layout)
            return input
        }
        val carrera = field("Carrera (opcional)", current.carrera)
        val grado = field("Grado o semestre (opcional)", current.grado)
        val intereses = field("Intereses (opcional)", current.intereses)
        MaterialAlertDialogBuilder(requireContext()).setTitle("Editar perfil").setView(box)
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.action_save) { _, _ ->
                viewModel.guardarDetalles(com.dubalin.app.data.local.ProfileDetails(
                    carrera.text.toString(), grado.text.toString(), intereses.text.toString()))
                mostrarPerfil()
            }.show()
    }

    private fun confirmarCierreDeSesion() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.profile_logout)
            .setMessage(R.string.profile_logout_question)
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.profile_logout) { _, _ ->
                viewModel.cerrarSesion()
            }
            .show()
    }

    private fun observarSesion() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sesionCerrada.collect { sesionCerrada ->
                    if (sesionCerrada) {
                        requireActivity()
                            .findNavController(R.id.nav_host_fragment)
                            .navigate(R.id.action_home_to_login)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
