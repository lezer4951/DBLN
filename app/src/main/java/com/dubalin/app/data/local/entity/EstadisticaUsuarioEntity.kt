package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Estadísticas acumuladas globales del usuario, una fila por usuario
 * (relación 1:1 -> índice único en usuarioId).
 */
@Entity(
    tableName = "estadistica_usuario",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["usuario_id"], unique = true)]
)
data class EstadisticaUsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Int,

    @ColumnInfo(name = "flashcards_estudiadas", defaultValue = "0")
    val flashcardsEstudiadas: Int = 0,

    @ColumnInfo(name = "quizzes_completados", defaultValue = "0")
    val quizzesCompletados: Int = 0,

    @ColumnInfo(name = "total_aciertos", defaultValue = "0")
    val totalAciertos: Int = 0,

    @ColumnInfo(name = "total_errores", defaultValue = "0")
    val totalErrores: Int = 0
)
