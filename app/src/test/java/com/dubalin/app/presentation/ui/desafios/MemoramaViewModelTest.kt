package com.dubalin.app.presentation.ui.desafios

import androidx.lifecycle.SavedStateHandle
import com.dubalin.app.domain.model.Flashcard
import com.dubalin.app.domain.model.Mazo
import com.dubalin.app.domain.repository.FlashcardsRepository
import com.dubalin.app.domain.repository.SessionRepository
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
class MemoramaViewModelTest {
    @Before fun configurar() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
    @After fun terminar() { Dispatchers.resetMain() }
    @Test fun completaParejasSinAceptarUnaCartaDosVeces() {
        val saved = SavedStateHandle(mapOf("textos" to arrayListOf("a", "A", "b", "B", "c", "C", "d", "D"),
            "parejas" to intArrayOf(0, 0, 1, 1, 2, 2, 3, 3)))
        val vm = MemoramaViewModel(RepoMemorama(), SesionMemorama(), saved)
        vm.elegir(0); vm.elegir(0)
        assertEquals(1, vm.state.value.visibles.size)
        assertEquals(0, vm.state.value.intentos)
        vm.elegir(1)
        assertEquals(setOf(0), vm.state.value.encontradas)
        val nuevo = MemoramaViewModel(RepoMemorama(), SesionMemorama(), SavedStateHandle(saved.keys().associateWith { saved.get<Any?>(it) }))
        nuevo.continuar()
        for (i in 1..3) { nuevo.elegir(i * 2); nuevo.elegir(i * 2 + 1); nuevo.continuar() }
        assertTrue(nuevo.state.value.completo)
        assertEquals(4, nuevo.state.value.intentos)
    }
    @Test fun muestraVacioCuandoNoHayTarjetas() {
        val vm = MemoramaViewModel(RepoMemorama(), SesionMemorama(), SavedStateHandle())
        assertTrue(vm.state.value.textos.isEmpty())
        assertFalse(vm.state.value.cargando)
        assertFalse(vm.state.value.error)
    }
}
private class SesionMemorama : SessionRepository {
    override val usuarioId = MutableStateFlow<Int?>(1)
    override fun getUsuarioId() = usuarioId.value
    override fun saveUsuarioId(id: Int) { usuarioId.value = id }
    override fun clearSession() { usuarioId.value = null }
}
private class RepoMemorama : FlashcardsRepository {
    override fun mazos(usuario: Int) = flowOf(emptyList<Mazo>())
    override fun tarjetas(mazo: Int) = flowOf(emptyList<Flashcard>())
    override suspend fun guardarMazo(usuario: Int, id: Int, titulo: String, descripcion: String) = Unit
    override suspend fun borrarMazo(usuario: Int, id: Int) = Unit
    override suspend fun guardarTarjeta(usuario: Int, mazo: Int, id: Int, frente: String, reverso: String) = Unit
    override suspend fun borrarTarjeta(usuario: Int, tarjeta: Flashcard) = Unit
}
