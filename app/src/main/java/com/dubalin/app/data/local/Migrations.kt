package com.dubalin.app.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Agrega las tablas de Mis Apuntes (Módulo 4): seccion_apuntes y apunte.
 * No estaban en el esquema original del Módulo 1 -- el usuario pidió
 * organizar apuntes por secciones (ej. "Inglés") ya en desarrollo.
 *
 * Migración real, no destructiva: preserva usuarios, mazos, flashcards
 * y todo lo demás ya guardado en dispositivos con la versión 1.
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `seccion_apuntes` (
                `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                `usuario_id` INTEGER NOT NULL,
                `nombre` TEXT NOT NULL,
                FOREIGN KEY(`usuario_id`) REFERENCES `usuario`(`id`) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_seccion_apuntes_usuario_id` " +
                "ON `seccion_apuntes` (`usuario_id`)"
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `apunte` (
                `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                `seccion_id` INTEGER NOT NULL,
                `titulo` TEXT NOT NULL,
                `contenido` TEXT NOT NULL,
                `fecha_actualizacion` INTEGER NOT NULL,
                FOREIGN KEY(`seccion_id`) REFERENCES `seccion_apuntes`(`id`) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_apunte_seccion_id` " +
                "ON `apunte` (`seccion_id`)"
        )
    }
}
