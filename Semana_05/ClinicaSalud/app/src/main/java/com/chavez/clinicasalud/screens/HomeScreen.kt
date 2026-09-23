package com.chavez.clinicasalud.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chavez.clinicasalud.data.DatosPrueba
import com.chavez.clinicasalud.data.Medico

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMedicoClick: (Int) -> Unit,
    onOpenDrawer: () -> Unit
) {
    var especialidadSeleccionada by remember { mutableStateOf<String?>(null) }

    val especialidades = listOf("Cardiología", "Pediatría", "Dermatología")

    // Filtrado dinámico de médicos
    val medicosFiltrados = if (especialidadSeleccionada == null) {
        DatosPrueba.medicos
    } else {
        DatosPrueba.medicos.filter {
            it.especialidad.equals(especialidadSeleccionada, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Clínica Salud+",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Hola, Juan",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4A148C)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Chips de especialidades (LazyRow)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(especialidades) { especialidad ->
                    val isSelected = especialidadSeleccionada == especialidad
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            // Si se vuelve a tocar el mismo, se deselecciona y muestra todos
                            especialidadSeleccionada = if (isSelected) null else especialidad
                        },
                        label = { Text(text = especialidad) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4A148C),
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFFF0F0F0),
                            labelColor = Color.DarkGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Médicos disponibles",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Lista filtrada (LazyColumn)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(medicosFiltrados) { medico ->
                    MedicoItemCard(
                        medico = medico,
                        onClick = { onMedicoClick(medico.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun MedicoItemCard(
    medico: Medico,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = Color(0xFFEDE7F6)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFF4A148C)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medico.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )
                Text(
                    text = medico.especialidad,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Calificación",
                    tint = Color(0xFFFFB300),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = medico.calificacion.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}