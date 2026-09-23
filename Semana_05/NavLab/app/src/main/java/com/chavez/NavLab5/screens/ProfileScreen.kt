package com.chavez.NavLab5.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chavez.NavLab5.navigation.Screen

@Composable
fun ProfileScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()       // Ocupa todo el ancho y alto disponible
            .padding(24.dp),     // Aplica un margen interno de 24dp alrededor
        verticalArrangement = Arrangement.Center,       // Centra los elementos de arriba a abajo
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos de izquierda a derecha
    ) {
        // Título principal de la vista de perfil
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        // Espaciador de 8dp entre el título y el nombre del usuario
        Spacer(modifier = Modifier.height(8.dp))

        // Muestra el nombre del usuario con el estilo secundario de la app
        Text(
            text = "Karla Estefany Chavez Lazo",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Espaciador de 32dp antes del botón
        Spacer(modifier = Modifier.height(32.dp))

        // Botón para retornar a la pantalla de inicio
        Button(
            onClick = {
                // Navega hacia la pantalla de Home
                navController.navigate(Screen.Home.route) {
                    // Limpia el back stack — evita apilar múltiples instancias de Home
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth() // El botón abarca todo el ancho
        ) {
            Text("Ir al inicio")
        }
    }
}