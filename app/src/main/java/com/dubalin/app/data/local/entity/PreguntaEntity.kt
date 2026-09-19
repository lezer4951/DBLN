package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Pregunta de cuestionario, con 4 opciones fijas (opcionA-D) y el índice
 * de la respuesta correcta. Si se borra la categoría, se borran en
 * cascada sus preguntas.
 */
@Entity(
    tableName = "pregunta",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoria_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoria_id"])]
)
data class PreguntaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "categoria_id")
    val categoriaId: Int,

    @ColumnInfo(name = "enunciado")
    val enunciado: String,

    @ColumnInfo(name = "opcion_a")
    val opcionA: String,

    @ColumnInfo(name = "opcion_b")
    val opcionB: String,

    @ColumnInfo(name = "opcion_c")
    val opcionC: String,

    @ColumnInfo(name = "opcion_d")
    val opcionD: String,

    /** Índice de la opción correcta: 0=A, 1=B, 2=C, 3=D. */
    @ColumnInfo(name = "respuesta_correcta_indice")
    val respuestaCorrectaIndice: Int,

    /** Valores esperados: "Fácil", "Medio", "Difícil". */
    @ColumnInfo(name = "dificultad")
    val dificultad: String
)
