package com.dubalin.app.domain.repository

import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.ExperienciaAstronomia
import com.dubalin.app.domain.model.FormatoAprendizaje
import com.dubalin.app.domain.model.ProgresoMateria
import kotlinx.coroutines.flow.Flow

interface ProgresoAcademicoRepository {
    fun observarProgreso(usuarioId: Int, materiaId: MateriaId): Flow<ProgresoMateria>

    suspend fun registrarPagina(
        usuarioId: Int,
        materiaId: MateriaId,
        pagina: Int
    ): Result<Unit>

    suspend fun configurarPlanAstronomia(
        usuarioId: Int,
        experiencia: ExperienciaAstronomia,
        minutosDiarios: Int,
        formato: FormatoAprendizaje
    ): Result<Unit>

    suspend fun completarTemaNivelCero(
        usuarioId: Int,
        indiceTema: Int
    ): Result<Unit>

    suspend fun registrarEvaluacionNivelCero(
        usuarioId: Int,
        materiaId: MateriaId,
        aciertos: Int,
        totalPreguntas: Int
    ): Result<Boolean>
}
