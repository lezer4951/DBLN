package com.dubalin.app.presentation.ui.desafios

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentMemoramaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemoramaFragment : Fragment(R.layout.fragment_memorama) {
    private val model: MemoramaViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentMemoramaBinding.bind(view)
        val cartas = listOf(b.carta0, b.carta1, b.carta2, b.carta3, b.carta4, b.carta5, b.carta6, b.carta7)
        b.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        cartas.forEachIndexed { index, boton -> boton.setOnClickListener { model.elegir(index) } }
        b.continuar.setOnClickListener { model.continuar() }
        b.reiniciar.setOnClickListener { model.cargar() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.collect { s ->
                    b.estado.text = when {
                        s.cargando -> getString(R.string.memory_loading)
                        s.error -> getString(R.string.memory_error)
                        s.textos.isEmpty() -> getString(R.string.memory_empty)
                        s.completo -> getString(R.string.memory_complete, s.intentos)
                        else -> getString(R.string.memory_progress, s.encontradas.size, s.intentos)
                    }
                    cartas.forEachIndexed { i, boton ->
                        boton.isVisible = s.textos.isNotEmpty()
                        val encontrada = s.parejas.getOrNull(i)?.let { it in s.encontradas } ?: false
                        boton.text = if (encontrada || i in s.visibles) s.textos[i] else getString(R.string.memory_card, i + 1)
                        boton.isEnabled = !encontrada && i !in s.visibles && s.visibles.size < 2
                    }
                    b.continuar.isVisible = s.visibles.size == 2 && !s.completo
                    b.reiniciar.isEnabled = !s.cargando
                }
            }
        }
    }
}
