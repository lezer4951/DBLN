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

/** Agrega progreso por usuario y materia sin modificar los datos existentes. */
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `progreso_materia` (
                `usuario_id` INTEGER NOT NULL,
                `materia_id` TEXT NOT NULL,
                `nivel_actual` INTEGER NOT NULL DEFAULT 0,
                `pagina_actual` INTEGER NOT NULL DEFAULT 0,
                `nivel_cero_completado` INTEGER NOT NULL DEFAULT 0,
                `mejor_puntaje` INTEGER NOT NULL DEFAULT 0,
                `fecha_actualizacion` INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY(`usuario_id`, `materia_id`),
                FOREIGN KEY(`usuario_id`) REFERENCES `usuario`(`id`) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_progreso_materia_usuario_id` " +
                "ON `progreso_materia` (`usuario_id`)"
        )
    }
}

/** Amplía el progreso con el plan de Dr. Aster y dominio de los diez temas. */
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE `progreso_materia` ADD COLUMN `onboarding_completado` INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE `progreso_materia` ADD COLUMN `experiencia` TEXT NOT NULL DEFAULT 'PRIMERA_VEZ'")
        db.execSQL("ALTER TABLE `progreso_materia` ADD COLUMN `minutos_diarios` INTEGER NOT NULL DEFAULT 30")
        db.execSQL("ALTER TABLE `progreso_materia` ADD COLUMN `formato_aprendizaje` TEXT NOT NULL DEFAULT 'EQUILIBRADO'")
        db.execSQL("ALTER TABLE `progreso_materia` ADD COLUMN `temas_completados` INTEGER NOT NULL DEFAULT 0")
        db.execSQL(
            "UPDATE `progreso_materia` SET `temas_completados` = 1023 " +
                "WHERE `nivel_cero_completado` = 1"
        )
        db.execSQL("UPDATE `progreso_materia` SET `onboarding_completado` = 1 WHERE `nivel_cero_completado` = 1")
    }
}

/** Crea progreso por nivel y traslada el avance histórico del Nivel 0. */
val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `progreso_nivel` (
                `usuario_id` INTEGER NOT NULL,
                `materia_id` TEXT NOT NULL,
                `nivel` INTEGER NOT NULL,
                `temas_completados` INTEGER NOT NULL DEFAULT 0,
                `practica_completada` INTEGER NOT NULL DEFAULT 0,
                `nivel_completado` INTEGER NOT NULL DEFAULT 0,
                `mejor_puntaje` INTEGER NOT NULL DEFAULT 0,
                `fecha_actualizacion` INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY(`usuario_id`, `materia_id`, `nivel`),
                FOREIGN KEY(`usuario_id`) REFERENCES `usuario`(`id`) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_progreso_nivel_usuario_id` ON `progreso_nivel` (`usuario_id`)")
        db.execSQL(
            """
            INSERT OR IGNORE INTO `progreso_nivel`
                (`usuario_id`, `materia_id`, `nivel`, `temas_completados`, `practica_completada`, `nivel_completado`, `mejor_puntaje`, `fecha_actualizacion`)
            SELECT `usuario_id`, `materia_id`, 0, `temas_completados`, `nivel_cero_completado`,
                `nivel_cero_completado`, `mejor_puntaje`, `fecha_actualizacion`
            FROM `progreso_materia`
            """.trimIndent()
        )
    }
}
