package com.dubalin.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo base de inyección de dependencias, instalado a nivel de aplicación
 * (SingletonComponent = una sola instancia viva durante todo el ciclo de vida
 * de la app).
 *
 * Por ahora solo expone el Context de aplicación, que es la dependencia
 * transversal que casi todo módulo futuro (Room, DataStore, Repositories)
 * va a necesitar.
 *
 * Próximas ampliaciones (en módulos siguientes, no aquí):
 * - Módulo 1: DatabaseModule -> provee DubalinDatabase y los DAOs.
 * - Módulo 1: RepositoryModule -> @Binds de AuthRepository a su implementación local.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ): Context = context
}
