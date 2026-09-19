package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dubalin.app.data.local.entity.OpcionRespuestaEntity

/**
 * DAO mínimo, reservado para cuando PreguntaEntity soporte opciones
 * dinámicas. No tiene consumidor todavía en la app.
 */
@Dao
interface OpcionRespuestaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(opcion: OpcionRespuestaEntity): Long

    @Query("SELECT * FROM opcion_respuesta WHERE pregunta_id = :preguntaId")
    suspend fun getByPreguntaId(preguntaId: Int): List<OpcionRespuestaEntity>
}
