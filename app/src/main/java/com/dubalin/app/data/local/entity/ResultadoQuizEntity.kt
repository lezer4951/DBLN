package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Historial de resultados de cuestionarios. Lleva usuarioId y categoriaId
 * para poder consultar el historial y las estadísticas agrupadas por
 * materia desde Perfil.
 */
@Entity(
    tableName = "resultado_quiz",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoriaEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoria_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["usuario_id"]),
        Index(value = ["categoria_id"])
    ]
)
data class ResultadoQuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Int,

    @ColumnInfo(name = "categoria_id")
    val categoriaId: Int,

    @ColumnInfo(name = "puntaje")
    val puntaje: Int,

    @ColumnInfo(name = "aciertos")
    val aciertos: Int,

    @ColumnInfo(name = "errores")
    val errores: Int,

    @ColumnInfo(name = "fecha")
    val fecha: Long
)
