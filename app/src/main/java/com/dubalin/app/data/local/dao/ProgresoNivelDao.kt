package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dubalin.app.data.local.entity.ProgresoNivelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgresoNivelDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfAbsent(entity: ProgresoNivelEntity): Long

    @Query("SELECT * FROM progreso_nivel WHERE usuario_id = :usuarioId AND materia_id = :materiaId AND nivel = :nivel LIMIT 1")
    fun observe(usuarioId: Int, materiaId: String, nivel: Int): Flow<ProgresoNivelEntity?>

    @Query("SELECT * FROM progreso_nivel WHERE usuario_id = :usuarioId AND materia_id = :materiaId AND nivel = :nivel LIMIT 1")
    suspend fun get(usuarioId: Int, materiaId: String, nivel: Int): ProgresoNivelEntity?

    @Query("UPDATE progreso_nivel SET temas_completados = temas_completados | :mascara, fecha_actualizacion = :fecha WHERE usuario_id = :usuarioId AND materia_id = :materiaId AND nivel = :nivel")
    suspend fun completarTema(usuarioId: Int, materiaId: String, nivel: Int, mascara: Int, fecha: Long): Int

    @Query("UPDATE progreso_nivel SET practica_completada = 1, fecha_actualizacion = :fecha WHERE usuario_id = :usuarioId AND materia_id = :materiaId AND nivel = :nivel")
    suspend fun completarPractica(usuarioId: Int, materiaId: String, nivel: Int, fecha: Long): Int

    @Query("UPDATE progreso_nivel SET nivel_completado = CASE WHEN :aprobado = 1 THEN 1 ELSE nivel_completado END, mejor_puntaje = MAX(mejor_puntaje, :puntaje), fecha_actualizacion = :fecha WHERE usuario_id = :usuarioId AND materia_id = :materiaId AND nivel = :nivel")
    suspend fun registrarEvaluacion(usuarioId: Int, materiaId: String, nivel: Int, puntaje: Int, aprobado: Boolean, fecha: Long): Int
}
