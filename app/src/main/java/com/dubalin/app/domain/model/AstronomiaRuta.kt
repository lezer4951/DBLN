package com.dubalin.app.domain.model

data class NivelAstronomia(
    val numero: Int,
    val titulo: String,
    val descripcion: String,
    val bloqueado: Boolean
)

/** Ruta académica inicial. El progreso persistente reemplazará el estado
 * bloqueado fijo cuando se implemente el motor de niveles. */
object AstronomiaRuta {
    val niveles: List<NivelAstronomia> = listOf(
        NivelAstronomia(0, "Primer vistazo al universo", "Qué estudia la astronomía y cómo observamos el cielo.", false),
        NivelAstronomia(1, "El Sistema Solar", "El Sol, los planetas y los cuerpos menores.", true),
        NivelAstronomia(2, "La Tierra y la Luna", "Movimientos, fases, eclipses y mareas.", true),
        NivelAstronomia(3, "El Sol y las estrellas", "Luz, energía y propiedades estelares.", true),
        NivelAstronomia(4, "Planetas y exoplanetas", "Mundos dentro y fuera del Sistema Solar.", true),
        NivelAstronomia(5, "Galaxias", "Estructura, tipos y evolución de las galaxias.", true),
        NivelAstronomia(6, "Nebulosas y evolución estelar", "Nacimiento, vida y muerte de las estrellas.", true),
        NivelAstronomia(7, "Agujeros negros", "Gravedad extrema y horizontes de sucesos.", true),
        NivelAstronomia(8, "Cosmología", "Origen, expansión y estructura del universo.", true),
        NivelAstronomia(9, "Exploración espacial", "Misiones, telescopios y tecnología espacial.", true),
        NivelAstronomia(10, "Astronomía avanzada", "Integra lo aprendido y analiza fenómenos complejos.", true)
    )
}
