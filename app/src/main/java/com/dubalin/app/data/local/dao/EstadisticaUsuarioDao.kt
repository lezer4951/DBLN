package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dubalin.app.data.local.entity.EstadisticaUsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EstadisticaUsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estadistica: EstadisticaUsuarioEntity): Long

    @Update
    suspend fun update(estadistica: EstadisticaUsuarioEntity)

    /** Relación 1:1 -- cada usuario tiene una sola fila de estadísticas. */
    @Query("SELECT * FROM estadistica_usuario WHERE usuario_id = :usuarioId LIMIT 1")
    suspend fun getByUsuarioId(usuarioId: Int): EstadisticaUsuarioEntity?

    @Query("SELECT * FROM estadistica_usuario WHERE usuario_id = :usuarioId LIMIT 1")
    fun observeByUsuarioId(usuarioId: Int): Flow<EstadisticaUsuarioEntity?>
}
