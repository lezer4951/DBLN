package com.dubalin.app.domain.model

/**
 * Usuario en la capa de dominio. No incluye la contraseña ni su hash --
 * eso es un detalle de la capa de datos (UsuarioEntity) que nunca debe
 * subir hasta ViewModels o UI.
 */
data class Usuario(
    val id: Int,
    val nombre: String,
    val correo: String,
    val fechaRegistro: Long,
    val rachaDias: Int
)
