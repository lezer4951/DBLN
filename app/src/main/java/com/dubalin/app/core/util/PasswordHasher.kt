package com.dubalin.app.core.util

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Hashing de contraseñas para el login local (sin backend).
 * Usa PBKDF2WithHmacSHA1 con salt aleatorio por usuario -- no requiere
 * ninguna librería externa, solo APIs incluidas en Android.
 *
 * El resultado de [hash] se guarda completo en el único campo `password`
 * de UsuarioEntity, con el formato "saltBase64:hashBase64", así no hace
 * falta una columna extra para el salt.
 */
object PasswordHasher {

    private const val ITERATIONS = 10_000
    private const val KEY_LENGTH = 256
    private const val ALGORITHM = "PBKDF2WithHmacSHA1"
    private const val SALT_BYTES = 16

    fun hash(password: String): String {
        val salt = ByteArray(SALT_BYTES).also { SecureRandom().nextBytes(it) }
        val hashBytes = pbkdf2(password, salt)
        return "${encode(salt)}:${encode(hashBytes)}"
    }

    fun verify(password: String, stored: String): Boolean {
        val parts = stored.split(":")
        if (parts.size != 2) return false

        val salt = decode(parts[0])
        val expectedHash = decode(parts[1])
        val actualHash = pbkdf2(password, salt)

        return actualHash.contentEquals(expectedHash)
    }

    private fun pbkdf2(password: String, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(ALGORITHM)
        return factory.generateSecret(spec).encoded
    }

    private fun encode(bytes: ByteArray): String =
        Base64.encodeToString(bytes, Base64.NO_WRAP)

    private fun decode(value: String): ByteArray =
        Base64.decode(value, Base64.NO_WRAP)
}
