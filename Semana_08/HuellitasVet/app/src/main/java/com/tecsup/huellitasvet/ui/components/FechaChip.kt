package com.tecsup.huellitasvet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Selector de fecha: dia de la semana arriba y numero abajo
@Composable
fun FechaChip(
    diaSemana: String,
    numero: Int,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        modifier = modifier.width(54.dp),
        shape = RoundedCornerShape(14.dp),
        color = if (seleccionado) cs.primary else cs.surface,
        contentColor = if (seleccionado) cs.onPrimary else cs.onSurface,
        border = if (seleccionado) null else BorderStroke(1.dp, cs.outline)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(diaSemana, style = MaterialTheme.typography.labelSmall)
            Text(numero.toString(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}
