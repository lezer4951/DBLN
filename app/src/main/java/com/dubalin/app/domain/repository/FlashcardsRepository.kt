package com.dubalin.app.domain.repository

import com.dubalin.app.domain.model.Mazo
import com.dubalin.app.domain.model.Flashcard
import kotlinx.coroutines.flow.Flow

interface FlashcardsRepository {
    fun mazos(usuario: Int): Flow<List<Mazo>>
    fun tarjetas(mazo: Int): Flow<List<Flashcard>>
    suspend fun guardarMazo(usuario: Int, id: Int, titulo: String, descripcion: String)
    suspend fun borrarMazo(usuario: Int, id: Int)
    suspend fun guardarTarjeta(usuario: Int, mazo: Int, id: Int, frente: String, reverso: String)
    suspend fun borrarTarjeta(usuario: Int, tarjeta: Flashcard)
}
