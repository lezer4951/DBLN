package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.SavedStateHandle
import com.dubalin.app.domain.model.CatalogoAstronomia
import com.dubalin.app.domain.repository.NivelAstronomiaRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias NivelDiezExamenUiState = EvaluacionUiState
typealias NivelDiezExamenResultado = EvaluacionResultado

@HiltViewModel
class AstronomiaNivelDiezExamenViewModel @Inject constructor(
    session: SessionRepository,
    repository: NivelAstronomiaRepository,
    saved: SavedStateHandle = SavedStateHandle()
) : EvaluacionAstronomiaViewModel(
    CatalogoAstronomia.examenes[10],
    false, saved,
    { aciertos, total ->
        val user = session.getUsuarioId()
        if (user == null) Result.failure(IllegalStateException("Sesión requerida"))
        else repository.registrarEvaluacion(user, 10, aciertos, total)
    }
)
