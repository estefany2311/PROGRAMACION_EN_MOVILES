package com.chavez.campusgps.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Fila de chips: "Todos", "Mis lugares" y una por categoría. No conoce el ViewModel.
// seleccionada == null significa "Todos"; "Mis lugares" se combina con la categoría.
@Composable
fun FiltroCategorias(
    categorias: List<String>,
    seleccionada: String?,
    onSeleccionar: (String?) -> Unit,
    soloFavoritos: Boolean,
    onAlternarFavoritos: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Fondo sólido en los chips sin seleccionar para que se lean sobre el mapa
    val colores = FilterChipDefaults.filterChipColors(
        containerColor = MaterialTheme.colorScheme.surface
    )

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = seleccionada == null,
                onClick = { onSeleccionar(null) },
                label = { Text("Todos") },
                colors = colores
            )
        }
        item {
            FilterChip(
                selected = soloFavoritos,
                onClick = onAlternarFavoritos,
                label = { Text("Mis lugares") },
                colors = colores,
                leadingIcon = {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Mis lugares favoritos",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            )
        }
        items(categorias) { categoria ->
            FilterChip(
                selected = categoria == seleccionada,
                onClick = { onSeleccionar(categoria) },
                label = { Text(categoria) },
                colors = colores
            )
        }
    }
}
