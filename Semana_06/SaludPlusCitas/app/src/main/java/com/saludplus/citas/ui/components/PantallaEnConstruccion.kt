package com.saludplus.citas.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Botón temporal para navegar mientras la pantalla real no está hecha
class BotonTemporal(val texto: String, val accion: () -> Unit)

@Composable
fun PantallaEnConstruccion(
    nombre: String,
    botones: List<BotonTemporal> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = nombre, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Pantalla en construcción", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(24.dp))
        botones.forEach { boton ->
            Button(
                onClick = boton.accion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(boton.texto)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
