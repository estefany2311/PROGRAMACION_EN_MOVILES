package com.chavez.NavLab5.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.chavez.NavLab5.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    // Genera una lista de 8 elementos con el formato "Elemento número 1", "Elemento número 2", etc.
    val items = (1..8).map { "Elemento número $it" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista") }, // Título de la barra superior
                navigationIcon = {
                    // Botón para retroceder a la pantalla anterior
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        // LazyColumn optimiza el rendimiento al renderizar solo los elementos visibles en pantalla
        LazyColumn(contentPadding = padding) {
            items(items.size) { index ->
                // Componente Material3 para mostrar cada fila de la lista de manera estándar
                ListItem(
                    headlineContent = { Text(items[index]) }, // Texto principal del elemento
                    supportingContent = { Text("Toca para ver el detalle") }, // Subtexto descriptivo
                    modifier = Modifier.clickable {
                        // Navega hacia DetailScreen pasando el ID (índice + 1)
                        navController.navigate(
                            Screen.Detail.createRoute(index + 1)
                        )
                    }
                )
                // Línea divisoria horizontal entre cada elemento de la lista
                HorizontalDivider()
            }
        }
    }
}