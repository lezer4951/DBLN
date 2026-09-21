package com.dubalin.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentHomeTabBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home_tab) {

    private var _binding: FragmentHomeTabBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeTabBinding.bind(view)

        val openSubjects = View.OnClickListener {
            findNavController().navigate(R.id.autoestudioFragment)
            findNavController().navigate(R.id.materiasFragment)
        }
        binding.btnHomeSubjects.setOnClickListener(openSubjects)
        binding.btnHomeAllSubjects.setOnClickListener(openSubjects)
        binding.homeSubjectAstronomy.setOnClickListener {
            findNavController().navigate(R.id.autoestudioFragment)
            findNavController().navigate(R.id.materiasFragment, Bundle().apply {
                putInt("materiaInicial", 0)
            })
        }
        listOf(binding.homeSubjectMath, binding.homeSubjectSpanish, binding.homeSubjectPhysics)
            .forEach { card ->
                card.isEnabled = false
                card.alpha = 0.58f
            }
        binding.btnHomeFlashcards.setOnClickListener { findNavController().navigate(R.id.autoestudioFragment); findNavController().navigate(R.id.flashcardsFragment) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvSaludo.text = getString(R.string.home_saludo, state.nombre)
                    binding.tvNivelXp.text = getString(R.string.ref_xp, state.xpTotal)
                    binding.tvLevelBadge.text = getString(R.string.ref_level_short, state.nivel)
                    binding.tvRachaDias.text = getString(R.string.ref_days, state.rachaDias)
                    binding.tvStreakTitle.text = resources.getQuantityString(
                        R.plurals.home_racha_dias, state.rachaDias, state.rachaDias)
                    binding.tvStreakDetail.setText(if (state.rachaDias == 0)
                        R.string.ref_study_today else R.string.ref_streak_continue)
                    binding.tvAvatar.text = state.nombre.trim().firstOrNull()?.uppercase() ?: "D"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        renderWeek()
    }

    private fun renderWeek() {
        val b = _binding ?: return
        b.streakWeek.removeAllViews()
        val today = java.util.Calendar.getInstance()
        val day = today.clone() as java.util.Calendar
        val offset = (day.get(java.util.Calendar.DAY_OF_WEEK) + 5) % 7
        day.add(java.util.Calendar.DAY_OF_MONTH, -offset)
        val labels = listOf("L", "M", "M", "J", "V", "S", "D")
        val format = java.text.SimpleDateFormat("EEEE d 'de' MMMM", java.util.Locale("es"))
        repeat(7) { index ->
            val cell = com.dubalin.app.databinding.ItemWeekDayBinding.inflate(layoutInflater, b.streakWeek, false)
            val isToday = day.get(java.util.Calendar.YEAR) == today.get(java.util.Calendar.YEAR) &&
                day.get(java.util.Calendar.DAY_OF_YEAR) == today.get(java.util.Calendar.DAY_OF_YEAR)
            cell.dayLetter.text = labels[index]
            cell.dayNumber.text = day.get(java.util.Calendar.DAY_OF_MONTH).toString()
            cell.dayLetter.setBackgroundResource(if (isToday) R.drawable.bg_circle_streak_active else R.drawable.bg_circle_streak_inactive)
            cell.root.contentDescription = if (isToday) getString(R.string.ref_today_description, format.format(day.time))
                else format.format(day.time)
            b.streakWeek.addView(cell.root)
            day.add(java.util.Calendar.DAY_OF_MONTH, 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
