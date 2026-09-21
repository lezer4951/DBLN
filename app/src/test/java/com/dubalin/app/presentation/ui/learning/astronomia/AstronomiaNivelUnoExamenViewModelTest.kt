package com.dubalin.app.presentation.ui.learning.astronomia

import com.dubalin.app.domain.model.ProgresoNivel
import com.dubalin.app.domain.repository.NivelAstronomiaRepository
import com.dubalin.app.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AstronomiaNivelUnoExamenViewModelTest {
    @Before fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())
    @After fun tearDown() = Dispatchers.resetMain()

    @Test fun `ocho de diez gradua el nivel uno`() {
        val repo = FakeNivelUnoRepository()
        val vm = AstronomiaNivelUnoExamenViewModel(FakeNivelUnoSession(), repo)
        repeat(10) { index ->
            val state = vm.state.value
            val q = state.preguntas[state.actual]
            vm.seleccionar(if (index < 8) q.respuestaCorrecta else (q.respuestaCorrecta + 1) % 4)
            vm.accion(); vm.accion()
        }
        assertTrue(vm.state.value.resultado!!.aprobado)
        assertEquals(8, repo.aciertos)
        assertEquals(listOf(12, 13), vm.state.value.resultado!!.temasDebiles)
    }

    @Test fun `siete de diez no gradua`() {
        val repo = FakeNivelUnoRepository()
        val vm = AstronomiaNivelUnoExamenViewModel(FakeNivelUnoSession(), repo)
        repeat(10) { index ->
            val s = vm.state.value; val q = s.preguntas[s.actual]
            vm.seleccionar(if (index < 7) q.respuestaCorrecta else (q.respuestaCorrecta + 1) % 4)
            vm.accion(); vm.accion()
        }
        assertFalse(vm.state.value.resultado!!.aprobado)
    }
}

private class FakeNivelUnoSession : SessionRepository {
    override val usuarioId = MutableStateFlow<Int?>(1)
    override fun getUsuarioId() = usuarioId.value
    override fun saveUsuarioId(id: Int) { usuarioId.value = id }
    override fun clearSession() { usuarioId.value = null }
}

private class FakeNivelUnoRepository : NivelAstronomiaRepository {
    var aciertos = 0
    override fun observarNivel(usuarioId: Int, nivel: Int): Flow<ProgresoNivel> = flowOf(ProgresoNivel(nivel))
    override suspend fun completarTema(usuarioId: Int, nivel: Int, indiceTema: Int) = Result.success(Unit)
    override suspend fun completarPractica(usuarioId: Int, nivel: Int) = Result.success(Unit)
    override suspend fun registrarEvaluacion(usuarioId: Int, nivel: Int, aciertos: Int, total: Int): Result<Boolean> {
        this.aciertos = aciertos
        return Result.success(aciertos * 100 / total >= 80)
    }
}
