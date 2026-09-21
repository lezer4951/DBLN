package com.dubalin.app.presentation.ui.learning.astronomia

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import org.json.JSONObject

/** Borrador local por usuario y nivel, independiente del progreso académico. */
@Singleton
class BorradorSesionStore @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences("borradores_astronomia_v1", Context.MODE_PRIVATE)

    fun ultimoNivel(usuario: Int?): Int? {
        if (usuario == null) return null
        return prefs.getInt("ultimo:$usuario", -1).takeIf { it in 0..10 && leer(usuario, it) != null }
    }

    fun leer(usuario: Int?, nivel: Int): JSONObject? {
        if (usuario == null) return null
        return runCatching { JSONObject(prefs.getString("$usuario:$nivel", null) ?: return null) }.getOrNull()
    }

    fun guardar(usuario: Int?, nivel: Int, tema: Int, fase: FaseSesion, pregunta: Int,
        seleccion: Int?, correcta: Boolean?, explicacion: String) {
        if (usuario == null) return
        val value = JSONObject().put("tema", tema).put("fase", fase.name)
            .put("pregunta", pregunta).put("seleccion", seleccion ?: -1)
            .put("correcta", correcta?.toString() ?: "").put("explicacion", explicacion)
        prefs.edit().putString("$usuario:$nivel", value.toString()).putInt("ultimo:$usuario", nivel).apply()
    }
}
