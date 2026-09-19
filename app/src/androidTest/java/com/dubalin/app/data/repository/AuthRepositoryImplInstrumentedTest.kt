package com.dubalin.app.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dubalin.app.core.util.PasswordHasher
import com.dubalin.app.data.local.DubalinDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthRepositoryImplInstrumentedTest {

    private lateinit var database: DubalinDatabase
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            DubalinDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = AuthRepositoryImpl(database.usuarioDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun registrar_guarda_el_usuario_y_la_contrasena_hasheada() = runTest {
        val result = repository.registrar(
            nombre = "Ana",
            correo = "ana@dubalin.com",
            password = "Dubalin123"
        )

        assertTrue(result.isSuccess)
        val usuario = result.getOrThrow()
        val entity = database.usuarioDao().getByCorreo("ana@dubalin.com")

        assertEquals("Ana", usuario.nombre)
        assertEquals("ana@dubalin.com", usuario.correo)
        assertTrue(entity != null)
        assertTrue(entity?.password != "Dubalin123")
        assertTrue(PasswordHasher.verify("Dubalin123", requireNotNull(entity).password))
    }

    @Test
    fun registrar_rechaza_un_correo_duplicado() = runTest {
        repository.registrar("Ana", "ana@dubalin.com", "Dubalin123")

        val duplicate = repository.registrar(
            nombre = "Otra Ana",
            correo = "ana@dubalin.com",
            password = "OtraClave123"
        )

        assertTrue(duplicate.isFailure)
        assertEquals(
            "Ya existe una cuenta con ese correo.",
            duplicate.exceptionOrNull()?.message
        )
    }

    @Test
    fun login_acepta_la_clave_correcta_y_rechaza_la_incorrecta() = runTest {
        repository.registrar("Ana", "ana@dubalin.com", "Dubalin123")

        val correct = repository.login("ana@dubalin.com", "Dubalin123")
        val incorrect = repository.login("ana@dubalin.com", "clave-incorrecta")

        assertTrue(correct.isSuccess)
        assertEquals("Ana", correct.getOrThrow().nombre)
        assertTrue(incorrect.isFailure)
        assertEquals("Contraseña incorrecta.", incorrect.exceptionOrNull()?.message)
    }
}
