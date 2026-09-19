package com.dubalin.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Flashcard individual dentro de un Mazo. Si se borra el mazo, se borran
 * en cascada sus flashcards.
 */
@Entity(
    tableName = "flashcard",
    foreignKeys = [
        ForeignKey(
            entity = MazoEntity::class,
            parentColumns = ["id"],
            childColumns = ["mazo_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["mazo_id"])]
)
data class FlashcardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "mazo_id")
    val mazoId: Int,

    @ColumnInfo(name = "frente")
    val frente: String,

    @ColumnInfo(name = "reverso")
    val reverso: String
)
