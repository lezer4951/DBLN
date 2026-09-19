package com.dubalin.app.domain.repository

import com.dubalin.app.domain.model.Apunte
import com.dubalin.app.domain.model.SeccionApuntes
import kotlinx.coroutines.flow.Flow

interface ApuntesRepository {

    fun observarSecciones(usuarioId: Int): Flow<List<SeccionApuntes>>

    suspend fun crearSeccion(usuarioId: Int, nombre: String): Result<Unit>

    suspend fun actualizarSeccion(
        seccionId: Int,
        usuarioId: Int,
        nombre: String
    ): Result<Unit>

    suspend fun eliminarSeccion(seccion: SeccionApuntes): Result<Unit>

    fun observarApuntes(seccionId: Int): Flow<List<Apunte>>

    suspend fun guardarApunte(
        seccionId: Int,
        apunteId: Int?,
        titulo: String,
        contenido: String
    ): Result<Unit>

    suspend fun eliminarApunte(apunte: Apunte): Result<Unit>

    suspend fun obtenerApunte(apunteId: Int): Result<Apunte>
}
