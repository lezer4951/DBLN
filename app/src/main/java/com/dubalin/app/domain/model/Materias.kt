package com.dubalin.app.domain.model

enum class MateriaId {
    ASTRONOMIA,
    MATEMATICAS,
    ESPANOL,
    INGLES,
    FRANCES,
    PORTUGUES,
    ALEMAN,
    FISICA,
    QUIMICA,
    BIOLOGIA,
    HISTORIA,
    GEOLOGIA,
    PSICOLOGIA,
    SOCIOLOGIA,
    TECNOLOGIA,
    ESCRITURA,
    ANATOMIA
}

data class Materia(
    val id: MateriaId,
    val nombre: String,
    val descripcion: String,
    val temas: List<String>,
    val disponible: Boolean = false
)

/**
 * Catálogo académico inicial. Solo Astronomía está activa durante la primera
 * etapa; conservar las demás materias aquí permite habilitarlas sin cambiar
 * los identificadores usados por progreso, evaluaciones o analíticas.
 */
object Materias {
    val todas = listOf(
        Materia(
            id = MateriaId.ASTRONOMIA,
            nombre = "Astronomía",
            descripcion = "Explora el Sistema Solar, las estrellas y el universo.",
            temas = listOf(
                "Introducción a la astronomía",
                "Sistema Solar",
                "La Tierra y la Luna",
                "El Sol y las estrellas",
                "Planetas y exoplanetas",
                "Galaxias",
                "Evolución estelar y nebulosas",
                "Agujeros negros",
                "Cosmología",
                "Exploración espacial",
                "Astronomía avanzada"
            ),
            disponible = true
        ),
        Materia(MateriaId.MATEMATICAS, "Matemáticas", "Números, patrones y resolución de problemas.", emptyList()),
        Materia(MateriaId.ESPANOL, "Español", "Lengua, lectura y comunicación escrita.", emptyList()),
        Materia(MateriaId.INGLES, "Inglés", "Vocabulario, gramática y comprensión.", emptyList()),
        Materia(MateriaId.FRANCES, "Francés", "Vocabulario, pronunciación y conversación.", emptyList()),
        Materia(MateriaId.PORTUGUES, "Portugués", "Comprensión, gramática y comunicación.", emptyList()),
        Materia(MateriaId.ALEMAN, "Alemán", "Vocabulario, estructuras y conversación.", emptyList()),
        Materia(MateriaId.FISICA, "Física", "Movimiento, energía y leyes de la naturaleza.", emptyList()),
        Materia(MateriaId.QUIMICA, "Química", "Materia, átomos y sus transformaciones.", emptyList()),
        Materia(MateriaId.BIOLOGIA, "Biología", "Vida, células, genética y ecosistemas.", emptyList()),
        Materia(MateriaId.HISTORIA, "Historia", "Procesos y acontecimientos que cambiaron al mundo.", emptyList()),
        Materia(MateriaId.GEOLOGIA, "Geología", "La Tierra, sus materiales y su evolución.", emptyList()),
        Materia(MateriaId.PSICOLOGIA, "Psicología", "Conducta, emociones y procesos mentales.", emptyList()),
        Materia(MateriaId.SOCIOLOGIA, "Sociología", "Sociedad, cultura y relaciones humanas.", emptyList()),
        Materia(MateriaId.TECNOLOGIA, "Tecnología", "Sistemas digitales, innovación y computación.", emptyList()),
        Materia(MateriaId.ESCRITURA, "Escritura", "Ideas, estructura y expresión escrita.", emptyList()),
        Materia(MateriaId.ANATOMIA, "Anatomía", "Estructuras y sistemas del cuerpo humano.", emptyList())
    )

    val disponibles: List<Materia> = todas.filter(Materia::disponible)

    fun buscar(id: MateriaId): Materia? = todas.find { it.id == id }
}
