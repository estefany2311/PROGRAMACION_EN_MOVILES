package com.saludplus.citas.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BarraInferior
import com.saludplus.citas.ui.components.TarjetaAcceso
import com.saludplus.citas.ui.theme.AzulClaro
import com.saludplus.citas.ui.theme.MoradoSuave
import com.saludplus.citas.ui.theme.NaranjaSuave
import com.saludplus.citas.ui.theme.VerdeSuave

@Composable
fun HomeScreen(
    navController: NavController
) {
    val nombreUsuario = Repositorio.usuarioActual?.nombre
        ?.trim()
        ?.split(" ")
        ?.firstOrNull() ?: "Paciente"

    Scaffold(
        bottomBar = {
            BarraInferior(
                rutaActual = Rutas.HOME,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Saludo y Notificaciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "¡Hola, $nombreUsuario!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "¿Qué deseas hacer hoy?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = { navController.navigate(Rutas.NOTIFICACIONES) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tarjetas de acceso en cuadrícula 2x2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaAcceso(
                    titulo = "Agendar cita",
                    icono = Icons.Default.CalendarMonth,
                    color = AzulClaro,
                    onClick = { navController.navigate(Rutas.ESPECIALIDADES) },
                    modifier = Modifier.weight(1f)
                )
                TarjetaAcceso(
                    titulo = "Mis citas",
                    icono = Icons.Default.DateRange,
                    color = VerdeSuave,
                    onClick = { navController.navigate(Rutas.MIS_CITAS) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaAcceso(
                    titulo = "Mis datos",
                    icono = Icons.Default.Person,
                    color = MoradoSuave,
                    onClick = { navController.navigate(Rutas.PERFIL) },
                    modifier = Modifier.weight(1f)
                )
                TarjetaAcceso(
                    titulo = "Resultados",
                    icono = Icons.Default.Description,
                    color = NaranjaSuave,
                    onClick = { navController.navigate(Rutas.RESULTADOS) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Especialidades destacadas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Especialidades destacadas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(
                    onClick = { navController.navigate(Rutas.ESPECIALIDADES) }
                ) {
                    Text(text = "Ver todas")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(Repositorio.especialidadesDestacadas()) { especialidad ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(88.dp)
                            .clickable {
                                navController.navigate(Rutas.medicos(especialidad.id))
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(AzulClaro, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = especialidad.icono,
                                contentDescription = especialidad.nombre,
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = especialidad.nombre,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}