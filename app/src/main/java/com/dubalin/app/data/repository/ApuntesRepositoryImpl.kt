package com.dubalin.app.data.repository

import com.dubalin.app.data.local.dao.ApunteDao
import com.dubalin.app.data.local.dao.SeccionApuntesDao
import com.dubalin.app.data.local.entity.ApunteEntity
import com.dubalin.app.data.local.entity.SeccionApuntesEntity
import com.dubalin.app.domain.model.Apunte
import com.dubalin.app.domain.model.SeccionApuntes
import com.dubalin.app.domain.repository.ApuntesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApuntesRepositoryImpl @Inject constructor(
    private val seccionApuntesDao: SeccionApuntesDao,
    private val apunteDao: ApunteDao
) : ApuntesRepository {

    override fun observarSecciones(usuarioId: Int): Flow<List<SeccionApuntes>> =
        seccionApuntesDao.observeByUsuarioId(usuarioId).map { list -> list.map { it.toDomain() } }

    override suspend fun crearSeccion(usuarioId: Int, nombre: String) {
        withContext(Dispatchers.IO) {
            seccionApuntesDao.insert(
                SeccionApuntesEntity(usuarioId = usuarioId, nombre = nombre)
            )
        }
    }

    override suspend fun eliminarSeccion(seccion: SeccionApuntes) {
        withContext(Dispatchers.IO) {
            seccionApuntesDao.delete(seccion.toEntity())
        }
    }

    override fun observarApuntes(seccionId: Int): Flow<List<Apunte>> =
        apunteDao.observeBySeccionId(seccionId).map { list -> list.map { it.toDomain() } }

    override suspend fun guardarApunte(
        seccionId: Int,
        apunteId: Int?,
        titulo: String,
        contenido: String
    ) {
        withContext(Dispatchers.IO) {
            val entity = ApunteEntity(
                id = apunteId ?: 0,
                seccionId = seccionId,
                titulo = titulo,
                contenido = contenido,
                fechaActualizacion = System.currentTimeMillis()
            )
            if (apunteId == null) {
                apunteDao.insert(entity)
            } else {
                apunteDao.update(entity)
            }
        }
    }

    override suspend fun eliminarApunte(apunte: Apunte) {
        withContext(Dispatchers.IO) {
            apunteDao.delete(apunte.toEntity())
        }
    }

    override suspend fun obtenerApunte(apunteId: Int): Apunte? =
        withContext(Dispatchers.IO) {
            apunteDao.getById(apunteId)?.toDomain()
        }
}

private fun SeccionApuntesEntity.toDomain(): SeccionApuntes =
    SeccionApuntes(id = id, usuarioId = usuarioId, nombre = nombre)

private fun SeccionApuntes.toEntity(): SeccionApuntesEntity =
    SeccionApuntesEntity(id = id, usuarioId = usuarioId, nombre = nombre)

private fun ApunteEntity.toDomain(): Apunte =
    Apunte(
        id = id,
        seccionId = seccionId,
        titulo = titulo,
        contenido = contenido,
        fechaActualizacion = fechaActualizacion
    )

private fun Apunte.toEntity(): ApunteEntity =
    ApunteEntity(
        id = id,
        seccionId = seccionId,
        titulo = titulo,
        contenido = contenido,
        fechaActualizacion = fechaActualizacion
    )
