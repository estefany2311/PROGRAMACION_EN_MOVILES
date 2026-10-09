package com.saludplus.citas.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonPrincipal
import com.saludplus.citas.ui.components.CampoTexto

@Composable
fun LoginScreen(
    navController: NavController
) {
    var telefono by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorGeneral by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bienvenido de nuevo a SaludPlus",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo Teléfono
        CampoTexto(
            valor = telefono,
            alCambiar = {
                telefono = it
                errorGeneral = null
            },
            etiqueta = "Teléfono",
            icono = Icons.Default.Phone,
            teclado = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        CampoTexto(
            valor = contrasena,
            alCambiar = {
                contrasena = it
                errorGeneral = null
            },
            etiqueta = "Contraseña",
            icono = Icons.Default.Lock,
            oculto = true,
            teclado = KeyboardType.Password,
            error = errorGeneral
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Ingresar
        BotonPrincipal(
            texto = "Ingresar",
            onClick = {
                val exito = Repositorio.iniciarSesion(telefono, contrasena)
                if (exito) {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.SPLASH) { inclusive = true }
                    }
                } else {
                    errorGeneral = "Teléfono o contraseña incorrectos"
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Enlace a Registro
        Text(
            text = "¿No tienes cuenta? Crear cuenta",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                navController.navigate(Rutas.REGISTRO)
            }
        )
    }
}