package com.chavez.campusgps.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chavez.campusgps.model.Lugar
import com.chavez.campusgps.model.LugarConDistancia
import com.chavez.campusgps.util.formatearDistancia
import com.chavez.campusgps.util.formatearMinutosAPie

// Tarjeta de un lugar. No conoce el ViewModel: recibe datos y callbacks. Mej 4
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TarjetaLugar(
    item: LugarConDistancia,
    seleccionada: Boolean,
    onClick: () -> Unit,
    onComoLlegar: (Lugar) -> Unit,
    onFavorito: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (seleccionada) {
                    Modifier.border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        CardDefaults.elevatedShape
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (seleccionada) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            }
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        item.lugar.nombre,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        item.lugar.categoria,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        item.metros?.let { formatearDistancia(it) } ?: "Sin calcular",
                        style = MaterialTheme.typography.titleSmall
                    )
                    IconButton(onClick = onFavorito) {
                        Icon(
                            imageVector = if (item.esFavorito) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = if (item.esFavorito) {
                                "Quitar de favoritos"
                            } else {
                                "Marcar como favorito"
                            },
                            tint = if (item.esFavorito) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            }

            if (item.minutosAPie != null || item.dentroDelRadio) {
                FlowRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item.minutosAPie?.let { minutos ->
                        AssistChip(
                            onClick = {},
                            label = { Text(formatearMinutosAPie(minutos)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Place,
                                    contentDescription = "Tiempo caminando",
                                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                                )
                            }
                        )
                    }
                    if (item.dentroDelRadio) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text("Dentro del radio") }
                        )
                    }
                }
            }

            // El botón aparece solo en la tarjeta seleccionada
            if (seleccionada) {
                OutlinedButton(
                    onClick = { onComoLlegar(item.lugar) },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Text("Cómo llegar", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}
