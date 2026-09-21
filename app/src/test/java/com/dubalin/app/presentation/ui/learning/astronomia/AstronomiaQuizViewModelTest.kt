package com.dubalin.app.presentation.ui.learning.astronomia

import com.dubalin.app.domain.model.MateriaId
import com.dubalin.app.domain.model.ExperienciaAstronomia
import com.dubalin.app.domain.model.FormatoAprendizaje
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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AstronomiaQuizViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `diez respuestas correctas aprueban y se guardan`() {
        val repository = FakeProgresoRepository()
        val viewModel = AstronomiaQuizViewModel(FakeSessionRepository(), repository)

        repeat(viewModel.uiState.value.preguntas.size) {
            val state = viewModel.uiState.value
            viewModel.seleccionarOpcion(state.preguntas[state.preguntaActual].respuestaCorrecta)
            viewModel.accionPrincipal()
            viewModel.accionPrincipal()
        }

        val result = assertNotNull(viewModel.uiState.value.resultado)
            .let { viewModel.uiState.value.resultado!! }
        assertEquals(10, result.aciertos)
        assertTrue(result.aprobado)
        assertEquals(10, repository.lastScore)
    }

    @Test
    fun `tres aciertos no desbloquean el nivel uno`() {
        val repository = FakeProgresoRepository()
        val viewModel = AstronomiaQuizViewModel(FakeSessionRepository(), repository)

        repeat(viewModel.uiState.value.preguntas.size) { index ->
            val state = viewModel.uiState.value
            val pregunta = state.preguntas[state.preguntaActual]
            val opcion = if (index < 3) pregunta.respuestaCorrecta else (pregunta.respuestaCorrecta + 1) % 4
            viewModel.seleccionarOpcion(opcion)
            viewModel.accionPrincipal()
            viewModel.accionPrincipal()
        }

        assertFalse(viewModel.uiState.value.resultado!!.aprobado)
        assertEquals(3, repository.lastScore)
        assertEquals(listOf(4, 5, 6, 7, 8, 9, 10), viewModel.uiState.value.resultado!!.temasDebiles)
    }
}

private class FakeSessionRepository : SessionRepository {
    override val usuarioId = MutableStateFlow<Int?>(1)
    override fun getUsuarioId(): Int? = usuarioId.value
    override fun saveUsuarioId(id: Int) { usuarioId.value = id }
    override fun clearSession() { usuarioId.value = null }
}

private class FakeProgresoRepository : ProgresoAcademicoRepository {
    var lastScore: Int? = null

    override fun observarProgreso(
        usuarioId: Int,
        materiaId: MateriaId
    ): Flow<ProgresoMateria> = flowOf(ProgresoMateria(materiaId))

    override suspend fun registrarPagina(
        usuarioId: Int,
        materiaId: MateriaId,
        pagina: Int
    ): Result<Unit> = Result.success(Unit)

    override suspend fun configurarPlanAstronomia(
        usuarioId: Int,
        experiencia: ExperienciaAstronomia,
        minutosDiarios: Int,
        formato: FormatoAprendizaje
    ): Result<Unit> = Result.success(Unit)

    override suspend fun completarTemaNivelCero(
        usuarioId: Int,
        indiceTema: Int
    ): Result<Unit> = Result.success(Unit)

    override suspend fun registrarEvaluacionNivelCero(
        usuarioId: Int,
        materiaId: MateriaId,
        aciertos: Int,
        totalPreguntas: Int
    ): Result<Boolean> {
        lastScore = aciertos
        return Result.success((aciertos * 100) / totalPreguntas >= 80)
    }
}
