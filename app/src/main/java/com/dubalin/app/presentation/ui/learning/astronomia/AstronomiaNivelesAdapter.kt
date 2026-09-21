package com.dubalin.app.presentation.ui.learning.astronomia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubalin.app.R
import com.dubalin.app.databinding.ItemAstronomiaNivelBinding
import com.dubalin.app.domain.model.NivelAstronomia

class AstronomiaNivelesAdapter(
    private val onNivelDisponible: (NivelAstronomia) -> Unit
) : ListAdapter<NivelAstronomia, AstronomiaNivelesAdapter.NivelViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NivelViewHolder {
        val binding = ItemAstronomiaNivelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NivelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NivelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NivelViewHolder(
        private val binding: ItemAstronomiaNivelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nivel: NivelAstronomia) = with(binding) {
            levelNumber.text = itemView.context.getString(R.string.astronomy_level_number, nivel.numero)
            levelTitle.text = nivel.titulo
            levelDescription.text = nivel.descripcion
            levelStatus.setText(
                if (nivel.bloqueado) R.string.astronomy_level_locked else R.string.astronomy_level_available
            )
            levelStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(
                if (nivel.bloqueado) R.drawable.ic_lock else R.drawable.ic_ref_sparkles,
                0,
                0,
                0
            )
            root.isEnabled = !nivel.bloqueado
            root.isClickable = !nivel.bloqueado
            root.isFocusable = !nivel.bloqueado
            root.alpha = if (nivel.bloqueado) 0.56f else 1f
            root.contentDescription = itemView.context.getString(
                if (nivel.bloqueado) R.string.astronomy_level_locked_description
                else R.string.astronomy_level_available_description,
                nivel.numero,
                nivel.titulo
            )
            root.setOnClickListener(if (nivel.bloqueado) null else View.OnClickListener {
                onNivelDisponible(nivel)
            })
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<NivelAstronomia>() {
        override fun areItemsTheSame(oldItem: NivelAstronomia, newItem: NivelAstronomia): Boolean =
            oldItem.numero == newItem.numero

        override fun areContentsTheSame(oldItem: NivelAstronomia, newItem: NivelAstronomia): Boolean =
            oldItem == newItem
    }
}
