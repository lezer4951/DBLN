package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubalin.app.databinding.ItemApunteBinding
import com.dubalin.app.domain.model.Apunte
import java.text.DateFormat
import java.util.Date

class ApuntesAdapter(
    private val onClick: (Apunte) -> Unit,
    private val onActionsClick: (Apunte) -> Unit
) : ListAdapter<Apunte, ApuntesAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val dateFormatter = DateFormat.getDateTimeInstance(
        DateFormat.MEDIUM,
        DateFormat.SHORT
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemApunteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemApunteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(apunte: Apunte) {
            binding.tvTituloApunte.text = apunte.titulo
            binding.tvContenidoApunte.text = apunte.contenido
            binding.tvFechaApunte.text = dateFormatter.format(
                Date(apunte.fechaActualizacion)
            )
            binding.root.setOnClickListener { onClick(apunte) }
            binding.btnAccionesApunte.setOnClickListener {
                onActionsClick(apunte)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Apunte>() {
            override fun areItemsTheSame(old: Apunte, new: Apunte): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: Apunte, new: Apunte): Boolean =
                old == new
        }
    }
}
