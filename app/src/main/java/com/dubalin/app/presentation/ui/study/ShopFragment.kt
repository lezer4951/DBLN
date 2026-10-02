package com.dubalin.app.presentation.ui.study

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dubalin.app.R
import com.dubalin.app.data.local.Cosmetic
import com.dubalin.app.data.local.Cosmetics
import com.dubalin.app.databinding.FragmentShopBinding
import com.dubalin.app.databinding.ItemShopHeaderBinding
import com.dubalin.app.databinding.ItemShopProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopFragment : Fragment(R.layout.fragment_shop) {
    private val model: LearningViewModel by viewModels()
    private var category = "all"
    private var binding: FragmentShopBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        category = savedInstanceState?.getString("category") ?: "all"
        val b = FragmentShopBinding.bind(view); binding = b
        com.dubalin.app.presentation.ui.learning.Motion.enter(b.root)
        b.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        val adapter = ShopAdapter()
        val minimumWidth = (180 * resources.configuration.fontScale.coerceAtLeast(1f)).toInt()
        val columns = ((resources.configuration.screenWidthDp - 32) / minimumWidth).coerceIn(1, 3)
        b.products.layoutManager = GridLayoutManager(requireContext(), columns).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = if(position == 0) columns else 1
            }
        }
        b.products.adapter = adapter
        b.products.itemAnimator = null
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.combine(model.store.changes) { s, _ -> s }.collect { adapter.update(it) }
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) { super.onSaveInstanceState(outState); outState.putString("category", category) }
    override fun onDestroyView() { binding?.products?.adapter = null; binding = null; super.onDestroyView() }

    private inner class ShopAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var screen = model.state.value
        private var items = filtered()
        private fun filtered() = Cosmetics.items.filter { category == "all" || it.category == category }
        fun update(s: LessonScreen) { screen = s; items = filtered(); notifyDataSetChanged() }
        override fun getItemCount() = items.size + 1
        override fun getItemViewType(position: Int) = if(position == 0) 0 else 1
        override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return if(type == 0) Header(ItemShopHeaderBinding.inflate(inflater, parent, false))
            else Product(ItemShopProductBinding.inflate(inflater, parent, false))
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is Header) holder.bind() else (holder as Product).bind(items[position - 1])
        }
        private inner class Header(val b: ItemShopHeaderBinding) : RecyclerView.ViewHolder(b.root) {
            fun bind() {
                b.balance.text = "${model.store.balance(model.user)} monedas"
                b.feedback.text = screen.feedback; b.feedback.isVisible = screen.feedback.isNotEmpty()
                b.categories.setOnCheckedStateChangeListener(null)
                b.categories.check(when(category) { "avatar" -> b.avatars.id; "background" -> b.backgrounds.id; "frame" -> b.frames.id; else -> b.all.id })
                b.categories.setOnCheckedStateChangeListener { _, ids ->
                    val next = when(ids.firstOrNull()) { b.avatars.id -> "avatar"; b.backgrounds.id -> "background"; b.frames.id -> "frame"; else -> "all" }
                    if(next != category) { category = next; update(screen) }
                }
            }
        }
        private inner class Product(val b: ItemShopProductBinding) : RecyclerView.ViewHolder(b.root) {
            fun bind(item: Cosmetic) {
                val owned = item.id in model.store.owned(model.user)
                val equipped = model.store.equipped(model.user, item.category) == item.id
                val balance = model.store.balance(model.user)
                val dp = resources.displayMetrics.density
                b.name.text = item.name.removePrefix("Avatar ").removePrefix("Fondo ").removePrefix("Marco ")
                b.price.text = if(owned) "En tu colección" else "${item.price} monedas"
                b.badge.text = if(equipped) "Equipado" else "Tuyo"; b.badge.isVisible = owned
                b.stage.background = GradientDrawable().apply {
                    cornerRadius = 18 * dp
                    setColor(if(item.category == "background") item.color else ContextCompat.getColor(requireContext(), R.color.dubalin_primary_soft))
                }
                b.art.setImageResource(if(item.id == "rocket") R.drawable.avatar_rocket else R.drawable.avatar_planet)
                b.art.background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL; setColor(0xFF5138DE.toInt())
                    if(item.category == "frame") setStroke((4 * dp).toInt(), item.color)
                }
                b.root.strokeColor = ContextCompat.getColor(requireContext(), if(equipped) R.color.md_primary else R.color.dubalin_line)
                b.root.strokeWidth = ((if(equipped) 2 else 1) * dp).toInt()
                b.buy.text = when { equipped -> "En uso"; owned -> "Equipar"; balance < item.price -> "Faltan ${item.price - balance}"; else -> "Comprar" }
                b.buy.isEnabled = screen.ready && !screen.busy && !equipped && (owned || balance >= item.price)
                b.buy.setOnClickListener { model.buy(item) }
                b.buy.contentDescription = "${b.buy.text}: ${item.name}"
                b.stage.contentDescription = "Vista previa de ${item.name}"
            }
        }
    }
}
