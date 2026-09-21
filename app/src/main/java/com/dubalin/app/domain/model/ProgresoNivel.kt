package com.dubalin.app.domain.model

data class ProgresoNivel(
    val nivel: Int,
    val temasCompletados: Int = 0,
    val practicaCompletada: Boolean = false,
    val nivelCompletado: Boolean = false,
    val mejorPuntaje: Int = 0
)
