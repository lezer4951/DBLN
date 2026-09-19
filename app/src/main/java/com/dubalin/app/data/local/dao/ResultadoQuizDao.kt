package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dubalin.app.data.local.entity.ResultadoQuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultadoQuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resultado: ResultadoQuizEntity): Long

    /** Historial completo del usuario, más reciente primero. */
    @Query("SELECT * FROM resultado_quiz WHERE usuario_id = :usuarioId ORDER BY fecha DESC")
    fun observeByUsuarioId(usuarioId: Int): Flow<List<ResultadoQuizEntity>>

    /** Historial filtrado por materia, para el desglose en Perfil. */
    @Query(
        "SELECT * FROM resultado_quiz WHERE usuario_id = :usuarioId " +
            "AND categoria_id = :categoriaId ORDER BY fecha DESC"
    )
    fun observeByUsuarioYCategoria(
        usuarioId: Int,
        categoriaId: Int
    ): Flow<List<ResultadoQuizEntity>>
}
