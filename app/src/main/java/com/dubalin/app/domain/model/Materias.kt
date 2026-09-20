package com.dubalin.app.domain.model
data class Materia(val nombre: String, val temas: List<String>)
object Materias {
    val todas = listOf(
        Materia("Matemáticas", listOf("Fracciones", "Porcentajes", "Álgebra", "Geometría")),
        Materia("Español", listOf("Ortografía", "Gramática", "Comprensión lectora")),
        Materia("Inglés", listOf("Vocabulario", "Verbos", "Comprensión lectora")),
        Materia("Física", listOf("Movimiento", "Fuerza", "Energía")),
        Materia("Química", listOf("Materia", "Átomos", "Enlaces")),
        Materia("Biología", listOf("Células", "Genética", "Ecosistemas")),
        Materia("Historia", listOf("Historia de México", "Historia universal")),
        Materia("Geografía", listOf("Mapas", "Continentes", "Climas")),
        Materia("Formación Cívica y Ética", listOf("Derechos", "Convivencia", "Ciudadanía"))
    )
}
