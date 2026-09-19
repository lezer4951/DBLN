package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dubalin.app.data.local.entity.CategoriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(categoria: CategoriaEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(categorias: List<CategoriaEntity>)

    /** Global, compartida por todos los usuarios. */
    @Query("SELECT * FROM categoria ORDER BY nombre ASC")
    fun observeAll(): Flow<List<CategoriaEntity>>

    @Query("SELECT * FROM categoria WHERE id = :id")
    suspend fun getById(id: Int): CategoriaEntity?
}
