package com.dubalin.app.presentation.ui.autoestudio.flashcards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dubalin.app.R
import com.dubalin.app.databinding.BottomSheetCrearMazoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/** El resultado sobrevive a la recreación del fragmento sin capturar callbacks. */
class CrearMazoBottomSheet : BottomSheetDialogFragment() {
    private var binding: BottomSheetCrearMazoBinding? = null
    override fun getTheme() = R.style.Widget_Dubalin_BottomSheetDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        return BottomSheetCrearMazoBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = requireNotNull(binding)
        b.btnCancelar.setOnClickListener { dismiss() }
        b.btnGuardar.setOnClickListener {
            val nombre = b.etNombre.text?.toString()?.trim().orEmpty()
            if (nombre.isBlank()) {
                b.tilNombre.error = getString(R.string.deck_name_required)
                b.etNombre.requestFocus()
            } else {
                b.tilNombre.error = null
                b.btnGuardar.isEnabled = false
                parentFragmentManager.setFragmentResult(RESULT, Bundle().apply {
                    putString("nombre", nombre)
                    putString("descripcion", b.etDescripcion.text?.toString()?.trim().orEmpty())
                })
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "crear_mazo"
        const val RESULT = "mazo_creado"
    }
}
