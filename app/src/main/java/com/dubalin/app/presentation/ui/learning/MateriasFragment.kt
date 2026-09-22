package com.dubalin.app.presentation.ui.learning

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentMateriasBinding
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.Materias
import com.dubalin.app.presentation.ui.learning.astronomia.AstronomiaViewModel
import com.dubalin.app.presentation.ui.learning.materias.MateriaListItem
import com.dubalin.app.presentation.ui.learning.materias.MateriasAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MateriasFragment : Fragment(R.layout.fragment_materias) {
    private var binding: FragmentMateriasBinding? = null
    private val model: AstronomiaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentMateriasBinding.bind(view)
        binding = b
        b.subjectsToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        b.rvMaterias.layoutManager = LinearLayoutManager(requireContext())
        if (savedInstanceState == null) {
            val index = arguments?.getInt("materiaInicial", -1) ?: -1
            Materias.todas.getOrNull(index)?.takeIf { it.disponible }?.let { abrirMateria() }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.uiState.collect { state ->
                    b.rvMaterias.adapter = MateriasAdapter(filas(state.nivelActual)) { abrirMateria() }
                }
            }
        }
    }

    private fun filas(nivel: Int): List<MateriaListItem> {
        fun color(id: Int) = ContextCompat.getColor(requireContext(), id)
        val items = mutableListOf<MateriaListItem>(
            MateriaListItem.Header("Tu ruta actual", color(R.color.astronomia_gradient_start)))
        val astronomia = requireNotNull(Materias.buscar(MateriaId.ASTRONOMIA))
        items += MateriaListItem.Destacada(astronomia.nombre, astronomia.descripcion, nivel, R.drawable.ic_materia_astronomia)
        fun familia(titulo: String, tinta: Int, fondo: Int, chip: Int, materias: List<Pair<MateriaId, Int>>) {
            items += MateriaListItem.Header(titulo, color(tinta))
            materias.forEach { (id, icono) ->
                val m = requireNotNull(Materias.buscar(id))
                items += MateriaListItem.Card(id.name, m.nombre, m.descripcion, m.disponible,
                    icono, color(fondo), color(chip))
            }
        }
        familia("Ciencias exactas", R.color.family_exactas, R.color.family_exactas_bg, R.color.family_exactas_chip, listOf(
            MateriaId.FISICA to R.drawable.ic_materia_fisica,
            MateriaId.QUIMICA to R.drawable.ic_materia_quimica,
            MateriaId.MATEMATICAS to R.drawable.ic_materia_matematicas,
            MateriaId.TECNOLOGIA to R.drawable.ic_nav_autoestudio))
        familia("Ciencias naturales", R.color.family_naturales, R.color.family_naturales_bg, R.color.family_naturales_chip, listOf(
            MateriaId.BIOLOGIA to R.drawable.ic_materia_biologia,
            MateriaId.GEOLOGIA to R.drawable.ic_materia_geologia,
            MateriaId.ANATOMIA to R.drawable.ic_ref_heart))
        familia("Humanidades", R.color.family_humanidades, R.color.family_humanidades_bg, R.color.family_humanidades_chip, listOf(
            MateriaId.HISTORIA to R.drawable.ic_materia_historia,
            MateriaId.PSICOLOGIA to R.drawable.ic_materia_psicologia,
            MateriaId.SOCIOLOGIA to R.drawable.ic_ref_friends,
            MateriaId.ESCRITURA to R.drawable.ic_ref_book))
        familia("Idiomas", R.color.family_idiomas, R.color.family_idiomas_bg, R.color.family_idiomas_chip, listOf(
            MateriaId.ESPANOL to R.drawable.ic_materia_idioma,
            MateriaId.INGLES to R.drawable.ic_materia_idioma,
            MateriaId.FRANCES to R.drawable.ic_materia_idioma,
            MateriaId.PORTUGUES to R.drawable.ic_materia_idioma,
            MateriaId.ALEMAN to R.drawable.ic_materia_idioma))
        return items
    }

    private fun abrirMateria() {
        findNavController().navigate(R.id.action_materias_to_astronomia)
    }

    override fun onDestroyView() {
        binding?.rvMaterias?.adapter = null
        binding = null
        super.onDestroyView()
    }
}
