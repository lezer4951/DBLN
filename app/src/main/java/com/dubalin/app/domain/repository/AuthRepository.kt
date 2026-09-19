package com.dubalin.app.domain.repository

import com.dubalin.app.domain.model.Usuario

/**
 * Contrato de autenticación. La implementación actual (AuthRepositoryImpl)
 * es 100% local con Room. Cuando se integre Firebase más adelante, se
 * crea FirebaseAuthRepositoryImpl implementando esta misma interfaz y
 * se cambia el binding en RepositoryModule -- ViewModels y UI no se tocan.
 */
interface AuthRepository {

    /**
     * Registra un nuevo usuario. Falla si el correo ya está en uso.
     */
    suspend fun registrar(nombre: String, correo: String, password: String): Result<Usuario>

    /**
     * Autentica contra el usuario guardado localmente. Falla si el
     * correo no existe o la contraseña no coincide.
     */
    suspend fun login(correo: String, password: String): Result<Usuario>
}
