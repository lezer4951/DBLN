package com.dubalin.app.domain.model

data class Mazo(val id: Int, val titulo: String, val descripcion: String)
data class Flashcard(val id: Int, val mazoId: Int, val frente: String, val reverso: String)
