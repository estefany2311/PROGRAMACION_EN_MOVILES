package com.chavez.NavLab5.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chavez.NavLab5.navigation.Screen

// OptIn necesario porque TopAppBar aún requiere confirmación de API experimental en Material3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, itemId: Int) { // Recibe el controlador y el ID seleccionado
    // Scaffold (Barra superior, cuerpo, etc.)
    Scaffold(
        topBar = {
            // Barra superior con el título de la pantalla de detalle
            TopAppBar(
                title = { Text("Detalle del elemento") },
                navigationIcon = {
                    // Botón para retroceder
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Ícono de flecha hacia atrás
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding -> // padding para no solaparse con la barra superior
        // Contenedor vertical para organizar
        Column(
            modifier = Modifier
                .padding(padding) // Aplica el padding del Scaffold
                .padding(24.dp)   // Agrega un margen interno de 24dp a los bordes
        ) {
            // Título principal  concatenado con el itemId
            Text(
                text = "Elemento #$itemId",
                style = MaterialTheme.typography.headlineSmall
            )

            // Espacio vertical de 12dp entre el título y la tarjeta
            Spacer(modifier = Modifier.height(12.dp))

            // Tarjeta contenedora para agrupar la información del argumento
            Card(modifier = Modifier.fillMaxWidth()) {
                // Columna interna con padding de 16dp dentro de la tarjeta
                Column(modifier = Modifier.padding(16.dp)) {
                    // Texto que confirma el ID exacto recibido desde la navegación
                    Text(
                        text = "ID recibido: $itemId",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    // Espacio vertical de 8dp entre los textos de la tarjeta
                    Spacer(modifier = Modifier.height(8.dp))

                    // Texto explicativo que indica de dónde vino el argumento
                    Text(
                        text = "Este valor llegó como argumento tipado Int " +
                                "desde el NavHost.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}