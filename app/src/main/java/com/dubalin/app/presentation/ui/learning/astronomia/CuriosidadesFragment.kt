package com.dubalin.app.presentation.ui.learning.astronomia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentCuriosidadesBinding
import com.dubalin.app.domain.model.CatalogoAstronomia

class CuriosidadesFragment : Fragment(R.layout.fragment_curiosidades) {
    private var indice = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentCuriosidadesBinding.bind(view)
        val temas = CatalogoAstronomia.sesiones.flatten()
        indice = (savedInstanceState?.getInt("indice") ?: 0).coerceIn(temas.indices)
        fun mostrar() {
            val tema = temas[indice]
            b.contador.text = getString(R.string.astronomy_lesson_counter, indice + 1, temas.size)
            b.titulo.text = tema.titulo
            b.detalle.text = "${tema.ideaClave}\n\n${tema.ejemploVisual}"
            b.anterior.isEnabled = indice > 0
            b.siguiente.isEnabled = indice < temas.lastIndex
        }
        b.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        b.anterior.setOnClickListener { indice--; mostrar() }
        b.siguiente.setOnClickListener { indice++; mostrar() }
        mostrar()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("indice", indice)
        super.onSaveInstanceState(outState)
    }
}
