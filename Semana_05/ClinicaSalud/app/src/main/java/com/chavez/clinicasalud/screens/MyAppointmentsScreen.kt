package com.chavez.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chavez.clinicasalud.data.Cita
import com.chavez.clinicasalud.data.DatosPrueba

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppointmentsScreen(
    onOpenDrawer: () -> Unit
) {
    var citaACancelar by remember { mutableStateOf<Cita?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Citas", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4A247B))
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = DatosPrueba.citasIniciales,
                key = { it.id }
            ) { cita ->
                Card(
                    onClick = {
                        if (cita.estado == "Confirmada") {
                            citaACancelar = cita
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F5F9)),
                    shape = RoundedCornerShape(16.dp)
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
                            Text(text = cita.medicoNombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            if (cita.estado == "Cancelada") {
                                AssistChip(
                                    onClick = { },
                                    label = {
                                        Text(
                                            text = "Cancelada",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = Color(0xFFFFEBEE),
                                        labelColor = Color(0xFFD32F2F)
                                    ),
                                    border = AssistChipDefaults.assistChipBorder(
                                        enabled = true,
                                        borderColor = Color(0xFFFFCDD2)
                                    )
                                )
                            } else {
                                Text(
                                    text = cita.estado,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (cita.estado == "Confirmada") Color(0xFF4CAF50) else Color.Gray
                                )
                            }
                        }
                        Text(text = cita.especialidad, color = Color.Gray, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Fecha: ${cita.fecha}", fontSize = 14.sp)
                    }
                }
            }
        }

        citaACancelar?.let { cita ->
            AlertDialog(
                onDismissRequest = { citaACancelar = null },
                title = { Text(text = "¿Cancelar cita?") },
                text = { Text(text = "¿Estás seguro de que deseas cancelar la cita con ${cita.medicoNombre}?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val index = DatosPrueba.citasIniciales.indexOfFirst { it.id == cita.id }
                            if (index != -1) {
                                DatosPrueba.citasIniciales[index] = cita.copy(estado = "Cancelada")
                            }
                            citaACancelar = null
                        }
                    ) {
                        Text("Sí, cancelar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { citaACancelar = null }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}