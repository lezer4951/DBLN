package com.dubalin.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase Application raíz de Dubalin.
 * @HiltAndroidApp genera el contenedor de dependencias a nivel de aplicación
 * y es el punto de entrada obligatorio para que Hilt funcione en Activities,
 * Fragments, ViewModels y Workers de todo el proyecto.
 */
@HiltAndroidApp
class DubalinApplication : Application()
