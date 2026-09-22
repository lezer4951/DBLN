package com.dubalin.app.presentation.ui.autoestudio.flashcards

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentFlashcardsBinding
import com.dubalin.app.domain.model.Mazo
import com.dubalin.app.domain.model.Flashcard
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlashcardsFragment : Fragment(R.layout.fragment_flashcards) {
    private var binding: FragmentFlashcardsBinding? = null
    private val vm: FlashcardsViewModel by viewModels()
    private var rows: List<Pair<String, () -> Unit>> = emptyList()
    private var renderedCard: String? = null
    private var renderedRevealed = false
    private var showingBack = false
    private var flipping = false
    private val adapter = object : RecyclerView.Adapter<RowHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RowHolder(
            com.dubalin.app.databinding.ItemHubCardBinding.inflate(
                android.view.LayoutInflater.from(parent.context), parent, false))
        override fun getItemCount() = rows.size
        override fun onBindViewHolder(holder: RowHolder, position: Int) {
            val row = rows[position]
            holder.item.hubTitle.text = row.first.substringBefore('\n')
            holder.item.hubDetail.text = row.first.substringAfter('\n', getString(R.string.ui_open))
            holder.item.root.isClickable = true
            holder.item.root.isFocusable = true
            holder.item.root.setOnClickListener { row.second() }
        }
    }
    private class RowHolder(val item: com.dubalin.app.databinding.ItemHubCardBinding) :
        RecyclerView.ViewHolder(item.root)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentFlashcardsBinding.bind(view)
        binding = b
        childFragmentManager.setFragmentResultListener(CrearMazoBottomSheet.RESULT, viewLifecycleOwner) { _, result ->
            vm.guardarMazo(null, result.getString("nombre").orEmpty(), result.getString("descripcion").orEmpty())
        }
        b.listFlashcards.layoutManager = LinearLayoutManager(requireContext())
        b.listFlashcards.adapter = adapter
        b.toolbarFlashcards.setNavigationOnClickListener { volver() }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = volver()
        })
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    render(state)
                    state.error?.let {
                        Snackbar.make(b.root, it, Snackbar.LENGTH_LONG).show()
                        vm.errorMostrado()
                    }
                }
            }
        }
    }
    private fun volver() {
        val s = vm.uiState.value
        if (s.estudio != null && s.indice < s.estudio.size) {
            confirmar("¿Salir del estudio?", "Se perderá el resultado de esta sesión.") { vm.volver() }
        } else if (s.mazo != null) vm.volver() else findNavController().navigateUp()
    }
    private fun render(s: FlashcardsState) {
        val b = binding ?: return
        b.toolbarFlashcards.title = s.mazo?.titulo ?: getString(R.string.autoestudio_flashcards_title)
        b.progressFlashcards.isVisible = s.loading || s.saving
        b.listFlashcards.isVisible = s.estudio == null
        b.studyScroll.isVisible = s.estudio != null
        b.primaryFlashcards.isEnabled = !s.loading && !s.saving
        b.secondaryFlashcards.isEnabled = !s.loading && !s.saving
        b.primaryFlashcards.backgroundTintList = androidx.appcompat.content.res.AppCompatResources.getColorStateList(requireContext(), R.color.button_primary)
        b.primaryFlashcards.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.md_on_primary))
        b.secondaryFlashcards.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.md_primary))
        b.thirdFlashcards.isVisible = false
        b.secondaryFlashcards.isVisible = false
        if (s.estudio != null) {
            val card = s.estudio.getOrNull(s.indice)
            if (card == null) {
                resetFlip()
                renderedCard = null
                b.studySide.setText(R.string.ui_summary)
                b.studyHint.isVisible = false
                b.studyCard.isClickable = false
                b.studyCard.isFocusable = false
                b.studyCard.setOnClickListener(null)
                b.statusFlashcards.text = "Sesión terminada"
                b.studyText.text = "Recordaste ${s.aciertos} de ${s.estudio.size} tarjetas.\n\nPor practicar: ${s.estudio.size - s.aciertos}"
                b.primaryFlashcards.text = "Volver al mazo"
                b.primaryFlashcards.setOnClickListener { vm.volver() }
            } else {
                b.statusFlashcards.text = "Tarjeta ${s.indice + 1} de ${s.estudio.size} · ${if (s.invertido) "Reverso → anverso" else "Anverso → reverso"}"
                val pregunta = if (s.invertido) card.reverso else card.frente
                val respuesta = if (s.invertido) card.frente else card.reverso
                b.studyHint.isVisible = true
                val key = "${card.id}:${s.indice}:${s.invertido}"
                if (renderedCard != key) {
                    resetFlip()
                    renderedCard = key
                    renderedRevealed = s.revelada
                    showingBack = s.revelada
                    showFace(pregunta, respuesta, s.invertido)
                } else if (s.revelada && !renderedRevealed) {
                    renderedRevealed = true
                    flip(pregunta, respuesta, s.invertido)
                }
                b.studyCard.isClickable = !flipping
                b.studyCard.isFocusable = true
                b.studyCard.setOnClickListener {
                    if (!flipping) {
                        if (!vm.uiState.value.revelada) vm.revelar()
                        else flip(pregunta, respuesta, s.invertido)
                    }
                }
                b.primaryFlashcards.setText(if (s.revelada) R.string.ui_known else R.string.ui_reveal)
                b.primaryFlashcards.setOnClickListener {
                    if (!flipping) { if (s.revelada) vm.responder(true) else vm.revelar() }
                }
                b.secondaryFlashcards.isVisible = s.revelada
                b.secondaryFlashcards.setText(R.string.ui_practice)
                b.secondaryFlashcards.setOnClickListener { if (!flipping) vm.responder(false) }
                if (s.revelada) {
                    b.primaryFlashcards.backgroundTintList = android.content.res.ColorStateList.valueOf(
                        androidx.core.content.ContextCompat.getColor(requireContext(), R.color.feedback_success))
                    b.primaryFlashcards.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.feedback_on_success))
                    b.secondaryFlashcards.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.md_error))
                }
                b.primaryFlashcards.isEnabled = !flipping && !s.saving && !s.loading
                b.secondaryFlashcards.isEnabled = !flipping && !s.saving && !s.loading

            }
            return
        }
        resetFlip()
        renderedCard = null
        rows = if (s.mazo == null) s.mazos.map { mazo ->
            Pair(mazo.titulo + if (mazo.descripcion.isBlank()) "" else "\n${mazo.descripcion}", { opcionesMazo(mazo) })
        } else s.tarjetas.map { card -> Pair("${card.frente} ↔ ${card.reverso}", { opcionesTarjeta(card) }) }
        adapter.notifyDataSetChanged()
        b.statusFlashcards.text = when {
            s.loading -> "Cargando…"
            rows.isEmpty() && s.mazo == null -> "Crea tu primer mazo. Por ejemplo: Inglés básico."
            rows.isEmpty() -> "Añade una tarjeta: anverso «Perro», reverso «Dog»."
            s.mazo == null -> "${rows.size} mazos · Toca uno para abrirlo o administrarlo."
            else -> "${rows.size} tarjetas · Toca una para editarla."
        }
        b.primaryFlashcards.text = if (s.mazo == null) "Crear mazo" else "Añadir tarjeta"
        b.primaryFlashcards.setOnClickListener { if (s.mazo == null) editarMazo(null) else editarTarjeta(null) }
        b.secondaryFlashcards.isVisible = s.mazo != null
        b.secondaryFlashcards.isEnabled = s.tarjetas.isNotEmpty() && !s.loading && !s.saving
        b.secondaryFlashcards.text = "Estudiar"
        b.secondaryFlashcards.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Sentido del estudio")
                .setItems(arrayOf("Anverso → reverso", "Reverso → anverso")) { _, i -> vm.estudiar(i == 1) }.show()
        }
    }
    private fun opcionesMazo(mazo: Mazo) {
        MaterialAlertDialogBuilder(requireContext()).setTitle(mazo.titulo)
            .setItems(arrayOf("Abrir", "Editar", "Eliminar")) { _, i ->
                when (i) {
                    0 -> vm.abrir(mazo)
                    1 -> editarMazo(mazo)
                    2 -> confirmar("Eliminar mazo", "Se eliminarán también todas sus tarjetas. No se puede deshacer.") { vm.borrarMazo(mazo) }
                }
            }.show()
    }
    private fun opcionesTarjeta(card: Flashcard) {
        MaterialAlertDialogBuilder(requireContext()).setTitle(card.frente)
            .setItems(arrayOf("Editar", "Eliminar")) { _, i ->
                if (i == 0) editarTarjeta(card)
                else confirmar("Eliminar tarjeta", "Esta acción no se puede deshacer.") { vm.borrarTarjeta(card) }
            }.show()
    }
    private fun editarMazo(mazo: Mazo?) {
        if (mazo == null) {
            if (childFragmentManager.findFragmentByTag(CrearMazoBottomSheet.TAG) == null) {
                CrearMazoBottomSheet().show(childFragmentManager, CrearMazoBottomSheet.TAG)
            }
        } else formulario("Mazo", "Nombre", "Descripción (opcional)",
            mazo.titulo, mazo.descripcion, false) { a, b -> vm.guardarMazo(mazo, a, b) }
    }
    private fun editarTarjeta(card: Flashcard?) = formulario("Flashcard", "Anverso (ej. Perro)", "Reverso (ej. Dog)",
        card?.frente.orEmpty(), card?.reverso.orEmpty(), true) { a, b -> vm.guardarTarjeta(card, a, b) }
    private fun formulario(titulo: String, labelA: String, labelB: String, a: String, b: String,
        ambos: Boolean, guardar: (String, String) -> Unit) {
        val form = com.dubalin.app.databinding.DialogFlashcardFormBinding.inflate(layoutInflater)
        form.firstLayout.hint = labelA
        form.secondLayout.hint = labelB
        val first = form.firstInput.apply { setText(a) }
        val second = form.secondInput.apply { setText(b) }
        val dialog = MaterialAlertDialogBuilder(requireContext()).setTitle(titulo).setView(form.root)
            .setNegativeButton(R.string.action_cancel, null).setPositiveButton(R.string.action_save, null).create()
        dialog.setOnShowListener {
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                when {
                    first.text.isNullOrBlank() -> first.error = "Campo obligatorio"
                    ambos && second.text.isNullOrBlank() -> second.error = "Campo obligatorio"
                    else -> { guardar(first.text.toString(), second.text.toString()); dialog.dismiss() }
                }
            }
        }
        dialog.show()
    }
    private fun showFace(question: String, answer: String, inverted: Boolean) {
        val b = binding ?: return
        b.studyText.text = if (showingBack) answer else question
        b.studySide.setText(if (showingBack != inverted) R.string.ui_back else R.string.ui_front)
        b.studyHint.setText(if (renderedRevealed) R.string.ui_rate else R.string.ui_flip)
    }

    private fun flip(question: String, answer: String, inverted: Boolean) {
        val b = binding ?: return
        if (flipping) return
        if (!com.dubalin.app.presentation.ui.learning.Motion.enabled(b.root)) {
            showingBack = !showingBack
            showFace(question, answer, inverted)
            return
        }
        flipping = true
        b.primaryFlashcards.isEnabled = false
        b.secondaryFlashcards.isEnabled = false
        b.studyCard.isClickable = false
        b.studyCard.cameraDistance = 8000 * resources.displayMetrics.density
        b.studyCard.animate().rotationY(90f).setDuration(140)
            .setInterpolator(android.view.animation.AccelerateInterpolator()).withEndAction {
                if (binding !== b) return@withEndAction
                showingBack = !showingBack
                showFace(question, answer, inverted)
                b.studyCard.rotationY = -90f
                b.studyCard.animate().rotationY(0f).setDuration(180)
                    .setInterpolator(android.view.animation.DecelerateInterpolator()).withEndAction {
                        if (binding === b) {
                            flipping = false
                            b.studyCard.isClickable = true
                            b.primaryFlashcards.isEnabled = !vm.uiState.value.saving && !vm.uiState.value.loading
                            b.secondaryFlashcards.isEnabled = b.primaryFlashcards.isEnabled
                        }
                    }.start()
            }.start()
    }

    private fun resetFlip() {
        binding?.studyCard?.animate()?.withEndAction(null)?.cancel()
        binding?.studyCard?.rotationY = 0f
        flipping = false
    }

    private fun confirmar(titulo: String, mensaje: String, action: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext()).setTitle(titulo).setMessage(mensaje)
            .setNegativeButton(R.string.action_cancel, null).setPositiveButton("Confirmar") { _, _ -> action() }.show()
    }
    override fun onDestroyView() {
        resetFlip()
        renderedCard = null
        binding?.listFlashcards?.adapter = null
        binding = null
        super.onDestroyView()
    }
}
