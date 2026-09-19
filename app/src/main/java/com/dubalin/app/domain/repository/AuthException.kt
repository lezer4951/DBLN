package com.dubalin.app.domain.repository

sealed class AuthException(message: String) : Exception(message)

class EmailAlreadyRegisteredException : AuthException(
    "Ya existe una cuenta con ese correo."
)

class UserNotFoundException : AuthException(
    "No existe una cuenta con ese correo."
)

class InvalidCredentialsException : AuthException(
    "Contraseña incorrecta."
)
