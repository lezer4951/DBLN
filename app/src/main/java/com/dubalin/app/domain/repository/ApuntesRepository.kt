package com.dubalin.app.domain.repository

import com.dubalin.app.domain.model.Apunte
import com.dubalin.app.domain.model.SeccionApuntes
import kotlinx.coroutines.flow.Flow

interface ApuntesRepository {

    fun observarSecciones(usuarioId: Int): Flow<List<SeccionApuntes>>

    suspend fun crearSeccion(usuarioId: Int, nombre: String)

    suspend fun eliminarSeccion(seccion: SeccionApuntes)

    fun observarApuntes(seccionId: Int): Flow<List<Apunte>>

    suspend fun guardarApunte(seccionId: Int, apunteId: Int?, titulo: String, contenido: String)

    suspend fun eliminarApunte(apunte: Apunte)

    suspend fun obtenerApunte(apunteId: Int): Apunte?
}
