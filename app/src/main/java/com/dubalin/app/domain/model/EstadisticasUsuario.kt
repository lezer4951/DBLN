package com.dubalin.app.domain.model

data class EstadisticasUsuario(
    val flashcardsEstudiadas: Int,
    val quizzesCompletados: Int,
    val totalAciertos: Int,
    val totalErrores: Int
)
