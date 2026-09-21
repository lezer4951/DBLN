package com.dubalin.app.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dubalin.app.data.local.entity.UsuarioEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/** Valida el resultado de las migraciones contra el esquema generado por Room. */
@RunWith(AndroidJUnit4::class)
class MigracionCompletaTest {
    @Test fun cadenaUnoACincoPreservaUsuarioYValidaEsquemaFinal() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val nombre = "dubalin-migracion-cadena-test.db"
        context.deleteDatabase(nombre)
        try {
            val inicial = Room.databaseBuilder(context, DubalinDatabase::class.java, nombre).build()
            try {
                inicial.usuarioDao().insert(UsuarioEntity(id = 1, nombre = "Ada", correo = "ada@example.com", password = "test", fechaRegistro = 100))
            } finally { inicial.close() }
            // Recrea la base previa a las cuatro migraciones, conservando las tablas originales.
            SQLiteDatabase.openDatabase(context.getDatabasePath(nombre).path, null, SQLiteDatabase.OPEN_READWRITE).use { db ->
                db.execSQL("DROP TABLE progreso_nivel")
                db.execSQL("DROP TABLE progreso_materia")
                db.execSQL("DROP TABLE apunte")
                db.execSQL("DROP TABLE seccion_apuntes")
                db.execSQL("DROP TABLE room_master_table")
                db.version = 1
            }
            val migrada = Room.databaseBuilder(context, DubalinDatabase::class.java, nombre)
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5).build()
            try {
                assertEquals("Ada", migrada.usuarioDao().getByCorreo("ada@example.com")!!.nombre)
                // Abrir mediante Room ejecuta también su validación de columnas, índices y claves.
                assertEquals(5, migrada.openHelper.writableDatabase.version)
            } finally { migrada.close() }
        } finally { context.deleteDatabase(nombre) }
    }
}
