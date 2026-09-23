package com.chavez.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chavez.clinicasalud.data.DatosPrueba

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(
    medicoId: Int,
    onBackClick: () -> Unit,
    onConfirmarClick: (medicoId: Int, fecha: String, hora: String) -> Unit
) {
    // Estados para almacenar la fecha y hora seleccionadas por el usuario
    var fechaSeleccionada by remember { mutableStateOf(DatosPrueba.fechasDisponibles[1]) }
    var horaSeleccionada by remember { mutableStateOf(DatosPrueba.horasDisponibles[1]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Agendar cita",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        bottomBar = {
            // Botón inferior para confirmar la selección
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        DatosPrueba.agregarCita(medicoId, fechaSeleccionada, horaSeleccionada)
                        onConfirmarClick(medicoId, fechaSeleccionada, horaSeleccionada)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A247B)
                    )
                ) {
                    Text(
                        text = "Confirmar cita",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            // Sección: Selecciona fecha
            Text(
                text = "Selecciona fecha",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DatosPrueba.fechasDisponibles.forEach { fecha ->
                    val esSeleccionado = fecha == fechaSeleccionada

                    // Separar el día del texto y el número
                    val partes = fecha.split(" ")
                    val diaTexto = partes.getOrNull(0) ?: ""
                    val diaNum = partes.getOrNull(1) ?: ""

                    Card(
                        onClick = { fechaSeleccionada = fecha },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (esSeleccionado) Color(0xFF4A247B) else Color(0xFFF2F2F2)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = diaTexto,
                                fontSize = 12.sp,
                                color = if (esSeleccionado) Color.White.copy(alpha = 0.8f) else Color.Gray
                            )
                            Text(
                                text = diaNum,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (esSeleccionado) Color.White else Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Sección: Selecciona hora
            Text(
                text = "Selecciona hora",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DatosPrueba.horasDisponibles.forEach { hora ->
                    val esSeleccionado = hora == horaSeleccionada

                    Card(
                        onClick = { horaSeleccionada = hora },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (esSeleccionado) Color(0xFF4A247B) else Color(0xFFF2F2F2)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = hora,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (esSeleccionado) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}