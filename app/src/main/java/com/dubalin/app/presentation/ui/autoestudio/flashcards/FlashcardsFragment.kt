package com.dubalin.app.presentation.ui.autoestudio.flashcards

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlashcardsFragment : Fragment(R.layout.fragment_flashcards) {
    private var binding: FragmentFlashcardsBinding? = null
    private val vm: FlashcardsViewModel by viewModels()
    private var rows: List<Pair<String, () -> Unit>> = emptyList()
    private val adapter = object : RecyclerView.Adapter<RowHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RowHolder(MaterialButton(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                isAllCaps = false
                minHeight = (56 * resources.displayMetrics.density).toInt()
            })
        override fun getItemCount() = rows.size
        override fun onBindViewHolder(holder: RowHolder, position: Int) {
            val row = rows[position]
            holder.button.text = row.first
            holder.button.setOnClickListener { row.second() }
        }
    }
    private class RowHolder(val button: MaterialButton) : RecyclerView.ViewHolder(button)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentFlashcardsBinding.bind(view)
        binding = b
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
        b.thirdFlashcards.isVisible = false
        b.secondaryFlashcards.isVisible = false
        if (s.estudio != null) {
            val card = s.estudio.getOrNull(s.indice)
            if (card == null) {
                b.statusFlashcards.text = "Sesión terminada"
                b.studyText.text = "Recordaste ${s.aciertos} de ${s.estudio.size} tarjetas.\n\nPor practicar: ${s.estudio.size - s.aciertos}"
                b.primaryFlashcards.text = "Volver al mazo"
                b.primaryFlashcards.setOnClickListener { vm.volver() }
            } else {
                b.statusFlashcards.text = "Tarjeta ${s.indice + 1} de ${s.estudio.size} · ${if (s.invertido) "Reverso → anverso" else "Anverso → reverso"}"
                val pregunta = if (s.invertido) card.reverso else card.frente
                val respuesta = if (s.invertido) card.frente else card.reverso
                b.studyText.text = if (s.revelada) "$pregunta\n\n$respuesta" else pregunta
                b.primaryFlashcards.text = if (s.revelada) "Lo sabía" else "Mostrar respuesta"
                b.primaryFlashcards.setOnClickListener { if (s.revelada) vm.responder(true) else vm.revelar() }
                b.secondaryFlashcards.isVisible = s.revelada
                b.secondaryFlashcards.text = "Necesito practicar"
                b.secondaryFlashcards.setOnClickListener { vm.responder(false) }
            }
            return
        }
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
    private fun editarMazo(mazo: Mazo?) = formulario("Mazo", "Nombre", "Descripción (opcional)",
        mazo?.titulo.orEmpty(), mazo?.descripcion.orEmpty(), false) { a, b -> vm.guardarMazo(mazo, a, b) }
    private fun editarTarjeta(card: Flashcard?) = formulario("Flashcard", "Anverso (ej. Perro)", "Reverso (ej. Dog)",
        card?.frente.orEmpty(), card?.reverso.orEmpty(), true) { a, b -> vm.guardarTarjeta(card, a, b) }
    private fun formulario(titulo: String, labelA: String, labelB: String, a: String, b: String,
        ambos: Boolean, guardar: (String, String) -> Unit) {
        val container = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            val padding = (24 * resources.displayMetrics.density).toInt()
            setPadding(padding, padding, padding, padding)
        }
        fun campo(label: String, texto: String): TextInputEditText {
            val layout = TextInputLayout(requireContext()).apply { hint = label }
            val edit = TextInputEditText(layout.context).apply {
                setText(texto)
                inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
                filters = arrayOf(android.text.InputFilter.LengthFilter(2000))
            }
            layout.addView(edit)
            container.addView(layout)
            return edit
        }
        val first = campo(labelA, a)
        val second = campo(labelB, b)
        val dialog = MaterialAlertDialogBuilder(requireContext()).setTitle(titulo).setView(container)
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
    private fun confirmar(titulo: String, mensaje: String, action: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext()).setTitle(titulo).setMessage(mensaje)
            .setNegativeButton(R.string.action_cancel, null).setPositiveButton("Confirmar") { _, _ -> action() }.show()
    }
    override fun onDestroyView() {
        binding?.listFlashcards?.adapter = null
        binding = null
        super.onDestroyView()
    }
}
