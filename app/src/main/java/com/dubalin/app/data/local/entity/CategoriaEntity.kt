package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Categoría/materia de los Cuestionarios Temáticos. Global, compartida
 * por todos los usuarios (Historia, Ciencia, Matemáticas, etc.), no
 * lleva usuarioId.
 */
@Entity(tableName = "categoria")
data class CategoriaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "nombre")
    val nombre: String
)
