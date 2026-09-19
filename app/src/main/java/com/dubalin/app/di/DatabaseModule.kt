package com.dubalin.app.di

import android.content.Context
import androidx.room.Room
import com.dubalin.app.data.local.DubalinDatabase
import com.dubalin.app.data.local.MIGRATION_1_2
import com.dubalin.app.data.local.dao.ApunteDao
import com.dubalin.app.data.local.dao.CategoriaDao
import com.dubalin.app.data.local.dao.ConfiguracionDao
import com.dubalin.app.data.local.dao.EstadisticaUsuarioDao
import com.dubalin.app.data.local.dao.FlashcardDao
import com.dubalin.app.data.local.dao.MazoDao
import com.dubalin.app.data.local.dao.OpcionRespuestaDao
import com.dubalin.app.data.local.dao.PreguntaDao
import com.dubalin.app.data.local.dao.ResultadoQuizDao
import com.dubalin.app.data.local.dao.SeccionApuntesDao
import com.dubalin.app.data.local.dao.UsuarioDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt que provee la base de datos Room y sus DAOs.
 * Una sola instancia de DubalinDatabase para toda la app (Singleton).
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDubalinDatabase(
        @ApplicationContext context: Context
    ): DubalinDatabase =
        Room.databaseBuilder(
            context,
            DubalinDatabase::class.java,
            DubalinDatabase.DATABASE_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideUsuarioDao(database: DubalinDatabase): UsuarioDao =
        database.usuarioDao()

    @Provides
    fun provideConfiguracionDao(database: DubalinDatabase): ConfiguracionDao =
        database.configuracionDao()

    @Provides
    fun provideMazoDao(database: DubalinDatabase): MazoDao =
        database.mazoDao()

    @Provides
    fun provideFlashcardDao(database: DubalinDatabase): FlashcardDao =
        database.flashcardDao()

    @Provides
    fun provideCategoriaDao(database: DubalinDatabase): CategoriaDao =
        database.categoriaDao()

    @Provides
    fun providePreguntaDao(database: DubalinDatabase): PreguntaDao =
        database.preguntaDao()

    @Provides
    fun provideOpcionRespuestaDao(database: DubalinDatabase): OpcionRespuestaDao =
        database.opcionRespuestaDao()

    @Provides
    fun provideResultadoQuizDao(database: DubalinDatabase): ResultadoQuizDao =
        database.resultadoQuizDao()

    @Provides
    fun provideEstadisticaUsuarioDao(database: DubalinDatabase): EstadisticaUsuarioDao =
        database.estadisticaUsuarioDao()

    @Provides
    fun provideSeccionApuntesDao(database: DubalinDatabase): SeccionApuntesDao =
        database.seccionApuntesDao()

    @Provides
    fun provideApunteDao(database: DubalinDatabase): ApunteDao =
        database.apunteDao()
}
