package com.dubalin.app.domain.model

data class ProgresoMateria(
    val materiaId: MateriaId,
    val nivelActual: Int = 0,
    val paginaActual: Int = 0,
    val nivelCeroCompletado: Boolean = false,
    val mejorPuntaje: Int = 0,
    val onboardingCompletado: Boolean = false,
    val experiencia: ExperienciaAstronomia = ExperienciaAstronomia.PRIMERA_VEZ,
    val minutosDiarios: Int = 30,
    val formatoAprendizaje: FormatoAprendizaje = FormatoAprendizaje.EQUILIBRADO,
    val temasCompletados: Int = 0
)
