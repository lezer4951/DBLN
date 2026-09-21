package com.dubalin.app.presentation.ui.desafios

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentDesafiosBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DesafiosFragment : Fragment(R.layout.fragment_desafios) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentDesafiosBinding.bind(view)
        listOf(
            b.cardPvp to Pair("Duelo PvP", getString(R.string.ref_pvp_desc)),
            b.cardFriend to Pair("Reto entre amigos", getString(R.string.ref_friend_desc)),
            b.cardRanking to Pair("Ranking por materia", getString(R.string.ref_ranking_desc))
        ).forEach { (card, copy) ->
            card.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(copy.first)
                    .setMessage(getString(R.string.challenge_online_pending, copy.second))
                    .setPositiveButton("Entendido", null)
                    .show()
            }
        }
        b.cardMemory.setOnClickListener { findNavController().navigate(R.id.action_desafios_to_memorama) }
        b.cardSurvival.setOnClickListener { findNavController().navigate(R.id.action_desafios_to_supervivencia) }
    }
}
