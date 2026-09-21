package com.dubalin.app.presentation.ui.learning
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentLearningHubBinding
import com.dubalin.app.domain.model.Materias

class MateriasFragment : Fragment(R.layout.fragment_learning_hub) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentLearningHubBinding.bind(view)
        b.hubToolbar.setTitle(R.string.subjects_title)
        b.hubToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        b.hubToolbar.setNavigationContentDescription(R.string.action_back)
        b.hubToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        b.hubContent.hubCard(
            title = getString(R.string.subjects_hero_title),
            detail = getString(R.string.subjects_hero_description),
            accent = true
        )
        if (savedInstanceState == null) {
            val index = arguments?.getInt("materiaInicial", -1) ?: -1
            Materias.todas.getOrNull(index)?.takeIf { it.disponible }?.let { abrirMateria() }
        }
        Materias.todas.forEach { m ->
            val detail = if (m.disponible) {
                getString(R.string.subject_available_description, m.descripcion)
            } else {
                m.descripcion
            }
            b.hubContent.hubCard(
                title = m.nombre,
                detail = detail,
                status = if (m.disponible) getString(R.string.subject_available) else getString(R.string.coming_soon),
                enabled = m.disponible,
                action = { abrirMateria() }
            )
        }
    }

    private fun abrirMateria() {
        findNavController().navigate(R.id.action_materias_to_astronomia)
    }
}
