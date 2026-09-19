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
class Migration1To2InstrumentedTest {

    private lateinit var context: Context
    private lateinit var helper: SupportSQLiteOpenHelper

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.deleteDatabase(TEST_DATABASE)

        val configuration = SupportSQLiteOpenHelper.Configuration.builder(context)
            .name(TEST_DATABASE)
            .callback(
                object : SupportSQLiteOpenHelper.Callback(1) {
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
                            """
                            INSERT INTO `usuario`
                                (`nombre`, `correo`, `password`, `fecha_registro`)
                            VALUES ('Ana', 'ana@dubalin.com', 'hash', 1)
                            """.trimIndent()
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
    fun migracion_1_2_preserva_datos_y_crea_el_esquema_esperado() {
        val db = helper.writableDatabase

        MIGRATION_1_2.migrate(db)

        assertEquals(1, rowCount(db, "usuario"))
        assertEquals(
            setOf("id", "usuario_id", "nombre"),
            columnNames(db, "seccion_apuntes")
        )
        assertEquals(
            setOf("id", "seccion_id", "titulo", "contenido", "fecha_actualizacion"),
            columnNames(db, "apunte")
        )
        assertTrue(indexExists(db, "index_seccion_apuntes_usuario_id"))
        assertTrue(indexExists(db, "index_apunte_seccion_id"))
        assertEquals(
            ForeignKeyInfo("usuario", "usuario_id", "id", "CASCADE"),
            foreignKeyInfo(db, "seccion_apuntes")
        )
        assertEquals(
            ForeignKeyInfo("seccion_apuntes", "seccion_id", "id", "CASCADE"),
            foreignKeyInfo(db, "apunte")
        )
    }

    private fun rowCount(db: SupportSQLiteDatabase, table: String): Int =
        db.query("SELECT COUNT(*) FROM `$table`").use { cursor ->
            cursor.moveToFirst()
            cursor.getInt(0)
        }

    private fun columnNames(
        db: SupportSQLiteDatabase,
        table: String
    ): Set<String> =
        db.query("PRAGMA table_info(`$table`)").use { cursor ->
            buildSet {
                val nameIndex = cursor.getColumnIndexOrThrow("name")
                while (cursor.moveToNext()) {
                    add(cursor.getString(nameIndex))
                }
            }
        }

    private fun indexExists(db: SupportSQLiteDatabase, index: String): Boolean {
        db.query(
            "SELECT 1 FROM sqlite_master WHERE type = 'index' AND name = ? LIMIT 1",
            arrayOf(index)
        ).use { cursor ->
            return cursor.moveToFirst()
        }
    }

    private fun foreignKeyInfo(
        db: SupportSQLiteDatabase,
        table: String
    ): ForeignKeyInfo? =
        db.query("PRAGMA foreign_key_list(`$table`)").use { cursor ->
            if (!cursor.moveToFirst()) return null
            ForeignKeyInfo(
                targetTable = cursor.getString(cursor.getColumnIndexOrThrow("table")),
                fromColumn = cursor.getString(cursor.getColumnIndexOrThrow("from")),
                toColumn = cursor.getString(cursor.getColumnIndexOrThrow("to")),
                onDelete = cursor.getString(cursor.getColumnIndexOrThrow("on_delete"))
            )
        }

    private data class ForeignKeyInfo(
        val targetTable: String,
        val fromColumn: String,
        val toColumn: String,
        val onDelete: String
    )

    private companion object {
        const val TEST_DATABASE = "migration-1-2-test.db"
    }
}
