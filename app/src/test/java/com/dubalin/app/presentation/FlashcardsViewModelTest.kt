package com.dubalin.app.presentation

import com.dubalin.app.domain.model.Flashcard
import com.dubalin.app.domain.model.Mazo
import com.dubalin.app.domain.repository.FlashcardsRepository
import com.dubalin.app.domain.repository.SessionRepository
import com.dubalin.app.presentation.ui.autoestudio.flashcards.FlashcardsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class FlashcardsViewModelTest {
    @Before fun setup() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
    @After fun cleanup() { Dispatchers.resetMain() }
    private fun model(cards: List<Flashcard>): FlashcardsViewModel {
        val repo = object : FlashcardsRepository {
            override fun mazos(usuario: Int) = flowOf(listOf(Mazo(1, "Inglés", "")))
            override fun tarjetas(mazo: Int) = flowOf(cards)
            override suspend fun guardarMazo(usuario: Int, id: Int, titulo: String, descripcion: String) {}
            override suspend fun borrarMazo(usuario: Int, id: Int) {}
            override suspend fun guardarTarjeta(usuario: Int, mazo: Int, id: Int, frente: String, reverso: String) {}
            override suspend fun borrarTarjeta(usuario: Int, tarjeta: Flashcard) {}
        }
        val session = object : SessionRepository {
            override val usuarioId = MutableStateFlow<Int?>(1)
            override fun getUsuarioId() = usuarioId.value
            override fun saveUsuarioId(id: Int) { usuarioId.value = id }
            override fun clearSession() { usuarioId.value = null }
        }
        return FlashcardsViewModel(repo, session).also { it.abrir(Mazo(1, "Inglés", "")) }
    }
    @Test fun emptyDeckCannotStart() {
        val vm = model(emptyList())
        vm.estudiar(false)
        assertNull(vm.uiState.value.estudio)
    }
    @Test fun answerMustBeRevealedAndCannotBeCountedTwice() {
        val vm = model(listOf(Flashcard(1, 1, "Perro", "Dog")))
        vm.estudiar(true)
        assertTrue(vm.uiState.value.invertido)
        vm.responder(true)
        assertEquals(0, vm.uiState.value.indice)
        vm.revelar()
        vm.responder(true)
        vm.responder(true)
        assertEquals(1, vm.uiState.value.aciertos)
        assertEquals(1, vm.uiState.value.indice)
        vm.volver()
        assertNull(vm.uiState.value.estudio)
        assertNotNull(vm.uiState.value.mazo)
    }
}
