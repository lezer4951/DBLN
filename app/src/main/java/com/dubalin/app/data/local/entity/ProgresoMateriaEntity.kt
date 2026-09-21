package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "progreso_materia",
    primaryKeys = ["usuario_id", "materia_id"],
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["usuario_id"])]
)
data class ProgresoMateriaEntity(
    @ColumnInfo(name = "usuario_id")
    val usuarioId: Int,

    @ColumnInfo(name = "materia_id")
    val materiaId: String,

    @ColumnInfo(name = "nivel_actual", defaultValue = "0")
    val nivelActual: Int = 0,

    @ColumnInfo(name = "pagina_actual", defaultValue = "0")
    val paginaActual: Int = 0,

    @ColumnInfo(name = "nivel_cero_completado", defaultValue = "0")
    val nivelCeroCompletado: Boolean = false,

    @ColumnInfo(name = "mejor_puntaje", defaultValue = "0")
    val mejorPuntaje: Int = 0,

    @ColumnInfo(name = "onboarding_completado", defaultValue = "0")
    val onboardingCompletado: Boolean = false,

    @ColumnInfo(name = "experiencia", defaultValue = "'PRIMERA_VEZ'")
    val experiencia: String = "PRIMERA_VEZ",

    @ColumnInfo(name = "minutos_diarios", defaultValue = "30")
    val minutosDiarios: Int = 30,

    @ColumnInfo(name = "formato_aprendizaje", defaultValue = "'EQUILIBRADO'")
    val formatoAprendizaje: String = "EQUILIBRADO",

    @ColumnInfo(name = "temas_completados", defaultValue = "0")
    val temasCompletados: Int = 0,

    @ColumnInfo(name = "fecha_actualizacion", defaultValue = "0")
    val fechaActualizacion: Long = 0L
)
