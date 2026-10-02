package com.dubalin.app.presentation.ui.perfil

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dubalin.app.R
import com.dubalin.app.data.local.ProfileDetails
import com.dubalin.app.databinding.DialogProfileBinding
import com.dubalin.app.databinding.FragmentPerfilBinding
import com.dubalin.app.databinding.ItemSubjectRankBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine

@AndroidEntryPoint
class PerfilFragment : Fragment(R.layout.fragment_perfil) {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PerfilViewModel by viewModels()
    private val rankAdapter = RankAdapter()
    @javax.inject.Inject lateinit var learning: com.dubalin.app.data.local.LearningStore
    @javax.inject.Inject lateinit var session: com.dubalin.app.domain.repository.SessionRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentPerfilBinding.bind(view)
        binding.profileRanks.layoutManager = LinearLayoutManager(requireContext())
        binding.profileRanks.adapter = rankAdapter
        binding.shop.setOnClickListener { androidx.navigation.fragment.NavHostFragment.findNavController(this).navigate(R.id.shopFragment) }
        binding.btnEditProfile.setOnClickListener { editarPerfil() }
        binding.profileEditIcon.setOnClickListener { editarPerfil() }
        binding.btnCerrarSesion.setOnClickListener { confirmarCierreDeSesion() }
        mostrarDetalles()
        observarEstado()
    }

    private fun mostrarDetalles() {
        val d = viewModel.detalles()
        binding.profileCareer.text = d.carrera.ifBlank { getString(R.string.ref_unspecified) }
        binding.profileGrade.text = d.grado.ifBlank { getString(R.string.ref_unspecified) }
        binding.profileInterests.text = d.intereses.ifBlank { getString(R.string.ref_unspecified) }
    }

    private fun observarEstado() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.header.combine(learning.changes) { h, _ -> h }.collect { h ->
                        binding.profileName.text = h.nombre
                        binding.profileLevel.text = getString(R.string.home_nivel_xp, h.nivel, h.xp)
                        com.dubalin.app.presentation.ui.study.applyAvatar(binding.profileAvatar, learning, session.getUsuarioId() ?: 0, h.nombre.firstOrNull()?.uppercase() ?: "D")
                        val bg = com.dubalin.app.data.local.Cosmetics.items.firstOrNull { it.id == learning.equipped(session.getUsuarioId() ?: 0, "background") }
                        if (bg != null) {
                            binding.profileHero.setBackgroundColor(bg.color)
                            val ink = if(bg.id == "mint") 0xFF201A38.toInt() else 0xFFFFFFFF.toInt()
                            binding.profileName.setTextColor(ink); binding.profileLevel.setTextColor(ink); binding.profileEditIcon.setTextColor(ink)
                        }
                        rankAdapter.setAstronomiaRango(h.rangoAstronomia)
                        binding.profileRanks.isVisible = rankAdapter.itemCount > 0
                        binding.profileRanksEmpty.isVisible = rankAdapter.itemCount == 0
                    }
                }
                launch {
                    viewModel.sesionCerrada.collect { closed ->
                        if (closed) requireActivity().findNavController(R.id.nav_host_fragment)
                            .navigate(R.id.action_home_to_login)
                    }
                }
            }
        }
    }

    private fun editarPerfil() {
        val current = viewModel.detalles()
        val form = DialogProfileBinding.inflate(layoutInflater)
        form.career.setText(current.carrera)
        form.grade.setText(current.grado)
        form.interests.setText(current.intereses)
        MaterialAlertDialogBuilder(requireContext()).setTitle("Editar perfil").setView(form.root)
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.action_save) { _, _ ->
                viewModel.guardarDetalles(ProfileDetails(
                    form.career.text.toString(), form.grade.text.toString(), form.interests.text.toString()))
                mostrarDetalles()
            }.show()
    }

    private fun confirmarCierreDeSesion() {
        MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.profile_logout)
            .setMessage(R.string.profile_logout_question)
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.profile_logout) { _, _ -> viewModel.cerrarSesion() }.show()
    }

    private inner class RankAdapter : RecyclerView.Adapter<RankHolder>() {
        private var rangoAstronomia: Int? = null
        override fun getItemCount() = if (rangoAstronomia != null) 1 else 0
        fun setAstronomiaRango(rango: Int?) {
            val obtenido = rango?.takeIf { it > 0 }
            if (rangoAstronomia == obtenido) return
            rangoAstronomia = obtenido
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RankHolder(
            ItemSubjectRankBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        override fun onBindViewHolder(holder: RankHolder, position: Int) {
            holder.bind(
                Triple(R.drawable.ic_ref_sparkles, R.color.astronomy_blue, R.string.subject_astronomy),
                getString(R.string.astronomy_rank_value, requireNotNull(rangoAstronomia))
            )
        }
    }

    private inner class RankHolder(private val item: ItemSubjectRankBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: Triple<Int, Int, Int>, status: String) {
            item.rankIcon.setImageResource(data.first)
            item.rankIcon.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), data.second))
            item.rankTitle.setText(data.third)
            item.rankStatus.text = status
            item.root.layoutParams = (item.root.layoutParams as RecyclerView.LayoutParams).apply {
                bottomMargin = (8 * resources.displayMetrics.density).toInt()
            }
        }
    }

    override fun onDestroyView() {
        binding.profileRanks.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
