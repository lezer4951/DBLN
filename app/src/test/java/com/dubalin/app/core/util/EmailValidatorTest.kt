package com.dubalin.app.core.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailValidatorTest {

    @Test
    fun acepta_correos_validos() {
        assertTrue(EmailValidator.isValid("ana@dubalin.com"))
        assertTrue(EmailValidator.isValid(" ANA+estudio@Dubalin.COM "))
    }

    @Test
    fun rechaza_correos_invalidos() {
        assertFalse(EmailValidator.isValid(""))
        assertFalse(EmailValidator.isValid("ana"))
        assertFalse(EmailValidator.isValid("ana@dubalin"))
        assertFalse(EmailValidator.isValid("@dubalin.com"))
    }
}
