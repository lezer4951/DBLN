package com.dubalin.app.presentation.ui.learning.astronomia

import com.dubalin.app.domain.model.ExperienciaAstronomia
import com.dubalin.app.domain.model.FormatoAprendizaje
import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.ProgresoMateria
import com.dubalin.app.domain.repository.ProgresoAcademicoRepository
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
class AstronomiaNivelCeroViewModelTest {
    @Before fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())
    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `un tema exige dos aciertos y explicacion propia antes de guardarse`() {
        val repository = FakeNivelRepository()
        val viewModel = AstronomiaNivelCeroViewModel(FakeNivelSession(), repository)
        viewModel.iniciarAutoevaluacion()

        repeat(2) { index ->
            val state = viewModel.uiState.value
            viewModel.seleccionarOpcion(state.pregunta!!.respuestaCorrecta)
            viewModel.comprobarOContinuar()
            if (index == 0) viewModel.comprobarOContinuar()
        }

        viewModel.comprobarOContinuar()
        assertEquals(null, repository.temaGuardado)
        assertFalse(viewModel.uiState.value.temaActualCompletado)

        viewModel.actualizarExplicacionPropia("La astronomía usa evidencia para explicar el universo.")
        viewModel.comprobarOContinuar()
        assertEquals(0, repository.temaGuardado)
        assertTrue(viewModel.uiState.value.temaActualCompletado)
    }
}

private class FakeNivelSession : SessionRepository {
    override val usuarioId = MutableStateFlow<Int?>(1)
    override fun getUsuarioId(): Int? = usuarioId.value
    override fun saveUsuarioId(id: Int) { usuarioId.value = id }
    override fun clearSession() { usuarioId.value = null }
}

private class FakeNivelRepository : ProgresoAcademicoRepository {
    var temaGuardado: Int? = null
    override fun observarProgreso(usuarioId: Int, materiaId: MateriaId): Flow<ProgresoMateria> =
        flowOf(ProgresoMateria(materiaId = materiaId, onboardingCompletado = true))
    override suspend fun registrarPagina(usuarioId: Int, materiaId: MateriaId, pagina: Int) = Result.success(Unit)
    override suspend fun configurarPlanAstronomia(usuarioId: Int, experiencia: ExperienciaAstronomia,
        minutosDiarios: Int, formato: FormatoAprendizaje) = Result.success(Unit)
    override suspend fun completarTemaNivelCero(usuarioId: Int, indiceTema: Int): Result<Unit> {
        temaGuardado = indiceTema
        return Result.success(Unit)
    }
    override suspend fun registrarEvaluacionNivelCero(usuarioId: Int, materiaId: MateriaId,
        aciertos: Int, totalPreguntas: Int) = Result.success(false)
}
