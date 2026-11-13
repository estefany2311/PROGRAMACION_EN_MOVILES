package com.tecsup.valorant.ui.detalle

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tecsup.valorant.domain.model.Item
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.components.BotonCortado
import com.tecsup.valorant.ui.components.Cargando
import com.tecsup.valorant.ui.components.ErrorView
import com.tecsup.valorant.ui.components.FormaIcono
import com.tecsup.valorant.ui.theme.ValoCoral
import com.tecsup.valorant.ui.theme.ValoFondo
import com.tecsup.valorant.ui.theme.ValoLavanda
import com.tecsup.valorant.ui.theme.ValoLinea
import com.tecsup.valorant.ui.theme.ValoMagenta
import com.tecsup.valorant.ui.theme.ValoSuperficie
import com.tecsup.valorant.ui.theme.ValoSuperficieAlta
import com.tecsup.valorant.ui.theme.ValoTexto
import com.tecsup.valorant.ui.theme.ValoTextoSuave
import com.tecsup.valorant.ui.theme.colorDeRol

@Composable
fun DetalleScreen(vm: DetalleViewModel, onVolver: () -> Unit) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    val s = estado
    val item: Item? = if (s is UiState.Exito) s.datos else null

    Surface(color = ValoFondo, contentColor = ValoTexto, modifier = Modifier.fillMaxSize()) {
    Box(Modifier.fillMaxSize()) {
        when (s) {
            is UiState.Cargando -> Cargando()
            is UiState.Error -> ErrorView(s.mensaje, onReintentar = vm::cargar)
            is UiState.Exito -> DetalleContenido(s.datos)
        }

        // Botones flotantes sobre la imagen: volver y favorito
        Row(
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BotonCortado(onClick = onVolver, fondo = ValoFondo.copy(alpha = 0.6f)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = ValoTexto, modifier = Modifier.size(20.dp))
            }
            if (item != null) {
                BotonCortado(onClick = vm::alternarFavorito, fondo = ValoFondo.copy(alpha = 0.6f)) {
                    Icon(
                        if (item.esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        "Favorito",
                        tint = if (item.esFavorito) ValoMagenta else ValoTexto,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
    }
}

@Composable
private fun DetalleContenido(item: Item) {
    val colorRol = colorDeRol(item.rol)

    LazyColumn(
        contentPadding = PaddingValues(bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Retrato sobre un degradado del color del rol
        item {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(
                        Brush.verticalGradient(
                            0f to colorRol.copy(alpha = 0.45f),
                            0.5f to ValoMagenta.copy(alpha = 0.22f),
                            1f to ValoFondo
                        )
                    )
            ) {
                AsyncImage(
                    model = item.retratoUrl ?: item.iconoUrl,
                    contentDescription = item.nombre,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(top = 48.dp)
                )
            }
        }
        // Rol, nombre y línea de degradado
        item {
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "[ ${item.rol.uppercase()} ]",
                    style = MaterialTheme.typography.labelSmall,
                    color = colorRol
                )
                Spacer(Modifier.height(6.dp))
                Text(item.nombre.uppercase(), style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(10.dp))
                Box(
                    Modifier
                        .width(120.dp)
                        .height(2.dp)
                        .background(Brush.horizontalGradient(listOf(ValoCoral, ValoMagenta, ValoLavanda)))
                )
            }
        }
        item {
            Text(
                item.descripcion.ifBlank { "Descripción no disponible sin conexión." },
                style = MaterialTheme.typography.bodyMedium,
                color = ValoTextoSuave,
                lineHeight = 21.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        if (item.habilidades.isNotEmpty()) {
            item {
                Text(
                    "// HABILIDADES",
                    style = MaterialTheme.typography.labelSmall,
                    color = ValoTextoSuave,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
        items(item.habilidades) { h ->
            Surface(
                shape = CutCornerShape(topEnd = 12.dp),
                color = ValoSuperficie,
                border = BorderStroke(1.dp, ValoLinea),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    AsyncImage(
                        model = h.iconoUrl,
                        contentDescription = h.nombre,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(FormaIcono)
                            .background(ValoSuperficieAlta)
                            .padding(6.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            h.nombre.uppercase(),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = colorRol
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            textoConEnfasis(h.descripcion),
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

// La API marca las palabras clave con etiquetas del juego: <whitebold>Estrella</> o <debuffred>atrae</>
// En vez de mostrarlas tal cual, las convertimos en negrita y color
@Composable
private fun textoConEnfasis(texto: String): AnnotatedString {
    val principal = ValoTexto
    val negativo = MaterialTheme.colorScheme.error   // efectos negativos (debuff) en rojo
    val patron = Regex("<([^/>]+)>(.*?)</>", RegexOption.DOT_MATCHES_ALL)
    val sinEtiquetas = Regex("<[^>]*>")

    return buildAnnotatedString {
        var inicio = 0
        for (m in patron.findAll(texto)) {
            append(texto.substring(inicio, m.range.first).replace(sinEtiquetas, ""))
            val color = if (m.groupValues[1].startsWith("debuff")) negativo else principal
            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = color)) {
                append(m.groupValues[2].replace(sinEtiquetas, ""))
            }
            inicio = m.range.last + 1
        }
        append(texto.substring(inicio).replace(sinEtiquetas, ""))
    }
}
