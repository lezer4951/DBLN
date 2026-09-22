package com.dubalin.app.presentation.ui.learning.materias

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dubalin.app.R
import com.dubalin.app.databinding.ItemMateriaHeaderBinding
import com.dubalin.app.databinding.ItemMateriaCardBinding
import com.dubalin.app.databinding.ItemMateriaDestacadaBinding

private const val TYPE_HEADER = 0
private const val TYPE_DESTACADA = 1
private const val TYPE_CARD = 2

class MateriasAdapter(
    private val items: List<MateriaListItem>,
    private val onClick: (MateriaListItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) = when (items[position]) {
        is MateriaListItem.Header -> TYPE_HEADER
        is MateriaListItem.Destacada -> TYPE_DESTACADA
        is MateriaListItem.Card -> TYPE_CARD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderVH(ItemMateriaHeaderBinding.inflate(inflater, parent, false))
            TYPE_DESTACADA -> DestacadaVH(ItemMateriaDestacadaBinding.inflate(inflater, parent, false))
            else -> CardVH(ItemMateriaCardBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is MateriaListItem.Header -> (holder as HeaderVH).bind(item)
            is MateriaListItem.Destacada -> (holder as DestacadaVH).bind(item, onClick)
            is MateriaListItem.Card -> (holder as CardVH).bind(item, onClick)
        }
    }

    class HeaderVH(private val b: ItemMateriaHeaderBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: MateriaListItem.Header) {
            b.tvTituloSeccion.text = item.titulo
            b.dotColor.backgroundTintList = ColorStateList.valueOf(item.colorFamilia)
        }
    }

    class DestacadaVH(private val b: ItemMateriaDestacadaBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: MateriaListItem.Destacada, onClick: (MateriaListItem) -> Unit) {
            b.tvNombreDestacada.text = item.nombre
            b.tvDescripcionDestacada.text = item.descripcion
            b.tvNivelDestacada.text = if (item.nivelActual > 10) "Ruta completada" else "Nivel ${item.nivelActual}"
            b.ivIconoDestacada.setImageResource(item.iconRes)
            b.root.setOnClickListener { onClick(item) }
        }
    }

    class CardVH(private val b: ItemMateriaCardBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: MateriaListItem.Card, onClick: (MateriaListItem) -> Unit) {
            b.tvNombre.text = item.nombre
            b.tvDescripcion.text = item.descripcion
            b.ivIcono.setImageResource(item.iconRes)

            b.root.backgroundTintList = ColorStateList.valueOf(item.cardBgColor)
            b.iconChip.backgroundTintList = ColorStateList.valueOf(item.chipBgColor)

            if (item.disponible) {
                b.tvEstado.text = "Disponible"
                b.tvEstado.setTextColor(0xFFFFFFFF.toInt())
                b.tvEstado.backgroundTintList = ColorStateList.valueOf(0xFF5B4EF0.toInt())
            } else {
                b.tvEstado.text = "Próximamente"
                b.tvEstado.setTextColor(0xFFB4652C.toInt())
                b.tvEstado.backgroundTintList = ColorStateList.valueOf(0xB3FFFFFF.toInt())
            }

            b.root.isEnabled = item.disponible
            b.root.isClickable = item.disponible
            b.root.isFocusable = item.disponible
            b.root.setOnClickListener(if (item.disponible) android.view.View.OnClickListener { onClick(item) } else null)
        }
    }
}
