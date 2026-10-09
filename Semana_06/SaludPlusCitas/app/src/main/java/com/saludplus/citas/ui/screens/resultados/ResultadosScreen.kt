package com.saludplus.citas.ui.screens.resultados

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BarraInferior
import com.saludplus.citas.ui.components.BarraSuperior

// Data class propio para la pantalla de resultados
data class Resultado(
    val titulo: String,
    val fecha: String,
    val estado: String,
    val detalle: String
)

@Composable
fun ResultadosScreen(
    navController: NavController
) {
    // Lista fija de resultados dentro de la pantalla
    val listaResultados = listOf(
        Resultado(
            titulo = "Hemograma Completo",
            fecha = "2026-09-28",
            estado = "Disponible",
            detalle = "Valores de hemoglobina y plaquetas dentro del rango normal."
        ),
        Resultado(
            titulo = "Perfil Lipídico",
            fecha = "2026-09-20",
            estado = "Disponible",
            detalle = "Colesterol total en 180 mg/dL. Triglicéridos óptimos."
        ),
        Resultado(
            titulo = "Radiografía de Tórax",
            fecha = "2026-08-15",
            estado = "Archivado",
            detalle = "Campos pulmonares claros sin alteraciones visibles."
        ),
        Resultado(
            titulo = "Examen de Orina Completo",
            fecha = "2026-07-10",
            estado = "Disponible",
            detalle = "Sin presencia de infecciones ni anomalías bioquímicas."
        )
    )

    Scaffold(
        topBar = {
            BarraSuperior(
                titulo = "Resultados Médicos",
                onVolver = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BarraInferior(
                rutaActual = Rutas.RESULTADOS,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                items(listaResultados) { res ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Assignment,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = res.titulo,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Text(
                                    text = res.estado,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Fecha: ${res.fecha}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = res.detalle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}