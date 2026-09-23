package com.chavez.NavLab5.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chavez.NavLab5.components.AcademicTextField
import com.chavez.NavLab5.components.PrimaryButton
import com.chavez.NavLab5.navigation.Screen
import com.chavez.NavLab5.ui.theme.BackgroundLavender
import com.chavez.NavLab5.ui.theme.PrimaryPurple
import com.chavez.NavLab5.ui.theme.TextMuted

@Composable
fun LoginScreen(navController: NavController) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLavender)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ícono central del Portal
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(PrimaryPurple),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Logo Tecsup",
                        tint = Color.White,
                        modifier = Modifier.size(38.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = "PORTAL ACADÉMICO",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "Ingrese sus credenciales institucionales",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Campo Usuario / Correo
                AcademicTextField(
                    value = usuario,
                    onValueChange = {
                        usuario = it
                        errorMessage = null
                    },
                    label = "Correo o Usuario",
                    leadingIcon = Icons.Default.Email,
                    isError = errorMessage != null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Contraseña
                AcademicTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = null
                    },
                    label = "Contraseña",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    isError = errorMessage != null,
                    errorMessage = errorMessage
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Botón Iniciar Sesión
                PrimaryButton(
                    text = "INICIAR SESIÓN",
                    onClick = {
                        if (usuario.isBlank() || password.isBlank()) {
                            errorMessage = "Por favor ingrese su usuario y contraseña"
                        } else {
                            // Navega a Home y limpia el Login del Backstack
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Enlace de ayuda
                TextButton(onClick = { /* Acción de ayuda opcional */ }) {
                    Text(
                        text = "¿Problemas para ingresar? Soporte Técnico",
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryPurple
                    )
                }
            }
        }
    }
}
