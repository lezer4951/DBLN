package com.dubalin.app.presentation.ui.study

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentShortLessonBinding
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShortLessonFragment : Fragment(R.layout.fragment_short_lesson) {
    private val model: LearningViewModel by viewModels()
    private var binding: FragmentShortLessonBinding? = null
    private var renderedPage = -1
    private var choice: Int? = null
    private var selectedLeft: Int? = null
    private val speech = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val text = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
        if (result.resultCode == Activity.RESULT_OK && text != null && model.state.value.page == 4) {
            binding?.input?.setText(text)
            model.verify(text, null)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentShortLessonBinding.bind(view); binding = b
        b.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        b.toolbar.title = model.lesson?.title
        b.speak.setOnClickListener {
            try {
                speech.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, model.lesson?.language)
                    putExtra(RecognizerIntent.EXTRA_PROMPT, model.lesson?.spoken)
                })
            } catch (_: ActivityNotFoundException) {
                b.feedback.text = "El reconocimiento de voz no está disponible. Puedes escribir la frase."
                b.feedback.isVisible = true
            }
        }
        b.action.setOnClickListener {
            val s = model.state.value
            when {
                s.page == 6 -> findNavController().navigateUp()
                s.page < 2 || s.correct -> model.advance()
                else -> model.verify(b.input.text?.toString().orEmpty(), choice)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { model.state.collect(::render) }
        }
    }
    private fun render(s: LessonScreen) {
        val b = binding ?: return; val l = model.lesson ?: return
        val activity = l.activityAt(s.page)
        if (s.page != renderedPage) {
            renderedPage = s.page; choice = null; selectedLeft = null
            b.input.setText(""); b.options.removeAllViews(); b.matches.removeAllViews()
            b.scroll.scrollTo(0, 0)
            val q = when { s.page == 5 -> l.questions.last(); s.page == 4 && l.spoken == null -> l.questions.first(); else -> null }
            if (q != null) q.options.indices.shuffled(kotlin.random.Random(l.id.hashCode() + s.page)).forEach { index ->
                val text = q.options[index]
                val option = layoutInflater.inflate(R.layout.item_short_option, b.options, false) as RadioButton
                option.id = View.generateViewId(); option.text = text
                option.setOnClickListener { choice = index }
                b.options.addView(option)
            }
        }
        b.progress.setProgressCompat(s.page * 100 / 6, true)
        b.kind.text = listOf("CONCEPTO", "EJEMPLO", "COMPLETA", "RELACIONA", "PRACTICA", "COMPRUEBA", "COMPLETADA")[activity]
        b.title.text = when(activity) { 0 -> l.title; 1 -> "Observa la aplicación"; 2 -> "Completa la frase"; 3 -> "Relaciona cada par"; 4 -> if(l.spoken != null) "Practica en voz alta" else "Aplica lo aprendido"; 5 -> "Última pregunta"; else -> "Lección completada" }
        b.body.text = when(activity) {
            0 -> l.theory
            1 -> l.example
            2 -> l.gapSentence
            3 -> "Selecciona un concepto y después su definición o respuesta."
            4 -> l.spoken?.let { "Di: «$it». También puedes escribirlo. Se comprueba la frase reconocida, no se califica el acento." } ?: l.questions.first().prompt
            5 -> l.questions.last().prompt
            else -> "Tu estudio de hoy está registrado. Racha: ${model.store.streak(model.user)} días. Puedes continuar o volver después."
        }
        b.inputLayout.isVisible = activity == 2 || (s.page == 4 && l.spoken != null)
        b.speak.isVisible = s.page == 4 && l.spoken != null
        b.options.isVisible = s.page == 5 || (s.page == 4 && l.spoken == null)
        b.matches.isVisible = activity == 3
        if(activity == 3) renderPairs(s)
        b.visual.isVisible = s.page == 1 && l.visual != null
        when(l.visual) {
            "solar" -> { b.visual.setImageResource(R.drawable.study_solar); b.visual.contentDescription = "Esquema sin escala: el Sol emite luz y la Tierra orbita a su alrededor." }
            "rectangle" -> { b.visual.setImageResource(R.drawable.study_rectangle); b.visual.contentDescription = "Rectángulo dividido en cuatro columnas y tres filas: doce cuadrados de un centímetro cuadrado." }
        }
        b.feedback.isVisible = s.feedback.isNotEmpty(); b.feedback.text = s.feedback
        b.action.isEnabled = s.ready && !s.busy && (activity != 3 || s.correct)
        b.action.text = when { s.busy -> "Guardando…"; s.page == 6 -> "Volver a mis lecciones"; s.page == 5 && s.correct -> "Completar lección"; s.page < 2 || s.correct -> "Continuar"; else -> "Comprobar" }
    }
    private fun renderPairs(s: LessonScreen) {
        val b = binding ?: return; val l = model.lesson ?: return
        b.matches.removeAllViews()
        l.pairs.indices.forEach { index ->
            val button = layoutInflater.inflate(R.layout.item_study_button, b.matches, false) as MaterialButton
            button.text = if(index in s.matched) "✓ ${l.pairs[index].first}" else l.pairs[index].first
            button.isEnabled = index !in s.matched
            button.strokeWidth = if(index == selectedLeft) 5 else 1
            button.setOnClickListener { selectedLeft = index; renderPairs(model.state.value) }
            b.matches.addView(button)
        }
        l.pairs.indices.shuffled(kotlin.random.Random(l.id.hashCode())).forEach { index ->
            val button = layoutInflater.inflate(R.layout.item_study_button, b.matches, false) as MaterialButton
            button.text = if(index in s.matched) "✓ ${l.pairs[index].second}" else l.pairs[index].second
            button.isEnabled = index !in s.matched && selectedLeft != null
            button.setOnClickListener { selectedLeft?.let { left -> selectedLeft = null; model.match(left, index) } }
            b.matches.addView(button)
        }
    }
    override fun onDestroyView() { binding = null; renderedPage = -1; super.onDestroyView() }
}
