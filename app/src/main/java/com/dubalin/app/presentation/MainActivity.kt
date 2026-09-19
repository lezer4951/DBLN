package com.dubalin.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dubalin.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity única de la app (patrón single-activity con Navigation Component).
 * Todo el flujo de pantallas -- Splash, Login, Registro, Home -- lo controla
 * el NavHostFragment declarado en activity_main.xml, cargando
 * res/navigation/nav_graph.xml automáticamente. Esta clase no necesita
 * lógica de navegación propia.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
