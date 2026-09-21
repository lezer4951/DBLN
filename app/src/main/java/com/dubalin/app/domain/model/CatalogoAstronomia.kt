package com.dubalin.app.domain.model

import kotlin.random.Random

object CatalogoAstronomia {
    val sesiones by lazy { listOf(
        NivelCeroAstronomiaContenido.sesiones, NivelUnoAstronomiaContenido.sesiones,
        NivelDosAstronomiaContenido.sesiones, NivelTresAstronomiaContenido.sesiones,
        NivelCuatroAstronomiaContenido.sesiones, NivelCincoAstronomiaContenido.sesiones,
        NivelSeisAstronomiaContenido.sesiones, NivelSieteAstronomiaContenido.sesiones,
        NivelOchoAstronomiaContenido.sesiones, NivelNueveAstronomiaContenido.sesiones,
        NivelDiezAstronomiaContenido.sesiones
    ) }
    val examenes by lazy { listOf(
        NivelCeroAstronomiaContenido.preguntas, NivelUnoAstronomiaContenido.examen,
        NivelDosAstronomiaContenido.examen, NivelTresAstronomiaContenido.examen,
        NivelCuatroAstronomiaContenido.examen, NivelCincoAstronomiaContenido.examen,
        NivelSeisAstronomiaContenido.examen, NivelSieteAstronomiaContenido.examen,
        NivelOchoAstronomiaContenido.examen, NivelNueveAstronomiaContenido.examen,
        NivelDiezAstronomiaContenido.examen
    ) }
    fun mascara(nivel: Int): Int {
        require(nivel in 0..10)
        return (1 shl sesiones[nivel].size) - 1
    }
    fun porcentaje(nivelDisponible: Int) = nivelDisponible.coerceIn(0, 11) * 100 / 11

    // Mantiene cada tema asociado a su lección para el repaso del examen.
    fun practica(nivel: Int): List<PreguntaAstronomia> {
        require(nivel in 1..10)
        val propias = listOf(
            NivelUnoAstronomiaContenido.practica, NivelDosAstronomiaContenido.practica,
            NivelTresAstronomiaContenido.practica, NivelCuatroAstronomiaContenido.practica,
            NivelCincoAstronomiaContenido.practica, NivelSeisAstronomiaContenido.practica,
            NivelSieteAstronomiaContenido.practica, NivelOchoAstronomiaContenido.practica,
            NivelNueveAstronomiaContenido.practica, NivelDiezAstronomiaContenido.practica
        )[nivel - 1]
        val niveles = if (nivel == 10) 0..10 else (nivel - 2).coerceAtLeast(0)..nivel
        return niveles.flatMap { origen -> (if (origen == nivel) propias else examenes[origen].take(2)).map {
            it.copy(enunciado = "Repaso del nivel $origen · ${it.enunciado}")
        } }
    }
}

fun PreguntaAstronomia.mezclar(random: Random): PreguntaAstronomia {
    val orden = opciones.indices.shuffled(random)
    return copy(opciones = orden.map(opciones::get), respuestaCorrecta = orden.indexOf(respuestaCorrecta))
}

fun SesionAstronomia.mezclarAutoevaluacion(): SesionAstronomia = copy(
    autoevaluacion = autoevaluacion.map { q ->
        val orden = q.opciones.indices.shuffled(Random(q.enunciado.hashCode()))
        q.copy(opciones = orden.map(q.opciones::get), respuestaCorrecta = orden.indexOf(q.respuestaCorrecta))
    }
)
