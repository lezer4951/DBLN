package com.dubalin.app.core.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PasswordHasherInstrumentedTest {

    @Test
    fun hash_y_verify_aceptan_la_contrasena_correcta() {
        val password = "Dubalin123"
        val stored = PasswordHasher.hash(password)

        assertTrue(PasswordHasher.verify(password, stored))
        assertFalse(PasswordHasher.verify("otra-contrasena", stored))
    }

    @Test
    fun hash_genera_un_salt_diferente_en_cada_llamada() {
        val first = PasswordHasher.hash("Dubalin123")
        val second = PasswordHasher.hash("Dubalin123")

        assertNotEquals(first, second)
        assertTrue(PasswordHasher.verify("Dubalin123", first))
        assertTrue(PasswordHasher.verify("Dubalin123", second))
    }

    @Test
    fun verify_rechaza_un_hash_con_formato_invalido() {
        assertFalse(PasswordHasher.verify("Dubalin123", "hash-invalido"))
    }
}
