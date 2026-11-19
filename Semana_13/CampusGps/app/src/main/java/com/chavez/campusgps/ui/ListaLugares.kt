package com.chavez.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chavez.campusgps.model.Lugar
import com.chavez.campusgps.model.LugarConDistancia
import com.chavez.campusgps.ui.components.TarjetaLugar

// No conoce el ViewModel: recibe la lista, el id seleccionado y callbacks
@Composable
fun ListaLugares(
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    onClick: (Int) -> Unit,
    onComoLlegar: (Lugar) -> Unit,
    onFavorito: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp), // el padding lo pone el panel
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lugares, key = { it.lugar.id }) { item ->
            TarjetaLugar(
                item = item,
                seleccionada = item.lugar.id == seleccionado,
                onClick = { onClick(item.lugar.id) },
                onComoLlegar = onComoLlegar,
                onFavorito = { onFavorito(item.lugar.id) }
            )
        }
    }
}
