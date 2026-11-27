package com.chavez.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chavez.murocampus.viewmodel.PostsViewModel
import com.chavez.murocampus.viewmodel.UiState

// La pantalla solo dibuja el estado que le da el ViewModel.
@Composable
fun PantallaLista(
    vm: PostsViewModel,
    onAbrir: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by vm.lista.collectAsStateWithLifecycle()
    val filtro by vm.usuarioFiltro.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        FilaDeChips(seleccionado = filtro, onElegir = { vm.filtrar(it) })

        val contenido = Modifier.weight(1f)
        when (val e = estado) {
            is UiState.Cargando -> Cargando(modifier = contenido)

            is UiState.Error -> ErrorConReintento(
                mensaje = e.mensaje,
                onReintentar = { vm.cargar() },
                modifier = contenido
            )

            is UiState.Exito -> if (e.datos.isEmpty()) {
                Box(modifier = contenido.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Sin publicaciones", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = contenido.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(e.datos, key = { it.id }) { post ->
                        TarjetaPost(
                            usuario = post.userId,
                            titulo = post.title,
                            texto = post.body,
                            onClick = { onAbrir(post.id) }
                        )
                    }
                }
            }
        }
    }
}

// Chips desplazables: Todos (null) y Usuario 1 a Usuario 10.
@Composable
private fun FilaDeChips(
    seleccionado: Int?,
    onElegir: (Int?) -> Unit
) {
    val opciones: List<Int?> = listOf(null) + (1..10).toList()
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(opciones) { userId ->
            FilterChip(
                selected = seleccionado == userId,
                onClick = { onElegir(userId) },
                label = { Text(if (userId == null) "Todos" else "Usuario $userId") }
            )
        }
    }
}
