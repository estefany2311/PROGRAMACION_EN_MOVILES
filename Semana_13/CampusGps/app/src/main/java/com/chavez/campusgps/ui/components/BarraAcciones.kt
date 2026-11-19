package com.chavez.campusgps.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// Botón principal (con indicador de progreso mientras busca) y botón "Ir al más cercano".
// No conoce el ViewModel.
@Composable
fun BarraAcciones(
    buscando: Boolean,
    hayMasCercano: Boolean,
    onBuscar: () -> Unit,
    onIrAlMasCercano: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Padding interno reducido para que el texto quepa completo en pantallas
    val paddingBoton = PaddingValues(horizontal = 12.dp, vertical = 10.dp)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onBuscar,
            enabled = !buscando,
            // Más ancho que el botón secundario: "Obtener mi ubicación" es el texto más largo
            modifier = Modifier.weight(1.3f),
            contentPadding = paddingBoton
        ) {
            if (buscando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = LocalContentColor.current
                )
                Text(
                    text = "Buscando…",
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )
            } else {
                Text(
                    text = "Obtener mi ubicación",
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        OutlinedButton(
            onClick = onIrAlMasCercano,
            enabled = hayMasCercano,
            modifier = Modifier.weight(1f),
            contentPadding = paddingBoton
        ) {
            Text(
                text = "Ir al más cercano",
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}