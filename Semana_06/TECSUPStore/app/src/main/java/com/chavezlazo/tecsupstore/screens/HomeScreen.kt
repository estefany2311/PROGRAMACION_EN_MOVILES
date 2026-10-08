package com.chavezlazo.tecsupstore.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chavezlazo.tecsupstore.model.Producto
import com.chavezlazo.tecsupstore.components.TarjetaProducto

val listaProductosPrueba = listOf(
    Producto(1, "Audífonos", "S/ 89.00"),
    Producto(2, "Smartwatch", "S/ 199.00"),
    Producto(3, "Funda celular", "S/ 25.00")
)

val categoriasPrueba = listOf("Más vendidos", "Laptops", "Accesorios", "Ofertas")

@Composable
fun HomeScreen(
    favoritos: List<Int> = emptyList(),
    onToggleFavorito: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            items(categoriasPrueba) { categoria ->
                FilterChip(
                    selected = categoria == "Más vendidos",
                    onClick = { },
                    label = { Text(categoria) }
                )
            }
        }

        Text(
            text = "Más vendidos",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(listaProductosPrueba) { producto ->
                TarjetaProducto(
                    producto = producto,
                    esFavorito = producto.id in favoritos,
                    onToggleFavorito = { onToggleFavorito(producto.id) }
                )
            }
        }
    }
}

