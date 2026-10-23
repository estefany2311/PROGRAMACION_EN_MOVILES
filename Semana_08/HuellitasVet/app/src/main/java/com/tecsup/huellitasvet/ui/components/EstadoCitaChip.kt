package com.tecsup.huellitasvet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.ui.theme.EstadoAtendida
import com.tecsup.huellitasvet.ui.theme.EstadoCancelada
import com.tecsup.huellitasvet.ui.theme.EstadoConfirmada
import com.tecsup.huellitasvet.ui.theme.EstadoPendiente

fun colorEstado(estado: EstadoCita): Color = when (estado) {
    EstadoCita.PENDIENTE -> EstadoPendiente
    EstadoCita.CONFIRMADA -> EstadoConfirmada
    EstadoCita.ATENDIDA -> EstadoAtendida
    EstadoCita.CANCELADA -> EstadoCancelada
}

// Pastilla con el color del estado de la cita
@Composable
fun EstadoCitaChip(estado: EstadoCita, modifier: Modifier = Modifier) {
    val c = colorEstado(estado)
    Text(
        text = estado.etiqueta,
        color = c,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .background(c.copy(alpha = 0.14f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    )
}
