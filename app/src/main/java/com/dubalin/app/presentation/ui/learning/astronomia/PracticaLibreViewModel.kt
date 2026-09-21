package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.SavedStateHandle
import com.dubalin.app.domain.model.CatalogoAstronomia
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PracticaLibreViewModel @Inject constructor(saved: SavedStateHandle) : EvaluacionAstronomiaViewModel(
    CatalogoAstronomia.examenes.flatMapIndexed { nivel, preguntas -> preguntas.take(2).map {
        it.copy(enunciado = "Nivel $nivel · ${it.enunciado}")
    } }, false, saved, { aciertos, total -> Result.success(aciertos * 100 / total >= 80) }
) {
    val supervivencia: Boolean = saved["supervivencia"] ?: false
    override val finalizarAlFallar get() = supervivencia
}
