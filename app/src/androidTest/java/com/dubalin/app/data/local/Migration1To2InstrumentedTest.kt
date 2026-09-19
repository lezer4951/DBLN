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
    fun migracion_1_2_crea_tablas_indices_y_claves_foraneas() {
        val db = helper.writableDatabase

        MIGRATION_1_2.migrate(db)

        assertTrue(tableExists(db, "seccion_apuntes"))
        assertTrue(tableExists(db, "apunte"))
        assertTrue(indexExists(db, "index_seccion_apuntes_usuario_id"))
        assertTrue(indexExists(db, "index_apunte_seccion_id"))
        assertEquals("usuario", foreignKeyTarget(db, "seccion_apuntes"))
        assertEquals("seccion_apuntes", foreignKeyTarget(db, "apunte"))
    }

    private fun tableExists(db: SupportSQLiteDatabase, table: String): Boolean =
        objectExists(db, "table", table)

    private fun indexExists(db: SupportSQLiteDatabase, index: String): Boolean =
        objectExists(db, "index", index)

    private fun objectExists(
        db: SupportSQLiteDatabase,
        type: String,
        name: String
    ): Boolean {
        db.query(
            "SELECT 1 FROM sqlite_master WHERE type = ? AND name = ? LIMIT 1",
            arrayOf(type, name)
        ).use { cursor ->
            return cursor.moveToFirst()
        }
    }

    private fun foreignKeyTarget(
        db: SupportSQLiteDatabase,
        table: String
    ): String? {
        db.query("PRAGMA foreign_key_list(`$table`)").use { cursor ->
            if (!cursor.moveToFirst()) return null
            return cursor.getString(cursor.getColumnIndexOrThrow("table"))
        }
    }

    private companion object {
        const val TEST_DATABASE = "migration-1-2-test.db"
    }
}
