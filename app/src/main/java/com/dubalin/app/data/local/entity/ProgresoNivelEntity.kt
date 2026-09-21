package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "progreso_nivel",
    primaryKeys = ["usuario_id", "materia_id", "nivel"],
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["usuario_id"])]
)
data class ProgresoNivelEntity(
    @ColumnInfo(name = "usuario_id") val usuarioId: Int,
    @ColumnInfo(name = "materia_id") val materiaId: String,
    val nivel: Int,
    @ColumnInfo(name = "temas_completados", defaultValue = "0") val temasCompletados: Int = 0,
    @ColumnInfo(name = "practica_completada", defaultValue = "0") val practicaCompletada: Boolean = false,
    @ColumnInfo(name = "nivel_completado", defaultValue = "0") val nivelCompletado: Boolean = false,
    @ColumnInfo(name = "mejor_puntaje", defaultValue = "0") val mejorPuntaje: Int = 0,
    @ColumnInfo(name = "fecha_actualizacion", defaultValue = "0") val fechaActualizacion: Long = 0L
)
