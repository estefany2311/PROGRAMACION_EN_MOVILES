package com.tecsup.valorant.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.valorant.ui.theme.ValoLinea
import com.tecsup.valorant.ui.theme.ValoSuperficie

// Forma "táctica" de la app: esquinas cortadas en diagonal
val FormaTarjeta = CutCornerShape(topEnd = 16.dp, bottomStart = 16.dp)
val FormaBoton = CutCornerShape(topEnd = 10.dp)
val FormaIcono = CutCornerShape(topEnd = 10.dp, bottomStart = 10.dp)

// Botón de ícono cuadrado con la esquina superior derecha cortada
@Composable
fun BotonCortado(
    onClick: () -> Unit,
    fondo: Color = ValoSuperficie,
    borde: Color = ValoLinea,
    contenido: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(44.dp)
            .clip(FormaBoton)
            .background(fondo, FormaBoton)
            .border(1.dp, borde, FormaBoton)
    ) {
        contenido()
    }
}
