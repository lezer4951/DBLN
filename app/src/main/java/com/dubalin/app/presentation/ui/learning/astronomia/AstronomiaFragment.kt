package com.dubalin.app.presentation.ui.learning.astronomia

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
import com.dubalin.app.databinding.FragmentAstronomiaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AstronomiaFragment : Fragment(R.layout.fragment_astronomia) {

    private var _binding: FragmentAstronomiaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AstronomiaViewModel by viewModels()
    @javax.inject.Inject lateinit var borradores: BorradorSesionStore
    @javax.inject.Inject lateinit var sesion: com.dubalin.app.domain.repository.SessionRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAstronomiaBinding.bind(view)

        binding.astronomyToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.cardStudy.setOnClickListener {
            findNavController().navigate(R.id.action_astronomia_to_ruta)
        }

        binding.cardCuriosity.setOnClickListener { findNavController().navigate(R.id.action_astronomia_to_curiosidades) }
        binding.cardPractice.setOnClickListener { findNavController().navigate(R.id.action_astronomia_to_practica_libre) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    mostrarContinuacion(state)
                    binding.astronomyProgress.setProgressCompat(com.dubalin.app.domain.model.CatalogoAstronomia.porcentaje(state.nivelActual), true)
                    binding.astronomyProgressText.text = when {
                        state.nivelActual > 10 -> getString(R.string.astronomy_progress_completed, state.mejorPuntaje)
                        state.nivelCeroCompletado -> getString(R.string.astronomy_progress_unlocked, state.nivelActual, state.mejorPuntaje)
                        else -> getString(R.string.astronomy_progress_initial)
                    }
                }
            }
        }
    }

    private fun mostrarContinuacion(state: AstronomiaUiState) {
        val user = sesion.getUsuarioId()
        val nivel = borradores.ultimoNivel(user)
        val draft = nivel?.let { borradores.leer(user, it) }
        val tema = draft?.optInt("tema", -1) ?: -1
        val contenido = nivel?.let {
            com.dubalin.app.domain.model.CatalogoAstronomia.sesiones.getOrNull(it)?.getOrNull(tema)
        }
        val disponible = state.cargado && state.onboardingCompletado && nivel != null &&
            nivel <= state.nivelActual && contenido != null
        binding.resumeSessionCard.isVisible = disponible
        binding.buttonResumeSession.setOnClickListener(null)
        if (!disponible || nivel == null || contenido == null || draft == null) return
        val autoevaluacion = draft.optString("fase") == FaseSesion.AUTOEVALUACION.name
        val paso = if (autoevaluacion) draft.optInt("pregunta", 0).coerceIn(contenido.autoevaluacion.indices) + 1
            else requireContext().getSharedPreferences("secciones_astronomia_v1", android.content.Context.MODE_PRIVATE)
                .getInt("$user:$nivel:$tema", 0).coerceIn(0, 4) + 1
        binding.resumeSessionDetail.text = getString(
            if (autoevaluacion) R.string.lesson_resume_assessment else R.string.lesson_resume_detail,
            nivel, contenido.titulo, paso)
        binding.buttonResumeSession.setOnClickListener {
            binding.buttonResumeSession.isEnabled = false
            val actions = listOf(R.id.action_ruta_to_nivel_cero, R.id.action_ruta_to_nivel_uno,
                R.id.action_ruta_to_nivel_dos, R.id.action_ruta_to_nivel_tres, R.id.action_ruta_to_nivel_cuatro,
                R.id.action_ruta_to_nivel_cinco, R.id.action_ruta_to_nivel_seis, R.id.action_ruta_to_nivel_siete,
                R.id.action_ruta_to_nivel_ocho, R.id.action_ruta_to_nivel_nueve, R.id.action_ruta_to_nivel_diez)
            findNavController().navigate(R.id.action_astronomia_to_ruta)
            findNavController().navigate(actions[nivel])
        }
        binding.buttonResumeSession.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
