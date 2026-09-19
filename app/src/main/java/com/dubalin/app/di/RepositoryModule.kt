package com.dubalin.app.di

import com.dubalin.app.data.repository.ApuntesRepositoryImpl
import com.dubalin.app.data.repository.AuthRepositoryImpl
import com.dubalin.app.data.repository.UsuarioRepositoryImpl
import com.dubalin.app.domain.repository.ApuntesRepository
import com.dubalin.app.domain.repository.AuthRepository
import com.dubalin.app.domain.repository.UsuarioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Enlaza cada interfaz de repositorio con su implementación actual.
 *
 * Para el futuro swap a Firebase: se crea FirebaseAuthRepositoryImpl
 * implementando AuthRepository, y se cambia SOLO la línea de @Binds de
 * aquí abajo. Nada en ViewModels ni UI necesita cambiar.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioRepository(impl: UsuarioRepositoryImpl): UsuarioRepository

    @Binds
    @Singleton
    abstract fun bindApuntesRepository(impl: ApuntesRepositoryImpl): ApuntesRepository
}
