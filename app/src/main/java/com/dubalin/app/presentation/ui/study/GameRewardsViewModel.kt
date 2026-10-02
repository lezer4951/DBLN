package com.dubalin.app.presentation.ui.study

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dubalin.app.data.local.LearningStore
import com.dubalin.app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class GameRewardsViewModel @Inject constructor(private val store: LearningStore,
    private val session: SessionRepository, private val saved: SavedStateHandle) : ViewModel() {
    private val day = saved.get<String>("rewardDay") ?: store.today().also { saved["rewardDay"] = it }
    private var attempted = false
    private val mutable = MutableStateFlow(saved.get<String>("rewardMessage") ?: "")
    val message = mutable.asStateFlow()
    fun claim(game: String, amount: Int) {
        if(attempted || saved.get<Boolean>("rewardDone") == true) return
        if (amount <= 0) { mutable.value = "Sin monedas en esta partida."; return }
        attempted = true
        viewModelScope.launch {
            runCatching { withContext(Dispatchers.IO) {
                if(amount <= 0) 0 else store.rewardGame(requireNotNull(session.getUsuarioId()), game, amount.coerceAtMost(10), day)
            } }.onSuccess { coins ->
                val text = if(amount <= 0) "Sin monedas en esta partida." else if(coins == 0) "Ya recibiste la recompensa de este juego hoy." else "+$coins monedas para la tienda."
                saved["rewardDone"] = true; saved["rewardMessage"] = text; mutable.value = text
            }.onFailure { mutable.value = "No se pudo guardar la recompensa. Vuelve a abrir la pantalla para reintentar." }
        }
    }
}
