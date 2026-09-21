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
class Migration2To3InstrumentedTest {

    private lateinit var context: Context
    private lateinit var helper: SupportSQLiteOpenHelper

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.deleteDatabase(TEST_DATABASE)
        val configuration = SupportSQLiteOpenHelper.Configuration.builder(context)
            .name(TEST_DATABASE)
            .callback(
                object : SupportSQLiteOpenHelper.Callback(2) {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        db.execSQL(
                            """
                            CREATE TABLE IF NOT EXISTS `usuario` (
                                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                                `nombre` TEXT NOT NULL,
                                `correo` TEXT NOT NULL,
                                `password` TEXT NOT NULL,
                                `fecha_registro` INTEGER NOT NULL,
                                `racha_dias` INTEGER NOT NULL DEFAULT 0
                            )
                            """.trimIndent()
                        )
                        db.execSQL(
                            "INSERT INTO `usuario` (`id`, `nombre`, `correo`, `password`, `fecha_registro`) " +
                                "VALUES (1, 'Ana', 'ana@dubalin.com', 'hash', 1)"
                        )
                    }

                    override fun onUpgrade(
                        db: SupportSQLiteDatabase,
                        oldVersion: Int,
                        newVersion: Int
                    ) = Unit
                }
            )
            .build()
        helper = FrameworkSQLiteOpenHelperFactory().create(configuration)
    }

    @After
    fun tearDown() {
        helper.close()
        context.deleteDatabase(TEST_DATABASE)
    }

    @Test
    fun migracion_2_3_crea_progreso_y_preserva_usuarios() {
        val db = helper.writableDatabase
        MIGRATION_2_3.migrate(db)

        assertEquals(1, rowCount(db, "usuario"))
        assertEquals(
            setOf(
                "usuario_id",
                "materia_id",
                "nivel_actual",
                "pagina_actual",
                "nivel_cero_completado",
                "mejor_puntaje",
                "fecha_actualizacion"
            ),
            columnNames(db, "progreso_materia")
        )
        assertTrue(indexExists(db, "index_progreso_materia_usuario_id"))
        assertEquals(
            ForeignKeyInfo("usuario", "usuario_id", "id", "CASCADE"),
            foreignKeyInfo(db, "progreso_materia")
        )
    }

    private fun rowCount(db: SupportSQLiteDatabase, table: String): Int =
        db.query("SELECT COUNT(*) FROM `$table`").use { cursor ->
            cursor.moveToFirst()
            cursor.getInt(0)
        }

    private fun columnNames(db: SupportSQLiteDatabase, table: String): Set<String> =
        db.query("PRAGMA table_info(`$table`)").use { cursor ->
            buildSet {
                val nameIndex = cursor.getColumnIndexOrThrow("name")
                while (cursor.moveToNext()) add(cursor.getString(nameIndex))
            }
        }

    private fun indexExists(db: SupportSQLiteDatabase, index: String): Boolean =
        db.query(
            "SELECT 1 FROM sqlite_master WHERE type = 'index' AND name = ? LIMIT 1",
            arrayOf(index)
        ).use { it.moveToFirst() }

    private fun foreignKeyInfo(
        db: SupportSQLiteDatabase,
        table: String
    ): ForeignKeyInfo? {
        return db.query("PRAGMA foreign_key_list(`$table`)").use { cursor ->
            if (!cursor.moveToFirst()) return null
            ForeignKeyInfo(
                targetTable = cursor.getString(cursor.getColumnIndexOrThrow("table")),
                fromColumn = cursor.getString(cursor.getColumnIndexOrThrow("from")),
                toColumn = cursor.getString(cursor.getColumnIndexOrThrow("to")),
                onDelete = cursor.getString(cursor.getColumnIndexOrThrow("on_delete"))
            )
        }
    }

    private data class ForeignKeyInfo(
        val targetTable: String,
        val fromColumn: String,
        val toColumn: String,
        val onDelete: String
    )

    private companion object {
        const val TEST_DATABASE = "migration-2-3-test.db"
    }
}
