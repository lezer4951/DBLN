package com.dubalin.app.presentation.ui.desafios

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentDesafiosBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DesafiosFragment : Fragment(R.layout.fragment_desafios) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentDesafiosBinding.bind(view)
        listOf(
            b.cardMemory to Pair("Memorama", getString(R.string.ref_memory_desc)),
            b.cardPvp to Pair("Duelo PvP", getString(R.string.ref_pvp_desc)),
            b.cardFriend to Pair("Reto entre amigos", getString(R.string.ref_friend_desc)),
            b.cardSurvival to Pair("Supervivencia", getString(R.string.ref_survival_desc)),
            b.cardRanking to Pair("Ranking por materia", getString(R.string.ref_ranking_desc))
        ).forEach { (card, copy) ->
            card.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(copy.first)
                    .setMessage("${copy.second}\n\nVista previa: todavía no inicia partidas ni otorga puntos.")
                    .setPositiveButton("Entendido", null)
                    .show()
            }
        }
    }
}
