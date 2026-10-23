package com.tecsup.huellitasvet.ui.components.graficos

import androidx.compose.ui.graphics.Color

// Un valor del grafico: el ViewModel convierte los datos de Room a esta forma
data class DatoGrafico(
    val etiqueta: String,
    val valor: Float,
    val color: Color
)
