package com.tecsup.huellitasvet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.huellitasvet.ui.theme.Rojo

// Un punto de la linea de tiempo del historial clinico
@Composable
fun HistorialItem(
    fecha: String,
    servicio: String,
    diagnostico: String,
    peso: Double?,
    esUltimo: Boolean,
    modifier: Modifier = Modifier,
    tratamiento: String? = null
) {
    val cs = MaterialTheme.colorScheme
    Row(modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier.width(32.dp).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(22.dp).background(cs.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Check, null, tint = cs.onPrimary, modifier = Modifier.size(14.dp))
            }
            if (!esUltimo) {
                Box(Modifier.width(2.dp).weight(1f).background(cs.primary.copy(alpha = 0.35f)))
            }
        }
        Card(
            modifier = Modifier.weight(1f).padding(start = 8.dp, bottom = 12.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = cs.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(servicio, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                    Text(fecha, style = MaterialTheme.typography.labelSmall, color = cs.primary)
                }
                Text(diagnostico, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                if (!tratamiento.isNullOrBlank()) {
                    Text("Tratamiento: $tratamiento", style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                }
                if (peso != null) {
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Favorite, null, tint = Rojo, modifier = Modifier.size(12.dp))
                        Text(" Peso: $peso kg", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
