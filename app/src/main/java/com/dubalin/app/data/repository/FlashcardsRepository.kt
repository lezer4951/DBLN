package com.dubalin.app.data.repository

import androidx.room.withTransaction
import com.dubalin.app.data.local.DubalinDatabase
import com.dubalin.app.data.local.entity.MazoEntity
import com.dubalin.app.data.local.entity.FlashcardEntity
import com.dubalin.app.domain.model.Mazo
import com.dubalin.app.domain.model.Flashcard
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FlashcardsRepository @Inject constructor(private val db: DubalinDatabase) : com.dubalin.app.domain.repository.FlashcardsRepository {
    override fun mazos(usuario: Int) = db.mazoDao().observeByUsuarioId(usuario).map { rows ->
        rows.map { Mazo(it.id, it.titulo, it.descripcion) }
    }

    override fun tarjetas(mazo: Int) = db.flashcardDao().observeByMazoId(mazo).map { rows ->
        rows.map { Flashcard(it.id, it.mazoId, it.frente, it.reverso) }
    }

    private suspend fun verificar(usuario: Int, mazo: Int) {
        require(db.mazoDao().getById(mazo)?.usuarioId == usuario) { "El mazo ya no está disponible." }
    }

    override suspend fun guardarMazo(usuario: Int, id: Int, titulo: String, descripcion: String) = db.withTransaction {
        require(titulo.isNotBlank()) { "Escribe el nombre del mazo." }
        if (id != 0) verificar(usuario, id)
        val entity = MazoEntity(id, usuario, titulo.trim(), descripcion.trim())
        if (id == 0) db.mazoDao().insert(entity) else db.mazoDao().update(entity)
        Unit
    }

    override suspend fun borrarMazo(usuario: Int, id: Int): Unit = db.withTransaction {
        verificar(usuario, id)
        db.mazoDao().getById(id)?.let { db.mazoDao().delete(it) }
        Unit
    }

    override suspend fun guardarTarjeta(usuario: Int, mazo: Int, id: Int, frente: String, reverso: String) = db.withTransaction {
        verificar(usuario, mazo)
        require(frente.isNotBlank() && reverso.isNotBlank()) { "Completa anverso y reverso." }
        if (id != 0) require(db.flashcardDao().getById(id)?.mazoId == mazo) { "La tarjeta ya no existe." }
        val entity = FlashcardEntity(id, mazo, frente.trim(), reverso.trim())
        if (id == 0) db.flashcardDao().insert(entity) else db.flashcardDao().update(entity)
        Unit
    }

    override suspend fun borrarTarjeta(usuario: Int, tarjeta: Flashcard) = db.withTransaction {
        verificar(usuario, tarjeta.mazoId)
        val entity = db.flashcardDao().getById(tarjeta.id)
        require(entity?.mazoId == tarjeta.mazoId) { "La tarjeta ya no existe." }
        db.flashcardDao().delete(requireNotNull(entity))
    }
}
