package com.dubalin.app.data.repository

import com.dubalin.app.core.util.PasswordHasher
import com.dubalin.app.data.local.dao.UsuarioDao
import com.dubalin.app.data.local.entity.UsuarioEntity
import com.dubalin.app.domain.model.Usuario
import com.dubalin.app.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementación local de AuthRepository: usuario/contraseña hasheada
 * en Room, sin red. Ver AuthRepository para el contrato y la nota sobre
 * el futuro swap a Firebase.
 */
class AuthRepositoryImpl @Inject constructor(
    private val usuarioDao: UsuarioDao
) : AuthRepository {

    override suspend fun registrar(
        nombre: String,
        correo: String,
        password: String
    ): Result<Usuario> = withContext(Dispatchers.IO) {
        runCatching {
            val existente = usuarioDao.getByCorreo(correo)
            if (existente != null) {
                throw IllegalStateException("Ya existe una cuenta con ese correo.")
            }

            val entity = UsuarioEntity(
                nombre = nombre,
                correo = correo,
                password = PasswordHasher.hash(password),
                fechaRegistro = System.currentTimeMillis()
            )
            val id = usuarioDao.insert(entity)

            entity.copy(id = id.toInt()).toDomain()
        }
    }

    override suspend fun login(
        correo: String,
        password: String
    ): Result<Usuario> = withContext(Dispatchers.IO) {
        runCatching {
            val entity = usuarioDao.getByCorreo(correo)
                ?: throw NoSuchElementException("No existe una cuenta con ese correo.")

            if (!PasswordHasher.verify(password, entity.password)) {
                throw SecurityException("Contraseña incorrecta.")
            }

            entity.toDomain()
        }
    }
}

private fun UsuarioEntity.toDomain(): Usuario = Usuario(
    id = id,
    nombre = nombre,
    correo = correo,
    fechaRegistro = fechaRegistro,
    rachaDias = rachaDias
)
