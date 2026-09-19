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

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(seccion: SeccionApuntesEntity): Long

    @Update
    suspend fun update(seccion: SeccionApuntesEntity)

    @Delete
    suspend fun delete(seccion: SeccionApuntesEntity)

    @Query("SELECT * FROM seccion_apuntes WHERE id = :id")
    suspend fun getById(id: Int): SeccionApuntesEntity?

    @Query(
        """
        SELECT EXISTS(
            SELECT 1
            FROM seccion_apuntes
            WHERE usuario_id = :usuarioId
              AND LOWER(TRIM(nombre)) = LOWER(TRIM(:nombre))
              AND id != :excludedId
        )
        """
    )
    suspend fun existsByName(
        usuarioId: Int,
        nombre: String,
        excludedId: Int = 0
    ): Boolean

    @Query("SELECT * FROM seccion_apuntes WHERE usuario_id = :usuarioId ORDER BY nombre COLLATE NOCASE ASC")
    fun observeByUsuarioId(usuarioId: Int): Flow<List<SeccionApuntesEntity>>
}
