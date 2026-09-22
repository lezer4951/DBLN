package com.dubalin.app.presentation.ui.learning.materias

sealed class MateriaListItem {

    data class Header(
        val titulo: String,
        val colorFamilia: Int
    ) : MateriaListItem()

    data class Destacada(
        val nombre: String,
        val descripcion: String,
        val nivelActual: Int,
        val iconRes: Int
    ) : MateriaListItem()

    data class Card(
        val id: String,
        val nombre: String,
        val descripcion: String,
        val disponible: Boolean,
        val iconRes: Int,
        val cardBgColor: Int,
        val chipBgColor: Int
    ) : MateriaListItem()
}
