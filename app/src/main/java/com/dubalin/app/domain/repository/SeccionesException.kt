package com.dubalin.app.domain.repository

sealed class SeccionesException(message: String) : Exception(message)

class DuplicateSectionNameException : SeccionesException(
    "Ya existe una sección con ese nombre."
)

class SectionNotFoundException : SeccionesException(
    "La sección ya no existe."
)
