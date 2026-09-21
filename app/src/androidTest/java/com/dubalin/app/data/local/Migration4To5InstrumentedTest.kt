package com.dubalin.app.data.local

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Migration4To5InstrumentedTest {
    private lateinit var context: Context
    private lateinit var helper: SupportSQLiteOpenHelper

    @Before fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.deleteDatabase(DB)
        helper = FrameworkSQLiteOpenHelperFactory().create(
            SupportSQLiteOpenHelper.Configuration.builder(context).name(DB)
                .callback(object : SupportSQLiteOpenHelper.Callback(4) {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        db.execSQL("CREATE TABLE usuario (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)")
                        db.execSQL("INSERT INTO usuario (id, nombre) VALUES (1, 'Ada')")
                        db.execSQL("""
                            CREATE TABLE progreso_materia (
                                usuario_id INTEGER NOT NULL, materia_id TEXT NOT NULL,
                                nivel_actual INTEGER NOT NULL DEFAULT 0, pagina_actual INTEGER NOT NULL DEFAULT 0,
                                nivel_cero_completado INTEGER NOT NULL DEFAULT 0, mejor_puntaje INTEGER NOT NULL DEFAULT 0,
                                onboarding_completado INTEGER NOT NULL DEFAULT 0, experiencia TEXT NOT NULL DEFAULT 'PRIMERA_VEZ',
                                minutos_diarios INTEGER NOT NULL DEFAULT 30, formato_aprendizaje TEXT NOT NULL DEFAULT 'EQUILIBRADO',
                                temas_completados INTEGER NOT NULL DEFAULT 0, fecha_actualizacion INTEGER NOT NULL DEFAULT 0,
                                PRIMARY KEY(usuario_id, materia_id)
                            )
                        """.trimIndent())
                        db.execSQL("INSERT INTO progreso_materia VALUES (1, 'ASTRONOMIA', 1, 9, 1, 90, 1, 'PRIMERA_VEZ', 30, 'EQUILIBRADO', 1023, 50)")
                    }
                    override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) = Unit
                }).build()
        )
    }

    @After fun tearDown() { helper.close(); context.deleteDatabase(DB) }

    @Test fun migracion_4_5_normaliza_y_conserva_nivel_cero() {
        val db = helper.writableDatabase
        MIGRATION_4_5.migrate(db)
        db.query("SELECT nivel, temas_completados, practica_completada, nivel_completado, mejor_puntaje FROM progreso_nivel").use {
            assertTrue(it.moveToFirst())
            assertEquals(0, it.getInt(0)); assertEquals(1023, it.getInt(1))
            assertEquals(1, it.getInt(2)); assertEquals(1, it.getInt(3)); assertEquals(90, it.getInt(4))
        }
        assertTrue(db.query("SELECT 1 FROM sqlite_master WHERE type='index' AND name='index_progreso_nivel_usuario_id'").use { it.moveToFirst() })
    }
    private companion object { const val DB = "migration-4-5-test.db" }
}
