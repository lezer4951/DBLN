package com.dubalin.app.data.repository

import com.dubalin.app.data.local.dao.ProgresoMateriaDao
import com.dubalin.app.data.local.entity.ProgresoMateriaEntity
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.ExperienciaAstronomia
import com.dubalin.app.domain.model.FormatoAprendizaje
import com.dubalin.app.domain.model.ProgresoMateria
import com.dubalin.app.domain.repository.ProgresoAcademicoRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProgresoAcademicoRepositoryImpl @Inject constructor(
    private val progresoMateriaDao: ProgresoMateriaDao
) : ProgresoAcademicoRepository {

    override fun observarProgreso(
        usuarioId: Int,
        materiaId: MateriaId
    ): Flow<ProgresoMateria> =
        progresoMateriaDao.observe(usuarioId, materiaId.name).map { entity ->
            entity?.toDomain(materiaId) ?: ProgresoMateria(materiaId = materiaId)
        }

    override suspend fun registrarPagina(
        usuarioId: Int,
        materiaId: MateriaId,
        pagina: Int
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            require(usuarioId > 0)
            require(pagina >= 0)
            asegurarFila(usuarioId, materiaId)
            check(
                progresoMateriaDao.updatePagina(
                    usuarioId = usuarioId,
                    materiaId = materiaId.name,
                    pagina = pagina,
                    fecha = System.currentTimeMillis()
                ) == 1
            )
        }
    }

    override suspend fun configurarPlanAstronomia(
        usuarioId: Int,
        experiencia: ExperienciaAstronomia,
        minutosDiarios: Int,
        formato: FormatoAprendizaje
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            require(usuarioId > 0)
            require(minutosDiarios in 10..180)
            asegurarFila(usuarioId, MateriaId.ASTRONOMIA)
            check(
                progresoMateriaDao.updatePlan(
                    usuarioId,
                    MateriaId.ASTRONOMIA.name,
                    experiencia.name,
                    minutosDiarios,
                    formato.name,
                    System.currentTimeMillis()
                ) == 1
            )
        }
    }

    override suspend fun completarTemaNivelCero(
        usuarioId: Int,
        indiceTema: Int
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            require(usuarioId > 0)
            require(indiceTema in 0..9)
            asegurarFila(usuarioId, MateriaId.ASTRONOMIA)
            check(
                progresoMateriaDao.completarTema(
                    usuarioId,
                    MateriaId.ASTRONOMIA.name,
                    1 shl indiceTema,
                    indiceTema,
                    System.currentTimeMillis()
                ) == 1
            )
        }
    }

    override suspend fun registrarEvaluacionNivelCero(
        usuarioId: Int,
        materiaId: MateriaId,
        aciertos: Int,
        totalPreguntas: Int
    ): Result<Boolean> = withContext(Dispatchers.IO) {
        runCatching {
            require(usuarioId > 0)
            require(totalPreguntas > 0)
            require(aciertos in 0..totalPreguntas)
            require(materiaId == MateriaId.ASTRONOMIA)
            require(totalPreguntas == com.dubalin.app.domain.model.NivelCeroAstronomiaContenido.preguntas.size)
            val progreso = requireNotNull(progresoMateriaDao.get(usuarioId, materiaId.name))
            check((progreso.onboardingCompletado || progreso.nivelCeroCompletado) && progreso.temasCompletados and 1023 == 1023)

            val porcentaje = (aciertos * 100) / totalPreguntas
            val aprobado = porcentaje >= PORCENTAJE_APROBATORIO
            asegurarFila(usuarioId, materiaId)
            check(
                progresoMateriaDao.updateEvaluacionNivelCero(
                    usuarioId = usuarioId,
                    materiaId = materiaId.name,
                    puntaje = porcentaje,
                    aprobado = aprobado,
                    fecha = System.currentTimeMillis()
                ) == 1
            )
            aprobado
        }
    }

    private suspend fun asegurarFila(usuarioId: Int, materiaId: MateriaId) {
        progresoMateriaDao.insertIfAbsent(
            ProgresoMateriaEntity(
                usuarioId = usuarioId,
                materiaId = materiaId.name,
                fechaActualizacion = System.currentTimeMillis()
            )
        )
    }

    private companion object {
        const val PORCENTAJE_APROBATORIO = 80
    }
}

private fun ProgresoMateriaEntity.toDomain(materiaId: MateriaId): ProgresoMateria =
    ProgresoMateria(
        materiaId = materiaId,
        nivelActual = nivelActual,
        paginaActual = paginaActual,
        nivelCeroCompletado = nivelCeroCompletado,
        mejorPuntaje = mejorPuntaje,
        onboardingCompletado = onboardingCompletado,
        experiencia = enumValueOrDefault(experiencia, ExperienciaAstronomia.PRIMERA_VEZ),
        minutosDiarios = minutosDiarios,
        formatoAprendizaje = enumValueOrDefault(formatoAprendizaje, FormatoAprendizaje.EQUILIBRADO),
        temasCompletados = temasCompletados
    )

private inline fun <reified T : Enum<T>> enumValueOrDefault(value: String, default: T): T =
    enumValues<T>().firstOrNull { it.name == value } ?: default
