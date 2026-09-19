package com.dubalin.app.core.util

object EmailValidator {

    private val emailRegex = Regex(
        pattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
        option = RegexOption.IGNORE_CASE
    )

    fun isValid(email: String): Boolean =
        emailRegex.matches(email.trim())
}
