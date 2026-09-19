package com.dubalin.app.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dubalin.app.data.local.DubalinDatabase
import com.dubalin.app.data.local.entity.UsuarioEntity
import com.dubalin.app.domain.repository.DuplicateSectionNameException
import com.dubalin.app.domain.repository.InvalidNoteException
import com.dubalin.app.domain.repository.NoteNotFoundException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApuntesRepositoryImplInstrumentedTest {

    private lateinit var database: DubalinDatabase
    private lateinit var repository: ApuntesRepositoryImpl
    private var usuarioId: Int = 0

    @Before
    fun setUp() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            DubalinDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = ApuntesRepositoryImpl(
            seccionApuntesDao = database.seccionApuntesDao(),
            apunteDao = database.apunteDao()
        )

        usuarioId = database.usuarioDao().insert(
            UsuarioEntity(
                nombre = "Ana",
                correo = "ana@dubalin.com",
                password = "hash-de-prueba",
                fechaRegistro = 1L
            )
        ).toInt()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun crear_normaliza_el_nombre_y_rechaza_duplicados() = runTest {
        val first = repository.crearSeccion(usuarioId, "  Inglés   básico  ")
        val duplicate = repository.crearSeccion(usuarioId, "inglés básico")

        val secciones = repository.observarSecciones(usuarioId).first()

        assertTrue(first.isSuccess)
        assertEquals("Inglés básico", secciones.single().nombre)
        assertTrue(duplicate.exceptionOrNull() is DuplicateSectionNameException)
    }

    @Test
    fun actualizar_cambia_el_nombre_y_valida_duplicados() = runTest {
        repository.crearSeccion(usuarioId, "Inglés")
        repository.crearSeccion(usuarioId, "Historia")
        val secciones = repository.observarSecciones(usuarioId).first()
        val ingles = secciones.first { it.nombre == "Inglés" }

        val updated = repository.actualizarSeccion(
            seccionId = ingles.id,
            usuarioId = usuarioId,
            nombre = "Idiomas"
        )
        val duplicate = repository.actualizarSeccion(
            seccionId = ingles.id,
            usuarioId = usuarioId,
            nombre = "historia"
        )

        assertTrue(updated.isSuccess)
        assertTrue(duplicate.exceptionOrNull() is DuplicateSectionNameException)
        assertEquals(
            listOf("Historia", "Idiomas"),
            repository.observarSecciones(usuarioId).first().map { it.nombre }
        )
    }

    @Test
    fun guardar_y_editar_apunte_persiste_los_cambios() = runTest {
        repository.crearSeccion(usuarioId, "Programación")
        val seccion = repository.observarSecciones(usuarioId).first().single()

        val created = repository.guardarApunte(
            seccionId = seccion.id,
            apunteId = null,
            titulo = "  Kotlin   Coroutines ",
            contenido = "  Contenido inicial  "
        )
        val apunteCreado = repository.observarApuntes(seccion.id).first().single()

        val updated = repository.guardarApunte(
            seccionId = seccion.id,
            apunteId = apunteCreado.id,
            titulo = "StateFlow",
            contenido = "Contenido actualizado"
        )
        val apunteActualizado = repository.obtenerApunte(apunteCreado.id).getOrThrow()

        assertTrue(created.isSuccess)
        assertTrue(updated.isSuccess)
        assertEquals("Kotlin Coroutines", apunteCreado.titulo)
        assertEquals("Contenido inicial", apunteCreado.contenido)
        assertEquals("StateFlow", apunteActualizado.titulo)
        assertEquals("Contenido actualizado", apunteActualizado.contenido)
    }

    @Test
    fun guardar_rechaza_un_apunte_incompleto() = runTest {
        repository.crearSeccion(usuarioId, "Programación")
        val seccion = repository.observarSecciones(usuarioId).first().single()

        val result = repository.guardarApunte(
            seccionId = seccion.id,
            apunteId = null,
            titulo = " ",
            contenido = "Contenido"
        )

        assertTrue(result.exceptionOrNull() is InvalidNoteException)
        assertTrue(repository.observarApuntes(seccion.id).first().isEmpty())
    }

    @Test
    fun eliminar_apunte_impide_eliminarlo_dos_veces() = runTest {
        repository.crearSeccion(usuarioId, "Programación")
        val seccion = repository.observarSecciones(usuarioId).first().single()
        repository.guardarApunte(
            seccionId = seccion.id,
            apunteId = null,
            titulo = "Coroutines",
            contenido = "Notas"
        )
        val apunte = repository.observarApuntes(seccion.id).first().single()

        val firstDelete = repository.eliminarApunte(apunte)
        val secondDelete = repository.eliminarApunte(apunte)

        assertTrue(firstDelete.isSuccess)
        assertTrue(secondDelete.exceptionOrNull() is NoteNotFoundException)
        assertTrue(repository.observarApuntes(seccion.id).first().isEmpty())
    }

    @Test
    fun eliminar_seccion_elimina_sus_apuntes_en_cascada() = runTest {
        repository.crearSeccion(usuarioId, "Programación")
        val seccion = repository.observarSecciones(usuarioId).first().single()
        repository.guardarApunte(
            seccionId = seccion.id,
            apunteId = null,
            titulo = "Coroutines",
            contenido = "Notas"
        )

        val result = repository.eliminarSeccion(seccion)

        assertTrue(result.isSuccess)
        assertTrue(repository.observarSecciones(usuarioId).first().isEmpty())
        assertTrue(repository.observarApuntes(seccion.id).first().isEmpty())
    }
}
