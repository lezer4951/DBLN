package com.dubalin.app.data.local

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Migration3To4InstrumentedTest {
    private lateinit var context: Context
    private lateinit var helper: SupportSQLiteOpenHelper

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.deleteDatabase(TEST_DATABASE)
        helper = FrameworkSQLiteOpenHelperFactory().create(
            SupportSQLiteOpenHelper.Configuration.builder(context)
                .name(TEST_DATABASE)
                .callback(object : SupportSQLiteOpenHelper.Callback(3) {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        db.execSQL(
                            """
                            CREATE TABLE `progreso_materia` (
                                `usuario_id` INTEGER NOT NULL,
                                `materia_id` TEXT NOT NULL,
                                `nivel_actual` INTEGER NOT NULL DEFAULT 0,
                                `pagina_actual` INTEGER NOT NULL DEFAULT 0,
                                `nivel_cero_completado` INTEGER NOT NULL DEFAULT 0,
                                `mejor_puntaje` INTEGER NOT NULL DEFAULT 0,
                                `fecha_actualizacion` INTEGER NOT NULL DEFAULT 0,
                                PRIMARY KEY(`usuario_id`, `materia_id`)
                            )
                            """.trimIndent()
                        )
                        db.execSQL(
                            "INSERT INTO progreso_materia VALUES " +
                                "(1, 'ASTRONOMIA', 1, 4, 1, 80, 1000), " +
                                "(2, 'ASTRONOMIA', 0, 2, 0, 40, 1000)"
                        )
                    }

                    override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) = Unit
                }).build()
        )
    }

    @After
    fun tearDown() {
        helper.close()
        context.deleteDatabase(TEST_DATABASE)
    }

    @Test
    fun migracion_3_4_agrega_plan_y_conserva_aprobados() {
        val db = helper.writableDatabase
        MIGRATION_3_4.migrate(db)

        db.query(
            "SELECT onboarding_completado, experiencia, minutos_diarios, " +
                "formato_aprendizaje, temas_completados FROM progreso_materia ORDER BY usuario_id"
        ).use { cursor ->
            cursor.moveToFirst()
            assertEquals(1, cursor.getInt(0))
            assertEquals("PRIMERA_VEZ", cursor.getString(1))
            assertEquals(30, cursor.getInt(2))
            assertEquals("EQUILIBRADO", cursor.getString(3))
            assertEquals(1023, cursor.getInt(4))
            cursor.moveToNext()
            assertEquals(0, cursor.getInt(0))
            assertEquals(0, cursor.getInt(4))
        }
    }

    private companion object { const val TEST_DATABASE = "migration-3-4-test.db" }
}
