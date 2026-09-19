package com.dubalin.app.domain.repository

sealed class ApunteException(message: String) : Exception(message)

class NoteNotFoundException : ApunteException(
    "El apunte ya no existe."
)

class InvalidNoteException : ApunteException(
    "El título y el contenido son obligatorios."
)
