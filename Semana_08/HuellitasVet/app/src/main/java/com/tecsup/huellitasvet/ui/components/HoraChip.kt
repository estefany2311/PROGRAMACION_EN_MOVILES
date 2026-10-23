package com.tecsup.huellitasvet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.tecsup.huellitasvet.ui.theme.GrisOcupado

// Selector de hora. Si esta "ocupado" se ve gris y tachado y no se puede tocar
@Composable
fun HoraChip(
    hora: String,
    seleccionado: Boolean,
    ocupado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        enabled = !ocupado,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = when {
            ocupado -> cs.surfaceVariant
            seleccionado -> cs.primary
            else -> cs.surface
        },
        contentColor = when {
            ocupado -> GrisOcupado
            seleccionado -> cs.onPrimary
            else -> cs.onSurface
        },
        border = if (seleccionado || ocupado) null else BorderStroke(1.dp, cs.outline)
    ) {
        Box(Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
            Text(
                hora,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
                textDecoration = if (ocupado) TextDecoration.LineThrough else null
            )
        }
    }
}
