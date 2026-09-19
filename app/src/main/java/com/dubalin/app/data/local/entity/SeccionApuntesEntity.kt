package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Sección para organizar Mis Apuntes (ej. "Inglés", "Historia").
 * Privada por usuario, igual que Mazo -- el usuario la crea libremente
 * para no revolver temas o materias.
 */
@Entity(
    tableName = "seccion_apuntes",
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
data class SeccionApuntesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Int,

    @ColumnInfo(name = "nombre")
    val nombre: String
)
