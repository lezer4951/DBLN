package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dubalin.app.data.local.entity.PreguntaEntity

@Dao
interface PreguntaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pregunta: PreguntaEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(preguntas: List<PreguntaEntity>)

    @Query("SELECT * FROM pregunta WHERE categoria_id = :categoriaId")
    suspend fun getByCategoriaId(categoriaId: Int): List<PreguntaEntity>

    @Query(
        "SELECT * FROM pregunta WHERE categoria_id = :categoriaId " +
            "AND dificultad = :dificultad"
    )
    suspend fun getByCategoriaYDificultad(
        categoriaId: Int,
        dificultad: String
    ): List<PreguntaEntity>

    @Query("SELECT * FROM pregunta WHERE id = :id")
    suspend fun getById(id: Int): PreguntaEntity?
}
