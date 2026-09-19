package com.dubalin.app.data.repository

import com.dubalin.app.data.local.dao.EstadisticaUsuarioDao
import com.dubalin.app.data.local.dao.UsuarioDao
import com.dubalin.app.data.local.entity.EstadisticaUsuarioEntity
import com.dubalin.app.data.local.entity.UsuarioEntity
import com.dubalin.app.domain.model.EstadisticasUsuario
import com.dubalin.app.domain.model.Usuario
import com.dubalin.app.domain.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val estadisticaUsuarioDao: EstadisticaUsuarioDao
) : UsuarioRepository {

    override suspend fun obtenerUsuario(usuarioId: Int): Usuario? =
        withContext(Dispatchers.IO) {
            usuarioDao.getById(usuarioId)?.toDomain()
        }

    override fun observarUsuario(usuarioId: Int): Flow<Usuario?> =
        usuarioDao.observeById(usuarioId).map { it?.toDomain() }

    override fun observarEstadisticas(usuarioId: Int): Flow<EstadisticasUsuario?> =
        estadisticaUsuarioDao.observeByUsuarioId(usuarioId).map { it?.toDomain() }
}

private fun UsuarioEntity.toDomain(): Usuario = Usuario(
    id = id,
    nombre = nombre,
    correo = correo,
    fechaRegistro = fechaRegistro,
    rachaDias = rachaDias
)

private fun EstadisticaUsuarioEntity.toDomain(): EstadisticasUsuario = EstadisticasUsuario(
    flashcardsEstudiadas = flashcardsEstudiadas,
    quizzesCompletados = quizzesCompletados,
    totalAciertos = totalAciertos,
    totalErrores = totalErrores
)
