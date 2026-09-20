package com.dubalin.app.data.local
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
data class ProfileDetails(val carrera: String = "", val grado: String = "", val intereses: String = "")
/** Datos descriptivos, independientes de las materias y del aprendizaje. */
class ProfileDetailsStore @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences("profile_details", Context.MODE_PRIVATE)
    fun read(id: Int) = ProfileDetails(prefs.getString("$id.carrera", "").orEmpty(),
        prefs.getString("$id.grado", "").orEmpty(), prefs.getString("$id.intereses", "").orEmpty())
    fun save(id: Int, d: ProfileDetails) {
        require(id > 0)
        prefs.edit().putString("$id.carrera", d.carrera.trim()).putString("$id.grado", d.grado.trim())
            .putString("$id.intereses", d.intereses.trim()).apply()
    }
}
