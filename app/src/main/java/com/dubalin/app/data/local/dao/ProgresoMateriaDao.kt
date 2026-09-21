package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dubalin.app.data.local.entity.ProgresoMateriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgresoMateriaDao {

    @Query("SELECT * FROM progreso_materia WHERE usuario_id = :usuarioId AND materia_id = :materiaId LIMIT 1")
    suspend fun get(usuarioId: Int, materiaId: String): ProgresoMateriaEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfAbsent(progreso: ProgresoMateriaEntity): Long

    @Query(
        "SELECT * FROM progreso_materia " +
            "WHERE usuario_id = :usuarioId AND materia_id = :materiaId LIMIT 1"
    )
    fun observe(usuarioId: Int, materiaId: String): Flow<ProgresoMateriaEntity?>

    @Query(
        "UPDATE progreso_materia SET " +
            "pagina_actual = MAX(pagina_actual, :pagina), " +
            "fecha_actualizacion = :fecha " +
            "WHERE usuario_id = :usuarioId AND materia_id = :materiaId"
    )
    suspend fun updatePagina(
        usuarioId: Int,
        materiaId: String,
        pagina: Int,
        fecha: Long
    ): Int

    @Query(
        "UPDATE progreso_materia SET " +
            "onboarding_completado = 1, experiencia = :experiencia, " +
            "minutos_diarios = :minutosDiarios, formato_aprendizaje = :formato, " +
            "fecha_actualizacion = :fecha " +
            "WHERE usuario_id = :usuarioId AND materia_id = :materiaId"
    )
    suspend fun updatePlan(
        usuarioId: Int,
        materiaId: String,
        experiencia: String,
        minutosDiarios: Int,
        formato: String,
        fecha: Long
    ): Int

    @Query(
        "UPDATE progreso_materia SET " +
            "temas_completados = temas_completados | :mascaraTema, " +
            "pagina_actual = MAX(pagina_actual, :pagina), fecha_actualizacion = :fecha " +
            "WHERE usuario_id = :usuarioId AND materia_id = :materiaId"
    )
    suspend fun completarTema(
        usuarioId: Int,
        materiaId: String,
        mascaraTema: Int,
        pagina: Int,
        fecha: Long
    ): Int

    @Query(
        "UPDATE progreso_materia SET " +
            "nivel_actual = CASE WHEN :aprobado = 1 THEN MAX(nivel_actual, 1) ELSE nivel_actual END, " +
            "nivel_cero_completado = CASE WHEN :aprobado = 1 THEN 1 ELSE nivel_cero_completado END, " +
            "mejor_puntaje = MAX(mejor_puntaje, :puntaje), " +
            "fecha_actualizacion = :fecha " +
            "WHERE usuario_id = :usuarioId AND materia_id = :materiaId"
    )
    suspend fun updateEvaluacionNivelCero(
        usuarioId: Int,
        materiaId: String,
        puntaje: Int,
        aprobado: Boolean,
        fecha: Long
    ): Int

    @Query("UPDATE progreso_materia SET nivel_actual = MAX(nivel_actual, :nivelDisponible), mejor_puntaje = MAX(mejor_puntaje, :puntaje), fecha_actualizacion = :fecha WHERE usuario_id = :usuarioId AND materia_id = :materiaId")
    suspend fun avanzarNivel(usuarioId: Int, materiaId: String, nivelDisponible: Int, puntaje: Int, fecha: Long): Int
}
