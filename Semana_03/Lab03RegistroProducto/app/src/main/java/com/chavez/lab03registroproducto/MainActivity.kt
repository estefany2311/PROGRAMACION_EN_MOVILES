package com.chavez.lab03registroproducto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chavez.lab03registroproducto.ui.theme.Lab03RegistroProductoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab03RegistroProductoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaRegistro(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaRegistro(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var mostrarResumen by remember { mutableStateOf(false) }

    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val precioEsNumero = precio.toDoubleOrNull() != null
                    val cantidadEsNumero = cantidad.toIntOrNull() != null

                    // Correcciones manuales B2: Manejo de vacíos y conversión de tipos
                    if (nombre.isBlank() || precio.isBlank() || cantidad.isBlank()) {
                        mensajeError = "¡Atención! Debe llenar todos los campos antes de continuar."
                        mostrarResumen = false
                    } else if (!precioEsNumero) {
                        mensajeError = "Error: El precio debe ser un número válido."
                        mostrarResumen = false
                    } else if (!cantidadEsNumero) {
                        mensajeError = "Error: La cantidad debe ser un número entero."
                        mostrarResumen = false
                    } else {
                        mensajeError = ""
                        mostrarResumen = true
                    }
                }
            ) {
                Text("AGREGAR")
            }

            OutlinedButton(
                onClick = {
                    nombre = ""
                    precio = ""
                    cantidad = ""
                    mensajeError = ""
                    mostrarResumen = false
                }
            ) {
                Text("LIMPIAR FORMULARIO")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mostrarResumen) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Producto: $nombre", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Precio: $precio")
                    Text(text = "Cantidad: $cantidad")
                }
            }
        }
    }
}