package com.dubalin.app.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.dubalin.app.core.util.PasswordHasher
import com.dubalin.app.data.local.dao.UsuarioDao
import com.dubalin.app.data.local.entity.UsuarioEntity
import com.dubalin.app.domain.model.Usuario
import com.dubalin.app.domain.repository.AuthRepository
import com.dubalin.app.domain.repository.EmailAlreadyRegisteredException
import com.dubalin.app.domain.repository.InvalidCredentialsException
import com.dubalin.app.domain.repository.UserNotFoundException
import java.util.Locale
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
            val correoNormalizado = correo.normalizarCorreo()
            val existente = usuarioDao.getByCorreo(correoNormalizado)
            if (existente != null) {
                throw EmailAlreadyRegisteredException()
            }

            val entity = UsuarioEntity(
                nombre = nombre.trim(),
                correo = correoNormalizado,
                password = PasswordHasher.hash(password),
                fechaRegistro = System.currentTimeMillis()
            )

            val id = try {
                usuarioDao.insert(entity)
            } catch (_: SQLiteConstraintException) {
                throw EmailAlreadyRegisteredException()
            }

            entity.copy(id = id.toInt()).toDomain()
        }
    }

    override suspend fun login(
        correo: String,
        password: String
    ): Result<Usuario> = withContext(Dispatchers.IO) {
        runCatching {
            val entity = usuarioDao.getByCorreo(correo.normalizarCorreo())
                ?: throw UserNotFoundException()

            if (!PasswordHasher.verify(password, entity.password)) {
                throw InvalidCredentialsException()
            }

            entity.toDomain()
        }
    }
}

private fun String.normalizarCorreo(): String =
    trim().lowercase(Locale.ROOT)

private fun UsuarioEntity.toDomain(): Usuario = Usuario(
    id = id,
    nombre = nombre,
    correo = correo,
    fechaRegistro = fechaRegistro,
    rachaDias = rachaDias
)
