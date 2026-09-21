package com.dubalin.app.presentation.ui.perfil

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

@AndroidEntryPoint
class PerfilFragment : Fragment(R.layout.fragment_perfil) {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PerfilViewModel by viewModels()
    private val rankAdapter = RankAdapter()

    private val rankStyles = listOf(
        Triple(R.drawable.ic_ref_math, R.color.dubalin_brand, R.string.subject_math),
        Triple(R.drawable.ic_ref_book, R.color.dubalin_pink, R.string.subject_spanish),
        Triple(R.drawable.ic_ref_globe, R.color.dubalin_green, R.string.subject_english),
        Triple(R.drawable.ic_ref_atom, R.color.dubalin_orange, R.string.subject_physics),
        Triple(R.drawable.ic_ref_flask, R.color.dubalin_cyan, R.string.subject_chemistry),
        Triple(R.drawable.ic_ref_leaf, R.color.dubalin_green, R.string.subject_biology),
        Triple(R.drawable.ic_ref_history, R.color.dubalin_purple, R.string.subject_history),
        Triple(R.drawable.ic_ref_globe, R.color.dubalin_blue, R.string.subject_geography),
        Triple(R.drawable.ic_ref_heart, R.color.dubalin_pink, R.string.subject_civic)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentPerfilBinding.bind(view)
        binding.profileRanks.layoutManager = LinearLayoutManager(requireContext())
        binding.profileRanks.adapter = rankAdapter
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
                    viewModel.header.collect { h ->
                        binding.profileName.text = h.nombre
                        binding.profileLevel.text = getString(R.string.home_nivel_xp, h.nivel, h.xp)
                        binding.profileAvatar.text = h.nombre.firstOrNull()?.uppercase() ?: "D"
                        rankAdapter.setAstronomiaRango(h.rangoAstronomia)
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
        override fun getItemCount() = rankStyles.size + if (rangoAstronomia != null) 1 else 0
        fun setAstronomiaRango(rango: Int?) {
            if (rangoAstronomia == rango) return
            rangoAstronomia = rango
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RankHolder(
            ItemSubjectRankBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        override fun onBindViewHolder(holder: RankHolder, position: Int) {
            if (rangoAstronomia != null && position == 0) {
                holder.bind(
                    Triple(R.drawable.ic_ref_sparkles, R.color.astronomy_blue, R.string.subject_astronomy),
                    getString(R.string.astronomy_rank_value, rangoAstronomia)
                )
            } else {
                holder.bind(rankStyles[position - if (rangoAstronomia != null) 1 else 0], getString(R.string.ref_unevaluated))
            }
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
