package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dubalin.app.data.local.entity.MazoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MazoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mazo: MazoEntity): Long

    @Update
    suspend fun update(mazo: MazoEntity)

    @Delete
    suspend fun delete(mazo: MazoEntity)

    @Query("SELECT * FROM mazo WHERE id = :id")
    suspend fun getById(id: Int): MazoEntity?

    /** Mazos privados del usuario, para el explorador de Flashcards. */
    @Query("SELECT * FROM mazo WHERE usuario_id = :usuarioId ORDER BY titulo ASC")
    fun observeByUsuarioId(usuarioId: Int): Flow<List<MazoEntity>>
}
