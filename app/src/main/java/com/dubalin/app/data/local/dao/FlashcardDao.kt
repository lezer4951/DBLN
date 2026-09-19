package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dubalin.app.data.local.entity.FlashcardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(flashcard: FlashcardEntity): Long

    @Update
    suspend fun update(flashcard: FlashcardEntity)

    @Delete
    suspend fun delete(flashcard: FlashcardEntity)

    @Query("SELECT * FROM flashcard WHERE id = :id")
    suspend fun getById(id: Int): FlashcardEntity?

    @Query("SELECT * FROM flashcard WHERE mazo_id = :mazoId")
    fun observeByMazoId(mazoId: Int): Flow<List<FlashcardEntity>>
}
