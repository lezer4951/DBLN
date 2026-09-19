package com.dubalin.app.presentation.ui.autoestudio.misapuntes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubalin.app.databinding.ItemSeccionBinding
import com.dubalin.app.domain.model.SeccionApuntes

class SeccionesAdapter(
    private val onClick: (SeccionApuntes) -> Unit,
    private val onLongClick: (SeccionApuntes) -> Unit
) : ListAdapter<SeccionApuntes, SeccionesAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSeccionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemSeccionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(seccion: SeccionApuntes) {
            binding.tvNombreSeccion.text = seccion.nombre
            binding.root.setOnClickListener { onClick(seccion) }
            binding.root.setOnLongClickListener {
                onLongClick(seccion)
                true
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SeccionApuntes>() {
            override fun areItemsTheSame(old: SeccionApuntes, new: SeccionApuntes) =
                old.id == new.id

            override fun areContentsTheSame(old: SeccionApuntes, new: SeccionApuntes) =
                old == new
        }
    }
}
