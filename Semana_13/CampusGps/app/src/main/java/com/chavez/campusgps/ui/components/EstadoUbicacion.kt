package com.chavez.campusgps.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chavez.campusgps.model.LugarConDistancia
import com.chavez.campusgps.model.UbicacionUi
import com.chavez.campusgps.ui.MensajeError
import com.chavez.campusgps.util.formatearDistancia

// Texto de estado (inicial / buscando / coordenadas), tarjeta de error y línea "Más cercano".
// No conoce el ViewModel.
@Composable
fun EstadoUbicacion(
    estado: UbicacionUi,
    masCercano: LugarConDistancia?,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // El padding de 16.dp lo pone el panel que contiene este componente
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        when (estado) {
            UbicacionUi.Inicial -> Text(
                "Pulsa el botón para obtener tu ubicación",
                style = MaterialTheme.typography.bodyMedium
            )
            UbicacionUi.Buscando -> Text(
                "Buscando…",
                style = MaterialTheme.typography.bodyMedium
            )
            is UbicacionUi.Lista -> Text(
                "Lat: ${estado.posicion.latitud}  Lon: ${estado.posicion.longitud}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            is UbicacionUi.Error -> MensajeError(error = estado, onReintentar = onReintentar)
        }

        masCercano?.let { cercano ->
            Text(
                "Más cercano: ${cercano.lugar.nombre} " +
                    "(${cercano.metros?.let { formatearDistancia(it) } ?: "Sin calcular"})",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
