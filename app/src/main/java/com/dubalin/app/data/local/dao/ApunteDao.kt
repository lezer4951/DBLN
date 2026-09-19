package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dubalin.app.data.local.entity.ApunteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApunteDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(apunte: ApunteEntity): Long

    @Update
    suspend fun update(apunte: ApunteEntity): Int

    @Delete
    suspend fun delete(apunte: ApunteEntity): Int

    @Query("SELECT * FROM apunte WHERE id = :id")
    suspend fun getById(id: Int): ApunteEntity?

    @Query("SELECT * FROM apunte WHERE seccion_id = :seccionId ORDER BY fecha_actualizacion DESC")
    fun observeBySeccionId(seccionId: Int): Flow<List<ApunteEntity>>
}
