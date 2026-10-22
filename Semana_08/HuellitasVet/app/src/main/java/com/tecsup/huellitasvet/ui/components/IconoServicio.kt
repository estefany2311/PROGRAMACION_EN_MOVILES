package com.tecsup.huellitasvet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val ICONOS_SERVICIO = listOf("heart", "syringe", "scissors")

fun iconoServicio(icono: String): ImageVector = when (icono) {
    "syringe" -> Icons.Filled.Vaccines
    "scissors" -> Icons.Filled.ContentCut
    else -> Icons.Filled.Favorite
}

// Cuadro redondeado con el icono del servicio
@Composable
fun IconoServicio(icono: String, modifier: Modifier = Modifier, tamano: Dp = 40.dp) {
    Box(
        modifier = modifier
            .size(tamano)
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            iconoServicio(icono),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(tamano * 0.5f)
        )
    }
}
