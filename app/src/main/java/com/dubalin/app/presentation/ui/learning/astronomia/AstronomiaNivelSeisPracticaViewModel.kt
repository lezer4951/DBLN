package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.SavedStateHandle
import com.dubalin.app.domain.model.CatalogoAstronomia
import com.dubalin.app.domain.repository.NivelAstronomiaRepository
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias NivelSeisPracticaUiState = EvaluacionUiState

@HiltViewModel
class AstronomiaNivelSeisPracticaViewModel @Inject constructor(
    session: SessionRepository,
    repository: NivelAstronomiaRepository,
    saved: SavedStateHandle = SavedStateHandle()
) : EvaluacionAstronomiaViewModel(
    CatalogoAstronomia.practica(6),
    true, saved,
    { aciertos, total ->
        val user = session.getUsuarioId()
        if (user == null) Result.failure(IllegalStateException("Sesión requerida"))
        else repository.completarPractica(user, 6).map { true }
    }
)
