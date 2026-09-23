package com.chavez.NavLab5.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chavez.NavLab5.components.MenuCardOption
import com.chavez.NavLab5.components.PrimaryButton
import com.chavez.NavLab5.navigation.Screen
import com.chavez.NavLab5.ui.theme.BackgroundLavender
import com.chavez.NavLab5.ui.theme.PrimaryPurple
import com.chavez.NavLab5.ui.theme.PurpleDark

@Composable
fun HomeScreen(navController: NavController) {
    // El contenedor principal aplica el degradado completo a toda la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PurpleDark,          // Arriba: Morado oscuro
                        PrimaryPurple,       // Centro: Morado principal
                        BackgroundLavender   // Abajo: Lavanda claro
                    )
                )
            )
    ) {
        // 1. Sección Superior (Texto de Bienvenida)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bienvenido,",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Karla Estefany",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "¿Qué deseas gestionar hoy?",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // 2. Sección Inferior con las tarjetas y el botón
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Opción 1: Directorio de Alumnos
            MenuCardOption(
                title = "Directorio de Alumnos",
                subtitle = "Ver y gestionar estudiantes",
                icon = Icons.Default.Group,
                onClick = { navController.navigate(Screen.Directory.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Opción 2: Mi Perfil Académico
            MenuCardOption(
                title = "Mi Perfil Académico",
                subtitle = "Datos personales y progreso",
                icon = Icons.Default.Person,
                onClick = { navController.navigate(Screen.Profile.route) }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón Cierre de Sesión
            PrimaryButton(
                text = "Cerrar Sesión Segura",
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                containerColor = Color.White,
                contentColor = PrimaryPurple,
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}