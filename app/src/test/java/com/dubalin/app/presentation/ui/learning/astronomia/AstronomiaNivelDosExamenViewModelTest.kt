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
class AstronomiaNivelDosExamenViewModelTest {
    @Before fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())
    @After fun tearDown() = Dispatchers.resetMain()

    @Test fun `ocho aciertos desbloquean nivel tres`() {
        val repo = FakeNivelDosRepository(); val vm = AstronomiaNivelDosExamenViewModel(FakeNivelDosSession(), repo)
        repeat(10) { i -> val s = vm.state.value; val q = s.preguntas[s.actual]
            vm.seleccionar(if (i < 8) q.respuestaCorrecta else (q.respuestaCorrecta + 1) % 4); vm.accion(); vm.accion() }
        assertTrue(vm.state.value.resultado!!.aprobado); assertEquals(2, repo.nivel); assertEquals(8, repo.aciertos)
    }

    @Test fun `siete aciertos conservan temas debiles`() {
        val vm = AstronomiaNivelDosExamenViewModel(FakeNivelDosSession(), FakeNivelDosRepository())
        repeat(10) { i -> val s = vm.state.value; val q = s.preguntas[s.actual]
            vm.seleccionar(if (i < 7) q.respuestaCorrecta else (q.respuestaCorrecta + 1) % 4); vm.accion(); vm.accion() }
        assertFalse(vm.state.value.resultado!!.aprobado)
        assertEquals(listOf(10, 12, 14), vm.state.value.resultado!!.temasDebiles)
    }
}

private class FakeNivelDosSession : SessionRepository {
    override val usuarioId = MutableStateFlow<Int?>(1); override fun getUsuarioId() = usuarioId.value
    override fun saveUsuarioId(id: Int) { usuarioId.value = id }; override fun clearSession() { usuarioId.value = null }
}
private class FakeNivelDosRepository : NivelAstronomiaRepository {
    var nivel = -1; var aciertos = -1
    override fun observarNivel(usuarioId: Int, nivel: Int): Flow<ProgresoNivel> = flowOf(ProgresoNivel(nivel))
    override suspend fun completarTema(usuarioId: Int, nivel: Int, indiceTema: Int) = Result.success(Unit)
    override suspend fun completarPractica(usuarioId: Int, nivel: Int) = Result.success(Unit)
    override suspend fun registrarEvaluacion(usuarioId: Int, nivel: Int, aciertos: Int, total: Int): Result<Boolean> {
        this.nivel = nivel; this.aciertos = aciertos; return Result.success(aciertos * 100 / total >= 80)
    }
}
