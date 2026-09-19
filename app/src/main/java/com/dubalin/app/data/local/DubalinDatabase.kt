package com.dubalin.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
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
import com.dubalin.app.data.local.entity.ApunteEntity
import com.dubalin.app.data.local.entity.CategoriaEntity
import com.dubalin.app.data.local.entity.ConfiguracionEntity
import com.dubalin.app.data.local.entity.EstadisticaUsuarioEntity
import com.dubalin.app.data.local.entity.FlashcardEntity
import com.dubalin.app.data.local.entity.MazoEntity
import com.dubalin.app.data.local.entity.OpcionRespuestaEntity
import com.dubalin.app.data.local.entity.PreguntaEntity
import com.dubalin.app.data.local.entity.ResultadoQuizEntity
import com.dubalin.app.data.local.entity.SeccionApuntesEntity
import com.dubalin.app.data.local.entity.UsuarioEntity

/**
 * Base de datos Room de Dubalin. Los esquemas se exportan a app/schemas
 * para conservar el historial y validar migraciones futuras.
 *
 * version = 2: agrega seccion_apuntes y apunte mediante MIGRATION_1_2.
 */
@Database(
    entities = [
        UsuarioEntity::class,
        ConfiguracionEntity::class,
        MazoEntity::class,
        FlashcardEntity::class,
        CategoriaEntity::class,
        PreguntaEntity::class,
        OpcionRespuestaEntity::class,
        ResultadoQuizEntity::class,
        EstadisticaUsuarioEntity::class,
        SeccionApuntesEntity::class,
        ApunteEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class DubalinDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun configuracionDao(): ConfiguracionDao
    abstract fun mazoDao(): MazoDao
    abstract fun flashcardDao(): FlashcardDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun preguntaDao(): PreguntaDao
    abstract fun opcionRespuestaDao(): OpcionRespuestaDao
    abstract fun resultadoQuizDao(): ResultadoQuizDao
    abstract fun estadisticaUsuarioDao(): EstadisticaUsuarioDao
    abstract fun seccionApuntesDao(): SeccionApuntesDao
    abstract fun apunteDao(): ApunteDao

    companion object {
        const val DATABASE_NAME = "dubalin.db"
    }
}
