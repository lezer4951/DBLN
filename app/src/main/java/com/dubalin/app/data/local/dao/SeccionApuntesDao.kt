package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dubalin.app.data.local.entity.SeccionApuntesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeccionApuntesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(seccion: SeccionApuntesEntity): Long

    @Update
    suspend fun update(seccion: SeccionApuntesEntity)

    @Delete
    suspend fun delete(seccion: SeccionApuntesEntity)

    @Query("SELECT * FROM seccion_apuntes WHERE id = :id")
    suspend fun getById(id: Int): SeccionApuntesEntity?

    @Query("SELECT * FROM seccion_apuntes WHERE usuario_id = :usuarioId ORDER BY nombre ASC")
    fun observeByUsuarioId(usuarioId: Int): Flow<List<SeccionApuntesEntity>>
}
