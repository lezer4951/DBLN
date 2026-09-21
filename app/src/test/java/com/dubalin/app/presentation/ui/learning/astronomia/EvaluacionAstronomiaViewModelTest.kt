package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.SavedStateHandle
import com.dubalin.app.domain.model.CatalogoAstronomia
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EvaluacionAstronomiaViewModelTest {
    @Before fun configurar() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
    @After fun terminar() { Dispatchers.resetMain() }
    @Test fun restauraOpcionesYRespuestaSinSumarDosVeces() {
        val saved = SavedStateHandle()
        val banco = CatalogoAstronomia.examenes[10]
        val vm = EvaluacionAstronomiaViewModel(banco, false, saved) { _, _ -> Result.success(true) }
        vm.seleccionar(vm.state.value.preguntas[0].respuestaCorrecta)
        vm.accion()
        val restaurado = SavedStateHandle(saved.keys().associateWith { saved.get<Any?>(it) })
        val nuevo = EvaluacionAstronomiaViewModel(banco, false, restaurado) { _, _ -> Result.success(true) }
        assertEquals(vm.state.value.preguntas, nuevo.state.value.preguntas)
        assertEquals(1, nuevo.state.value.aciertos)
        assertEquals(true, nuevo.state.value.feedback)
        nuevo.accion()
        assertEquals(1, nuevo.state.value.actual)
        assertEquals(1, nuevo.state.value.aciertos)
    }
    @Test fun dobleEnvioNoDuplicaGuardado() = runTest {
        val pendiente = CompletableDeferred<Result<Boolean>>()
        var guardados = 0
        val vm = EvaluacionAstronomiaViewModel(CatalogoAstronomia.examenes[1].take(1), false, SavedStateHandle()) { _, _ ->
            guardados++; pendiente.await()
        }
        vm.seleccionar(vm.state.value.preguntas[0].respuestaCorrecta)
        vm.accion(); vm.accion(); vm.accion()
        assertEquals(1, guardados)
        assertTrue(vm.state.value.guardando)
        pendiente.complete(Result.success(true))
        runCurrent()
        assertNotNull(vm.state.value.resultado)
        vm.accion()
        assertEquals(1, guardados)
    }
    @Test fun errorPermiteReintentarSinPerderResultado() {
        var llamadas = 0
        val vm = EvaluacionAstronomiaViewModel(CatalogoAstronomia.examenes[1].take(1), false, SavedStateHandle()) { _, _ ->
            if (++llamadas == 1) Result.failure(IllegalStateException()) else Result.success(true)
        }
        vm.seleccionar(vm.state.value.preguntas[0].respuestaCorrecta)
        vm.accion(); vm.accion()
        assertTrue(vm.state.value.error)
        assertFalse(vm.state.value.guardando)
        vm.accion()
        assertEquals(1, vm.state.value.resultado!!.aciertos)
    }
    @Test fun supervivenciaTerminaAlPrimerErrorSinAvanzar() {
        val vm = PracticaLibreViewModel(SavedStateHandle(mapOf("supervivencia" to true)))
        val q = vm.state.value.preguntas[0]
        vm.seleccionar((q.respuestaCorrecta + 1) % q.opciones.size)
        vm.accion(); vm.accion()
        assertNotNull(vm.state.value.resultado)
        assertEquals(0, vm.state.value.actual)
        assertEquals(0, vm.state.value.aciertos)
    }
}
