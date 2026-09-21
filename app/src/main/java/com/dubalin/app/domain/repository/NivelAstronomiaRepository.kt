package com.dubalin.app.domain.repository

import com.dubalin.app.domain.model.ProgresoNivel
import kotlinx.coroutines.flow.Flow

interface NivelAstronomiaRepository {
    fun observarNivel(usuarioId: Int, nivel: Int): Flow<ProgresoNivel>
    suspend fun completarTema(usuarioId: Int, nivel: Int, indiceTema: Int): Result<Unit>
    suspend fun completarPractica(usuarioId: Int, nivel: Int): Result<Unit>
    suspend fun registrarEvaluacion(usuarioId: Int, nivel: Int, aciertos: Int, total: Int): Result<Boolean>
}
