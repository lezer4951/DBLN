package com.dubalin.app.presentation.ui.learning.astronomia

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.domain.model.PreguntaAstronomia
import com.dubalin.app.domain.model.mezclar
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class EvaluacionResultado(val aciertos: Int, val total: Int, val aprobado: Boolean, val temasDebiles: List<Int>)
data class EvaluacionUiState(
    val preguntas: List<PreguntaAstronomia>, val actual: Int = 0, val seleccion: Int? = null,
    val aciertos: Int = 0, val temasDebiles: Set<Int> = emptySet(), val feedback: Boolean? = null,
    val guardando: Boolean = false, val resultado: EvaluacionResultado? = null,
    val completada: Boolean = false, val error: Boolean = false
)

/** Un solo motor evita divergencias entre los veinte flujos de evaluación. */
open class EvaluacionAstronomiaViewModel(
    private val banco: List<PreguntaAstronomia>,
    private val practica: Boolean,
    private val saved: SavedStateHandle,
    private val guardar: suspend (Int, Int) -> Result<Boolean>
) : ViewModel() {
    protected open val finalizarAlFallar = false
    private val mutable = MutableStateFlow(restaurar())
    val state = mutable.asStateFlow()

    private fun preguntas(): List<PreguntaAstronomia> {
        val semilla = saved.get<Int>("semilla") ?: Random.nextInt().also { saved["semilla"] = it }
        val random = Random(semilla)
        return banco.map { it.mezclar(random) }
    }

    private fun restaurar(): EvaluacionUiState {
        val preguntas = preguntas()
        val actual = (saved.get<Int>("actual") ?: 0).coerceIn(preguntas.indices)
        val aciertos = (saved.get<Int>("aciertos") ?: 0).coerceIn(0, preguntas.size)
        val debiles = saved.get<IntArray>("debiles")?.toSet().orEmpty()
        val aprobado = saved.get<Boolean>("aprobado")
        return EvaluacionUiState(preguntas, actual,
            saved.get<Int>("seleccion")?.takeIf { it in preguntas[actual].opciones.indices },
            aciertos, debiles, saved["feedback"], resultado = aprobado?.let {
                EvaluacionResultado(aciertos, preguntas.size, it, debiles.sorted())
            }, completada = saved["completada"] ?: false)
    }

    private fun publicar(s: EvaluacionUiState) {
        saved["actual"] = s.actual
        saved["seleccion"] = s.seleccion
        saved["aciertos"] = s.aciertos
        saved["debiles"] = s.temasDebiles.toIntArray()
        saved["feedback"] = s.feedback
        saved["aprobado"] = s.resultado?.aprobado
        saved["completada"] = s.completada
        mutable.value = s
    }

    fun seleccionar(i: Int) {
        val s = state.value
        if (!s.guardando && s.resultado == null && !s.completada && s.feedback == null && i in s.preguntas[s.actual].opciones.indices)
            publicar(s.copy(seleccion = i, error = false))
    }

    fun accion() {
        val s = state.value
        if (s.guardando || s.resultado != null || s.completada) return
        if (s.feedback == null) {
            val seleccion = s.seleccion ?: return
            val q = s.preguntas[s.actual]
            val ok = seleccion == q.respuestaCorrecta
            publicar(s.copy(feedback = ok, aciertos = s.aciertos + if (ok) 1 else 0,
                temasDebiles = if (ok) s.temasDebiles else s.temasDebiles + q.tema))
        } else if (s.actual < s.preguntas.lastIndex && !(finalizarAlFallar && s.feedback == false)) {
            publicar(s.copy(actual = s.actual + 1, seleccion = null, feedback = null))
        } else {
            // Bloquea dobles envíos antes de iniciar la coroutine.
            publicar(s.copy(guardando = true, error = false))
            viewModelScope.launch {
                val resultado = try { guardar(s.aciertos, s.preguntas.size) }
                catch (e: CancellationException) { throw e }
                catch (e: Exception) { Result.failure(e) }
                resultado.onSuccess { aprobado ->
                    publicar(s.copy(completada = practica, resultado = if (practica) null else
                        EvaluacionResultado(s.aciertos, s.preguntas.size, aprobado, s.temasDebiles.sorted())))
                }.onFailure { publicar(s.copy(error = true)) }
            }
        }
    }

    fun reiniciar() {
        if (state.value.guardando) return
        saved["semilla"] = Random.nextInt()
        publicar(EvaluacionUiState(preguntas()))
    }
    fun errorMostrado() = publicar(state.value.copy(error = false))
}
