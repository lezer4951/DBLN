package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.SavedStateHandle
import com.dubalin.app.domain.model.CatalogoAstronomia
import com.dubalin.app.domain.repository.NivelAstronomiaRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias NivelCincoPracticaUiState = EvaluacionUiState

@HiltViewModel
class AstronomiaNivelCincoPracticaViewModel @Inject constructor(
    session: SessionRepository,
    repository: NivelAstronomiaRepository,
    saved: SavedStateHandle = SavedStateHandle()
) : EvaluacionAstronomiaViewModel(
    CatalogoAstronomia.practica(5),
    true, saved,
    { aciertos, total ->
        val user = session.getUsuarioId()
        if (user == null) Result.failure(IllegalStateException("Sesión requerida"))
        else repository.completarPractica(user, 5).map { true }
    }
)
