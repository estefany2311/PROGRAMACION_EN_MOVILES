package com.chavez.avisoscampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chavez.avisoscampus.model.Aviso
import com.chavez.avisoscampus.model.Origen

// No conoce el ViewModel: solo recibe la lista. Column + forEach (no LazyColumn),
// porque la pantalla ya tiene scroll vertical.
@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    TarjetaFuncion("Historial") {
        if (avisos.isEmpty()) {
            Text("Sin avisos todavía", style = MaterialTheme.typography.bodyMedium)
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                avisos.take(20).forEach { aviso -> FilaAviso(aviso) }
            }
        }
    }
}

@Composable
private fun FilaAviso(aviso: Aviso) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EtiquetaOrigen(aviso.origen)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    aviso.titulo,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    aviso.texto,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(aviso.hora, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun EtiquetaOrigen(origen: Origen) {
    val colores = MaterialTheme.colorScheme
    val (fondo, contenido) = when (origen) {
        Origen.BOTON -> colores.primary to colores.onPrimary
        Origen.RECORDATORIO -> colores.tertiary to colores.onTertiary
        Origen.MODO_AVION -> colores.secondary to colores.onSecondary
    }
    Surface(color = fondo, contentColor = contenido, shape = MaterialTheme.shapes.extraSmall) {
        Text(
            origen.texto,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}
