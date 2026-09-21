package com.dubalin.app.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dubalin.app.data.local.entity.ProgresoMateriaEntity
import com.dubalin.app.data.local.entity.UsuarioEntity
import com.dubalin.app.data.repository.NivelAstronomiaRepositoryImpl
import com.dubalin.app.domain.model.CatalogoAstronomia
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProgresoIntegracionTest {
    @Test fun rutaRealProtegeBloqueosYTerminaEnRangoDiez() = runBlocking {
        val db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Context>(), DubalinDatabase::class.java).build()
        try {
            db.usuarioDao().insert(UsuarioEntity(id = 1, nombre = "Prueba", correo = "prueba@example.com", password = "test", fechaRegistro = 0))
            db.progresoMateriaDao().insertIfAbsent(ProgresoMateriaEntity(usuarioId = 1, materiaId = "ASTRONOMIA", nivelActual = 1, nivelCeroCompletado = true))
            val repo = NivelAstronomiaRepositoryImpl(db, db.progresoNivelDao(), db.progresoMateriaDao())
            assertTrue(repo.completarTema(1, 2, 0).isFailure)
            assertTrue(repo.completarTema(1, 11, 0).isFailure)
            assertTrue(repo.completarTema(1, 1, 30).isFailure)
            for (nivel in 1..10) {
                assertTrue(repo.completarPractica(1, nivel).isFailure)
                CatalogoAstronomia.sesiones[nivel].indices.forEach { assertTrue(repo.completarTema(1, nivel, it).isSuccess) }
                assertTrue(repo.registrarEvaluacion(1, nivel, 10, 10).isFailure)
                assertTrue(repo.completarPractica(1, nivel).isSuccess)
                assertFalse(repo.registrarEvaluacion(1, nivel, 7, 10).getOrThrow())
                assertEquals(nivel, db.progresoMateriaDao().get(1, "ASTRONOMIA")!!.nivelActual)
                assertTrue(repo.registrarEvaluacion(1, nivel, 8, 10).getOrThrow())
            }
            assertEquals(11, db.progresoMateriaDao().get(1, "ASTRONOMIA")!!.nivelActual)
            assertTrue(db.progresoNivelDao().get(1, "ASTRONOMIA", 10)!!.nivelCompletado)
        } finally { db.close() }
    }
}
