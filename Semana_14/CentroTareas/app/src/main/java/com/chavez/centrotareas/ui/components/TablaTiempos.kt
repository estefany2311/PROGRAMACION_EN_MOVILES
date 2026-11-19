package com.chavez.centrotareas.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chavez.centrotareas.model.EstadisticaModo

//Tabla de comparación de tiempos por modo. Stateless: solo recibe datos. mej4
@Composable
fun TablaTiempos(
    estadisticas: List<EstadisticaModo>,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Comparación de tiempos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Celda("Modo", negrita = true)
                Celda("Veces", negrita = true)
                Celda("Último", negrita = true)
                Celda("Promedio", negrita = true)
            }
            estadisticas.forEach { e ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Celda(e.modo)
                    Celda(e.cantidad.toString())
                    Celda(e.ultimo?.let { "$it ms" } ?: "-")
                    Celda(e.promedio?.let { "$it ms" } ?: "-")
                }
            }
        }
    }
}

@Composable
private fun RowScope.Celda(texto: String, negrita: Boolean = false) {
    Text(
        text = texto,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = if (negrita) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier.weight(1f)
    )
}
