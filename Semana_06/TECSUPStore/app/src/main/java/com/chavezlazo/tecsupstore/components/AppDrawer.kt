package com.chavezlazo.tecsupstore.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// Modelo para cada ítem de la lista
data class DrawerItemData(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
)

// NavigationDrawer con sus íconos
val listaItemsDrawer = listOf(
    DrawerItemData("inicio", "Inicio", Icons.Default.Home),
    DrawerItemData("pedidos", "Mis pedidos", Icons.Default.ShoppingBag),
    DrawerItemData("favoritos", "Favoritos", Icons.Default.Favorite),
    DrawerItemData("perfil", "Perfil", Icons.Default.Person),
    DrawerItemData("logout", "Cerrar sesión", Icons.Default.ExitToApp)
)

@Composable
fun AppDrawerContent(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    cantidadFavoritos: Int = 0
) {
    ModalDrawerSheet {
        // Encabezado de Usuario
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "KC",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Karla Chavez",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "karla@tecsup.edu.pe",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))

        // Renderizado de los ítems con ícono
        listaItemsDrawer.forEach { item ->
            val isSelected = currentRoute == item.ruta
            NavigationDrawerItem(
                label = { Text(item.titulo) },
                icon = {
                    Icon(
                        imageVector = item.icono,
                        contentDescription = item.titulo
                    )
                },
                selected = isSelected,
                onClick = { onNavigate(item.ruta) },
                badge = {
                    if (item.ruta == "favoritos" && cantidadFavoritos > 0) {
                        Badge { Text(cantidadFavoritos.toString()) }
                    }
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}