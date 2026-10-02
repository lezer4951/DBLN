package com.dubalin.app.presentation.ui.study

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.data.local.Cosmetics
import com.dubalin.app.databinding.FragmentStudyRouteBinding
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopFragment : Fragment(R.layout.fragment_study_route) {
    private val model: LearningViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentStudyRouteBinding.bind(view)
        b.toolbar.title = "Tienda"; b.resume.isVisible = false
        b.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.combine(model.store.changes) { s, _ -> s }.collect { s ->
                    b.summary.text = "${model.store.balance(model.user)} monedas\nAvatares, fondos y marcos para tu perfil.\n${s.feedback}"
                    b.levels.removeAllViews()
                    Cosmetics.items.forEach { item ->
                        val owned = item.id in model.store.owned(model.user)
                        val equipped = model.store.equipped(model.user, item.category) == item.id
                        val button = layoutInflater.inflate(R.layout.item_study_button, b.levels, false) as MaterialButton
                        button.text = "${item.name}\n${if(equipped) "Equipado" else if(owned) "Equipar" else "Comprar y equipar · ${item.price} monedas"}"
                        if(item.category == "avatar") button.setIconResource(if(item.id == "planet") R.drawable.avatar_planet else R.drawable.avatar_rocket)
                        button.isEnabled = s.ready && !s.busy && !equipped
                        button.setOnClickListener { model.buy(item) }; b.levels.addView(button)
                    }
                }
            }
        }
    }
}
