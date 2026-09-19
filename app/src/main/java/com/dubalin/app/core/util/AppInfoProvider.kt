package com.dubalin.app.core.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Clase de prueba para el Módulo 0.3 (Hilt base).
 * Al llevar un constructor con @Inject, Hilt la provee automáticamente
 * sin necesidad de declararla en ningún @Module -- sirve para confirmar
 * que el grafo de dependencias resuelve construcciones reales, no solo
 * que las anotaciones @HiltAndroidApp/@AndroidEntryPoint compilan.
 *
 * Se puede eliminar sin riesgo una vez que el Módulo 1 introduzca
 * dependencias reales (Room, Repositories) para probar el mismo flujo.
 */
class AppInfoProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getAppName(): String = context.getString(context.applicationInfo.labelRes)
}
