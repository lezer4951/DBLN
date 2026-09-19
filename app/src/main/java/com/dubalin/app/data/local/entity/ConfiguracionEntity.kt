package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Configuración de la app, una fila por usuario (relación 1:1 -> índice
 * único en usuarioId).
 */
@Entity(
    tableName = "configuracion",
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
data class ConfiguracionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Int,

    @ColumnInfo(name = "tema_oscuro", defaultValue = "0")
    val temaOscuro: Boolean = false,

    @ColumnInfo(name = "idioma", defaultValue = "es")
    val idioma: String = "es",

    @ColumnInfo(name = "volumen", defaultValue = "1.0")
    val volumen: Float = 1.0f
)
