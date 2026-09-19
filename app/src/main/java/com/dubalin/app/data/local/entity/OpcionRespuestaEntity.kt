package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Opción de respuesta dinámica, reservada para preguntas con más de 4
 * opciones en el futuro. Por ahora PreguntaEntity sigue usando sus
 * columnas fijas opcionA-D; esta tabla no se consume todavía desde
 * ningún flujo de la app.
 */
@Entity(
    tableName = "opcion_respuesta",
    foreignKeys = [
        ForeignKey(
            entity = PreguntaEntity::class,
            parentColumns = ["id"],
            childColumns = ["pregunta_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["pregunta_id"])]
)
data class OpcionRespuestaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "pregunta_id")
    val preguntaId: Int,

    @ColumnInfo(name = "texto_opcion")
    val textoOpcion: String
)
