package com.dubalin.app.presentation.ui.study

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentDailyWordBinding
import com.dubalin.app.domain.model.DailyWord
import com.dubalin.app.domain.model.LetterMark
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyWordFragment : Fragment(R.layout.fragment_daily_word) {
    private val model: DailyWordViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = FragmentDailyWordBinding.bind(view)
        b.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        b.submit.setOnClickListener { model.guess(b.input.text.toString()); b.input.setText("") }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.collect { s ->
                    b.rows.removeAllViews()
                    repeat(6) { i ->
                        val row = layoutInflater.inflate(R.layout.item_word_row, b.rows, false) as TextView
                        val guess = s.guesses.getOrNull(i)
                        row.text = if(guess == null) "□  □  □  □  □" else {
                            val marks = DailyWord.evaluate(DailyWord.answer(s.day), guess)
                            guess.indices.joinToString("  ") { index -> "${guess[index]}${when(marks[index]) { LetterMark.EXACT -> "✓"; LetterMark.PRESENT -> "↔"; else -> "—" }}" }
                        }
                        b.rows.addView(row)
                    }
                    b.status.text = s.message.ifEmpty { "Intento ${minOf(s.guesses.size + 1, 6)} de 6" }
                    b.submit.isEnabled = !s.finished && !s.busy
                    b.input.isEnabled = !s.finished && !s.busy
                }
            }
        }
    }
}
