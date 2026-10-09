package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.theme.AzulClaro

@Composable
fun ConfirmarCitaScreen(
    navController: NavController,
    medicoId: Int,
    fecha: String,
    hora: String
) {
    val medico = Repositorio.obtenerMedico(medicoId)
    val especialidad = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }

    var motivo by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            BarraSuperior(
                titulo = "Confirmar cita",
                onVolver = { navController.popBackStack() }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        val citaAgendada = Repositorio.agendarCita(
                            medicoId = medicoId,
                            fecha = fecha,
                            hora = hora,
                            motivo = motivo
                        )
                        if (citaAgendada != null) {
                            navController.navigate(Rutas.citaExitosa(citaAgendada.id)) {
                                popUpTo(Rutas.HOME) {
                                    inclusive = false
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Agendar cita")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Tarjeta del Médico
            medico?.let { m ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(AzulClaro, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = m.nombre.take(1),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = m.nombre,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = especialidad?.nombre ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "CMP: ${m.cmp}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Detalle de la Cita
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
                    ElementoResumen(
                        icono = Icons.Default.CalendarToday,
                        titulo = "Fecha",
                        valor = fecha
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ElementoResumen(
                        icono = Icons.Default.AccessTime,
                        titulo = "Hora",
                        valor = hora
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ElementoResumen(
                        icono = Icons.Default.MedicalServices,
                        titulo = "Tipo de atención",
                        valor = "Consulta presencial"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ElementoResumen(
                        icono = Icons.Default.LocationOn,
                        titulo = "Local de atención",
                        valor = Repositorio.localSeleccionado.ifBlank {
                            "No se seleccionó un local"
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Campo de motivo opcional
            Text(
                text = "Motivo de consulta (opcional)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = motivo,
                onValueChange = { motivo = it },
                placeholder = { Text("Ej. Consulta de rutina, dolor articular...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ElementoResumen(
    icono: ImageVector,
    titulo: String,
    valor: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}