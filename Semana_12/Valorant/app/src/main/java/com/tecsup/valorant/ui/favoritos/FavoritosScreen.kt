package com.tecsup.valorant.ui.favoritos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.components.Cargando
import com.tecsup.valorant.ui.components.ErrorView
import com.tecsup.valorant.ui.components.Vacio
import com.tecsup.valorant.ui.lista.ListaAgentes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    vm: FavoritosViewModel,
    onAbrirDetalle: (String) -> Unit,
    onVolver: () -> Unit
) {
    val estado by vm.estado.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") }
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (val e = estado) {
                is UiState.Cargando -> Cargando()
                is UiState.Error -> ErrorView(e.mensaje, onReintentar = {})
                is UiState.Exito ->
                    if (e.datos.isEmpty()) Vacio("Aún no tienes agentes favoritos.")
                    else ListaAgentes(e.datos, onClick = onAbrirDetalle, onFavorito = vm::alternarFavorito)
            }
        }
    }
}
