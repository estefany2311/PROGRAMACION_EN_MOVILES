package com.tecsup.huellitasvet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Boton principal del color del rol. "contorno" = boton secundario
@Composable
fun VetButton(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contorno: Boolean = false,
    color: Color = Color.Unspecified
) {
    val c = if (color == Color.Unspecified) MaterialTheme.colorScheme.primary else color
    val forma = RoundedCornerShape(12.dp)
    val mod = modifier.fillMaxWidth().height(50.dp)
    if (contorno) {
        OutlinedButton(
            onClick = onClick,
            modifier = mod,
            enabled = enabled,
            shape = forma,
            border = BorderStroke(1.5.dp, if (enabled) c else c.copy(alpha = 0.4f)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = c)
        ) { Text(texto, style = MaterialTheme.typography.labelLarge) }
    } else {
        Button(
            onClick = onClick,
            modifier = mod,
            enabled = enabled,
            shape = forma,
            colors = ButtonDefaults.buttonColors(containerColor = c)
        ) { Text(texto, style = MaterialTheme.typography.labelLarge) }
    }
}
