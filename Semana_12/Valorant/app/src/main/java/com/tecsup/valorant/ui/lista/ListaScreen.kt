package com.tecsup.valorant.ui.lista

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tecsup.valorant.domain.model.Item
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.components.BotonCortado
import com.tecsup.valorant.ui.components.Cargando
import com.tecsup.valorant.ui.components.ErrorView
import com.tecsup.valorant.ui.components.FormaIcono
import com.tecsup.valorant.ui.components.FormaTarjeta
import com.tecsup.valorant.ui.components.Vacio
import com.tecsup.valorant.ui.theme.ValoCoral
import com.tecsup.valorant.ui.theme.ValoLavanda
import com.tecsup.valorant.ui.theme.ValoLinea
import com.tecsup.valorant.ui.theme.ValoMagenta
import com.tecsup.valorant.ui.theme.ValoSuperficie
import com.tecsup.valorant.ui.theme.ValoSuperficieAlta
import com.tecsup.valorant.ui.theme.ValoTexto
import com.tecsup.valorant.ui.theme.ValoTextoSuave
import com.tecsup.valorant.ui.theme.ValoTextoTenue
import com.tecsup.valorant.ui.theme.colorDeRol

@Composable
fun ListaScreen(
    vm: ListaViewModel,
    esAdmin: Boolean,
    onAbrirDetalle: (String) -> Unit,
    onFavoritos: () -> Unit,
    onAdmin: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    val aviso by vm.aviso.collectAsStateWithLifecycle()
    val total = (estado as? UiState.Exito)?.datos?.size

    Scaffold(
        topBar = {
            EncabezadoLista(
                total = total,
                esAdmin = esAdmin,
                onFavoritos = onFavoritos,
                onAdmin = onAdmin,
                onCerrarSesion = onCerrarSesion
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                // Brillo magenta suave en la esquina superior derecha
                .drawBehind {
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(ValoMagenta.copy(alpha = 0.14f), Color.Transparent),
                            center = Offset(size.width, 0f),
                            radius = size.width
                        )
                    )
                }
        ) {
            // Aviso de sin conexión
            aviso?.let { texto ->
                Surface(color = MaterialTheme.colorScheme.errorContainer, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        texto,
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            when (val e = estado) {
                is UiState.Cargando -> Cargando()
                is UiState.Error -> ErrorView(e.mensaje, onReintentar = vm::refrescar)
                is UiState.Exito ->
                    if (e.datos.isEmpty()) Vacio("No hay agentes guardados.")
                    else ListaAgentes(e.datos, onClick = onAbrirDetalle, onFavorito = vm::alternarFavorito)
            }
        }
    }
}

@Composable
private fun EncabezadoLista(
    total: Int?,
    esAdmin: Boolean,
    onFavoritos: () -> Unit,
    onAdmin: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(start = 16.dp, end = 12.dp, top = 12.dp, bottom = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "// CATÁLOGO DE AGENTES",
                style = MaterialTheme.typography.labelSmall,
                color = ValoCoral,
                modifier = Modifier.weight(1f)
            )
            BotonCortado(onClick = onFavoritos) {
                Icon(Icons.Filled.Favorite, "Favoritos", tint = ValoTexto, modifier = Modifier.size(20.dp))
            }
            if (esAdmin) {
                Spacer(Modifier.width(6.dp))
                BotonCortado(onClick = onAdmin) {
                    Icon(Icons.Filled.Settings, "Panel Admin", tint = ValoTexto, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(Modifier.width(6.dp))
            BotonCortado(onClick = onCerrarSesion) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, "Cerrar sesión", tint = ValoTexto, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                "AGENTES",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.weight(1f)
            )
            if (total != null) {
                Text(
                    "$total REGISTROS",
                    style = MaterialTheme.typography.labelSmall,
                    color = ValoTextoSuave,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        // Línea con degradado coral → magenta → lavanda
        Box(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    Brush.horizontalGradient(
                        0f to ValoCoral,
                        0.4f to ValoMagenta,
                        0.7f to ValoLavanda,
                        1f to Color.Transparent
                    )
                )
        )
    }
}

@Composable
fun ListaAgentes(lista: List<Item>, onClick: (String) -> Unit, onFavorito: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(lista, key = { _, item -> item.id }) { indice, item ->
            AgenteFila(
                item,
                numero = indice + 1,
                onClick = { onClick(item.id) },
                onFavorito = { onFavorito(item.id) }
            )
        }
    }
}

@Composable
fun AgenteFila(item: Item, numero: Int? = null, onClick: () -> Unit, onFavorito: () -> Unit) {
    val colorRol = colorDeRol(item.rol)
    // Los favoritos llevan borde con degradado; los demás, una línea fina
    val borde = if (item.esFavorito) {
        BorderStroke(1.dp, Brush.linearGradient(listOf(ValoCoral, ValoMagenta, ValoLavanda)))
    } else {
        BorderStroke(1.dp, ValoLinea)
    }

    Surface(
        onClick = onClick,
        shape = FormaTarjeta,
        color = ValoSuperficie,
        border = borde,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(start = 12.dp, end = 4.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (numero != null) {
                Text(
                    numero.toString().padStart(2, '0'),
                    style = MaterialTheme.typography.labelSmall,
                    color = ValoTextoTenue,
                    modifier = Modifier.width(24.dp)
                )
            }
            // Ícono del agente sobre un fondo del color de su rol
            AsyncImage(
                model = item.iconoUrl,
                contentDescription = item.nombre,
                modifier = Modifier
                    .size(60.dp)
                    .clip(FormaIcono)
                    .background(
                        Brush.verticalGradient(listOf(colorRol.copy(alpha = 0.35f), ValoSuperficieAlta))
                    )
                    .padding(4.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(item.nombre.uppercase(), style = MaterialTheme.typography.titleMedium)
                Text(
                    "[ ${item.rol.uppercase()} ]",
                    style = MaterialTheme.typography.labelSmall,
                    color = colorRol
                )
            }
            IconButton(onClick = onFavorito) {
                Icon(
                    imageVector = if (item.esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (item.esFavorito) ValoMagenta else ValoTextoSuave
                )
            }
        }
    }
}
