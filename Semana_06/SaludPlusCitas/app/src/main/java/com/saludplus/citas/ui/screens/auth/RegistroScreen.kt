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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saludplus.citas.data.model.Usuario
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonPrincipal
import com.saludplus.citas.ui.components.CampoTexto

@Composable
fun RegistroScreen(
    navController: NavController
) {
    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    // Estados de errores
    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorTelefono by remember { mutableStateOf<String?>(null) }
    var errorCorreo by remember { mutableStateOf<String?>(null) }
    var errorContrasena by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Regístrate para agendar tus citas",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo Nombre completo
        CampoTexto(
            valor = nombre,
            alCambiar = {
                nombre = it
                errorNombre = null
            },
            etiqueta = "Nombre completo",
            icono = Icons.Default.Person,
            error = errorNombre
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Teléfono
        CampoTexto(
            valor = telefono,
            alCambiar = {
                telefono = it
                errorTelefono = null
            },
            etiqueta = "Teléfono",
            icono = Icons.Default.Phone,
            teclado = KeyboardType.Phone,
            error = errorTelefono
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Correo (Opcional)
        CampoTexto(
            valor = correo,
            alCambiar = {
                correo = it
                errorCorreo = null
            },
            etiqueta = "Correo (opcional)",
            icono = Icons.Default.Email,
            teclado = KeyboardType.Email,
            error = errorCorreo
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        CampoTexto(
            valor = contrasena,
            alCambiar = {
                contrasena = it
                errorContrasena = null
            },
            etiqueta = "Contraseña",
            icono = Icons.Default.Lock,
            oculto = true,
            teclado = KeyboardType.Password,
            error = errorContrasena
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Registro
        BotonPrincipal(
            texto = "Registrarme",
            onClick = {
                var hayError = false

                // 1. Validar Nombre
                if (nombre.isBlank()) {
                    errorNombre = "El nombre no puede estar vacío"
                    hayError = true
                }

                // 2. Validar Teléfono (9 dígitos numéricos)
                val telLimpio = telefono.trim()
                if (telLimpio.length != 9 || !telLimpio.all { it.isDigit() }) {
                    errorTelefono = "Ingresa un teléfono válido de 9 dígitos"
                    hayError = true
                }

                // 3. Validar Correo (si no está vacío, requiere @)
                val correoLimpio = correo.trim()
                if (correoLimpio.isNotBlank() && !correoLimpio.contains("@")) {
                    errorCorreo = "El correo debe contener @"
                    hayError = true
                }

                // 4. Validar Contraseña (mínimo 6 caracteres)
                if (contrasena.length < 6) {
                    errorContrasena = "La contraseña debe tener al menos 6 caracteres"
                    hayError = true
                }

                // Registrar si todas las validaciones pasaron
                if (!hayError) {
                    val nuevoUsuario = Usuario(
                        nombre = nombre.trim(),
                        telefono = telLimpio,
                        correo = correoLimpio,
                        contrasena = contrasena
                    )

                    val exito = Repositorio.registrarUsuario(nuevoUsuario)
                    if (exito) {
                        navController.navigate(Rutas.LOGIN) {
                            popUpTo(Rutas.REGISTRO) { inclusive = true }
                        }
                    } else {
                        errorTelefono = "Este teléfono ya está registrado"
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Enlace Términos y Condiciones
        Text(
            text = "Términos y Condiciones",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(Rutas.TERMINOS)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Enlace Iniciar sesión
        Text(
            text = "¿Ya tienes cuenta? Iniciar sesión",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                navController.navigate(Rutas.LOGIN)
            }
        )
    }
}