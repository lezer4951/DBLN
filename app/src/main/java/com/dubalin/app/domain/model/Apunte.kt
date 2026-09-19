package com.dubalin.app.domain.model

data class Apunte(
    val id: Int,
    val seccionId: Int,
    val titulo: String,
    val contenido: String,
    val fechaActualizacion: Long
)
