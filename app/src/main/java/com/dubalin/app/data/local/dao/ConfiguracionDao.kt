package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dubalin.app.data.local.entity.ConfiguracionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfiguracionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(configuracion: ConfiguracionEntity): Long

    @Update
    suspend fun update(configuracion: ConfiguracionEntity)

    /** Relación 1:1 -- cada usuario tiene una sola fila de configuración. */
    @Query("SELECT * FROM configuracion WHERE usuario_id = :usuarioId LIMIT 1")
    suspend fun getByUsuarioId(usuarioId: Int): ConfiguracionEntity?

    @Query("SELECT * FROM configuracion WHERE usuario_id = :usuarioId LIMIT 1")
    fun observeByUsuarioId(usuarioId: Int): Flow<ConfiguracionEntity?>
}
