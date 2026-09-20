package com.dubalin.app.presentation.ui.idiomas
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubalin.app.R
import com.dubalin.app.databinding.FragmentIdiomasBinding
class IdiomasFragment : Fragment(R.layout.fragment_idiomas) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentIdiomasBinding.bind(view).btnLanguageCards.setOnClickListener {
            findNavController().navigate(R.id.autoestudioFragment); findNavController().navigate(R.id.flashcardsFragment)
        }
    }
}
