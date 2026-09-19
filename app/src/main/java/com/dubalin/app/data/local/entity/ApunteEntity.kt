package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Apunte individual dentro de una sección (ej. "Modal verbs" dentro de
 * la sección "Inglés"). Si se borra la sección, se borran en cascada
 * sus apuntes.
 */
@Entity(
    tableName = "apunte",
    foreignKeys = [
        ForeignKey(
            entity = SeccionApuntesEntity::class,
            parentColumns = ["id"],
            childColumns = ["seccion_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["seccion_id"])]
)
data class ApunteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "seccion_id")
    val seccionId: Int,

    @ColumnInfo(name = "titulo")
    val titulo: String,

    @ColumnInfo(name = "contenido")
    val contenido: String,

    @ColumnInfo(name = "fecha_actualizacion")
    val fechaActualizacion: Long
)
