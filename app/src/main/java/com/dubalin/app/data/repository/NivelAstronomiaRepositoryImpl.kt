package com.dubalin.app.data.repository

import androidx.room.withTransaction
import com.dubalin.app.domain.model.CatalogoAstronomia
import com.dubalin.app.data.local.DubalinDatabase
import com.dubalin.app.data.local.dao.ProgresoMateriaDao
import com.dubalin.app.data.local.dao.ProgresoNivelDao
import com.dubalin.app.data.local.entity.ProgresoNivelEntity
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.NivelUnoAstronomiaContenido
import com.dubalin.app.domain.model.NivelDosAstronomiaContenido
import com.dubalin.app.domain.model.NivelTresAstronomiaContenido
import com.dubalin.app.domain.model.NivelCuatroAstronomiaContenido
import com.dubalin.app.domain.model.NivelCincoAstronomiaContenido
import com.dubalin.app.domain.model.NivelSeisAstronomiaContenido
import com.dubalin.app.domain.model.NivelSieteAstronomiaContenido
import com.dubalin.app.domain.model.NivelOchoAstronomiaContenido
import com.dubalin.app.domain.model.NivelNueveAstronomiaContenido
import com.dubalin.app.domain.model.NivelDiezAstronomiaContenido
import com.dubalin.app.domain.model.ProgresoNivel
import com.dubalin.app.domain.repository.NivelAstronomiaRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NivelAstronomiaRepositoryImpl @Inject constructor(
    private val database: DubalinDatabase,
    private val dao: ProgresoNivelDao,
    private val progresoMateriaDao: ProgresoMateriaDao
) : NivelAstronomiaRepository {
    override fun observarNivel(usuarioId: Int, nivel: Int): Flow<ProgresoNivel> =
        dao.observe(usuarioId, MateriaId.ASTRONOMIA.name, nivel).map { entity ->
            entity?.let { ProgresoNivel(it.nivel, it.temasCompletados, it.practicaCompletada, it.nivelCompletado, it.mejorPuntaje) }
                ?: ProgresoNivel(nivel)
        }

    override suspend fun completarTema(usuarioId: Int, nivel: Int, indiceTema: Int): Result<Unit> =
        withContext(Dispatchers.IO) { runCatching {
            validarAcceso(usuarioId, nivel)
            require(indiceTema in CatalogoAstronomia.sesiones[nivel].indices)
            asegurarFila(usuarioId, nivel)
            check(dao.completarTema(usuarioId, MateriaId.ASTRONOMIA.name, nivel, 1 shl indiceTema, System.currentTimeMillis()) == 1)
        } }

    override suspend fun completarPractica(usuarioId: Int, nivel: Int): Result<Unit> =
        withContext(Dispatchers.IO) { runCatching {
            validarAcceso(usuarioId, nivel)
            asegurarFila(usuarioId, nivel)
            mascaraEsperada(nivel)?.let { mascara ->
                val progreso = requireNotNull(dao.get(usuarioId, MateriaId.ASTRONOMIA.name, nivel))
                check(progreso.temasCompletados and mascara == mascara)
            }
            check(dao.completarPractica(usuarioId, MateriaId.ASTRONOMIA.name, nivel, System.currentTimeMillis()) == 1)
        } }

    override suspend fun registrarEvaluacion(usuarioId: Int, nivel: Int, aciertos: Int, total: Int): Result<Boolean> =
        withContext(Dispatchers.IO) { runCatching {
            validarAcceso(usuarioId, nivel)
            require(total == CatalogoAstronomia.examenes[nivel].size)
            require(total > 0 && aciertos in 0..total)
            val puntaje = aciertos * 100 / total
            val aprobado = puntaje >= 80
            asegurarFila(usuarioId, nivel)
            if (nivel > 0) {
                check(dao.get(usuarioId, MateriaId.ASTRONOMIA.name, nivel)?.practicaCompletada == true)
                val mascara = CatalogoAstronomia.mascara(nivel)
                check(requireNotNull(dao.get(usuarioId, MateriaId.ASTRONOMIA.name, nivel)).temasCompletados and mascara == mascara)
            }
            database.withTransaction {
                check(dao.registrarEvaluacion(usuarioId, MateriaId.ASTRONOMIA.name, nivel, puntaje, aprobado, System.currentTimeMillis()) == 1)
                if (aprobado) {
                    check(progresoMateriaDao.avanzarNivel(usuarioId, MateriaId.ASTRONOMIA.name, nivel + 1, puntaje, System.currentTimeMillis()) == 1)
                }
            }
            aprobado
        } }

    private suspend fun validarAcceso(usuarioId: Int, nivel: Int) {
        require(usuarioId > 0 && nivel in 1..10)
        val global = requireNotNull(progresoMateriaDao.get(usuarioId, MateriaId.ASTRONOMIA.name))
        check(global.nivelCeroCompletado && global.nivelActual >= nivel)
        if (nivel > 1) check(dao.get(usuarioId, MateriaId.ASTRONOMIA.name, nivel - 1)?.nivelCompletado == true)
    }

    private suspend fun asegurarFila(usuarioId: Int, nivel: Int) {
        dao.insertIfAbsent(ProgresoNivelEntity(usuarioId, MateriaId.ASTRONOMIA.name, nivel, fechaActualizacion = System.currentTimeMillis()))
    }

    private fun mascaraEsperada(nivel: Int): Int? = when (nivel) {
        1 -> NivelUnoAstronomiaContenido.mascaraCompleta
        2 -> NivelDosAstronomiaContenido.mascaraCompleta
        3 -> NivelTresAstronomiaContenido.mascaraCompleta
        4 -> NivelCuatroAstronomiaContenido.mascaraCompleta
        5 -> NivelCincoAstronomiaContenido.mascaraCompleta
        6 -> NivelSeisAstronomiaContenido.mascaraCompleta
        7 -> NivelSieteAstronomiaContenido.mascaraCompleta
        8 -> NivelOchoAstronomiaContenido.mascaraCompleta
        9 -> NivelNueveAstronomiaContenido.mascaraCompleta
        10 -> NivelDiezAstronomiaContenido.mascaraCompleta
        else -> null
    }
}
