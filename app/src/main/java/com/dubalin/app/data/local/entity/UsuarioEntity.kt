package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Cuenta de usuario local (sin backend por ahora).
 * El campo [password] guarda el hash, nunca la contraseña en texto plano
 * (el hashing se implementa en el Paso 1.4, AuthRepository).
 */
@Entity(
    tableName = "usuario",
    indices = [Index(value = ["correo"], unique = true)]
)
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "correo")
    val correo: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "fecha_registro")
    val fechaRegistro: Long,

    @ColumnInfo(name = "racha_dias", defaultValue = "0")
    val rachaDias: Int = 0
)
