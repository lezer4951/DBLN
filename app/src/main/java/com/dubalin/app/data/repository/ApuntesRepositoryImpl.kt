package com.dubalin.app.data.repository

import com.dubalin.app.data.local.dao.ApunteDao
import com.dubalin.app.data.local.dao.SeccionApuntesDao
import com.dubalin.app.data.local.entity.ApunteEntity
import com.dubalin.app.data.local.entity.SeccionApuntesEntity
import com.dubalin.app.domain.model.Apunte
import com.dubalin.app.domain.model.SeccionApuntes
import com.dubalin.app.domain.repository.ApuntesRepository
import com.dubalin.app.domain.repository.DuplicateSectionNameException
import com.dubalin.app.domain.repository.InvalidNoteException
import com.dubalin.app.domain.repository.NoteNotFoundException
import com.dubalin.app.domain.repository.SectionNotFoundException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ApuntesRepositoryImpl @Inject constructor(
    private val seccionApuntesDao: SeccionApuntesDao,
    private val apunteDao: ApunteDao
) : ApuntesRepository {

    override fun observarSecciones(usuarioId: Int): Flow<List<SeccionApuntes>> =
        seccionApuntesDao.observeByUsuarioId(usuarioId).map { list -> list.map { it.toDomain() } }

    override suspend fun crearSeccion(
        usuarioId: Int,
        nombre: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val nombreNormalizado = nombre.normalizarTexto()
            validarNombreDisponible(usuarioId, nombreNormalizado)

            seccionApuntesDao.insert(
                SeccionApuntesEntity(
                    usuarioId = usuarioId,
                    nombre = nombreNormalizado
                )
            )
            Unit
        }
    }

    override suspend fun actualizarSeccion(
        seccionId: Int,
        usuarioId: Int,
        nombre: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val actual = seccionApuntesDao.getById(seccionId)
                ?.takeIf { it.usuarioId == usuarioId }
                ?: throw SectionNotFoundException()

            val nombreNormalizado = nombre.normalizarTexto()
            validarNombreDisponible(usuarioId, nombreNormalizado, seccionId)
            seccionApuntesDao.update(actual.copy(nombre = nombreNormalizado))
        }
    }

    override suspend fun eliminarSeccion(
        seccion: SeccionApuntes
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val actual = seccionApuntesDao.getById(seccion.id)
                ?.takeIf { it.usuarioId == seccion.usuarioId }
                ?: throw SectionNotFoundException()

            seccionApuntesDao.delete(actual)
        }
    }

    override fun observarApuntes(seccionId: Int): Flow<List<Apunte>> =
        apunteDao.observeBySeccionId(seccionId).map { list -> list.map { it.toDomain() } }

    override suspend fun guardarApunte(
        seccionId: Int,
        apunteId: Int?,
        titulo: String,
        contenido: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val tituloNormalizado = titulo.normalizarTexto()
            val contenidoNormalizado = contenido.trim()
            if (tituloNormalizado.isBlank() || contenidoNormalizado.isBlank()) {
                throw InvalidNoteException()
            }
            if (seccionApuntesDao.getById(seccionId) == null) {
                throw SectionNotFoundException()
            }

            if (apunteId == null) {
                apunteDao.insert(
                    ApunteEntity(
                        seccionId = seccionId,
                        titulo = tituloNormalizado,
                        contenido = contenidoNormalizado,
                        fechaActualizacion = System.currentTimeMillis()
                    )
                )
            } else {
                val actual = apunteDao.getById(apunteId)
                    ?.takeIf { it.seccionId == seccionId }
                    ?: throw NoteNotFoundException()

                val updatedRows = apunteDao.update(
                    actual.copy(
                        titulo = tituloNormalizado,
                        contenido = contenidoNormalizado,
                        fechaActualizacion = System.currentTimeMillis()
                    )
                )
                if (updatedRows == 0) throw NoteNotFoundException()
            }
            Unit
        }
    }

    override suspend fun eliminarApunte(
        apunte: Apunte
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val actual = apunteDao.getById(apunte.id)
                ?.takeIf { it.seccionId == apunte.seccionId }
                ?: throw NoteNotFoundException()

            if (apunteDao.delete(actual) == 0) {
                throw NoteNotFoundException()
            }
        }
    }

    override suspend fun obtenerApunte(
        apunteId: Int
    ): Result<Apunte> = withContext(Dispatchers.IO) {
        runCatching {
            apunteDao.getById(apunteId)?.toDomain()
                ?: throw NoteNotFoundException()
        }
    }

    private suspend fun validarNombreDisponible(
        usuarioId: Int,
        nombre: String,
        excludedId: Int = 0
    ) {
        if (seccionApuntesDao.existsByName(usuarioId, nombre, excludedId)) {
            throw DuplicateSectionNameException()
        }
    }
}

private fun String.normalizarTexto(): String =
    trim().replace(Regex("\\s+"), " ")

private fun SeccionApuntesEntity.toDomain(): SeccionApuntes =
    SeccionApuntes(id = id, usuarioId = usuarioId, nombre = nombre)

private fun ApunteEntity.toDomain(): Apunte =
    Apunte(
        id = id,
        seccionId = seccionId,
        titulo = titulo,
        contenido = contenido,
        fechaActualizacion = fechaActualizacion
    )
