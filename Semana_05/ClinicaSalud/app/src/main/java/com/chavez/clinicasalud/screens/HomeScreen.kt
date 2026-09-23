package com.chavez.clinicasalud.screens


import androidx.compose.foundation.background
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
    // Estado para filtrar por especialidad seleccionada
    var especialidadSeleccionada by remember { mutableStateOf("Cardiología") }

    // Filtrado de médicos según la especialidad
    val medicosFiltrados = remember(especialidadSeleccionada) {
        DatosPrueba.medicos.filter { it.especialidad == especialidadSeleccionada }
    }

    Scaffold(
        topBar = {
            // Barra superior morada
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Clínica Salud+",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hola, Juan",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
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
                    containerColor = Color(0xFF4A247B) // Morado principal
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Filtros horizontales (LazyRow)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                items(DatosPrueba.especialidades) { especialidad ->
                    val esSeleccionado = especialidad == especialidadSeleccionada
                    FilterChip(
                        selected = esSeleccionado,
                        onClick = { especialidadSeleccionada = especialidad },
                        label = { Text(especialidad) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4A247B),
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFFF0F0F0),
                            labelColor = Color.DarkGray
                        )
                    )
                }
            }

            Text(
                text = "Médicos disponibles",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Lista vertical de médicos (LazyColumn)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(medicosFiltrados) { medico ->
                    TarjetaMedico(
                        medico = medico,
                        onClick = { onMedicoClick(medico.id) }
                    )
                }
            }
        }
    }
}

// Componente para la tarjeta de cada médico
@Composable
fun TarjetaMedico(
    medico: Medico,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F5F9)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono/Avatar del médico
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFE8DDFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xFF4A247B)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información (Nombre y Especialidad)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medico.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = medico.especialidad,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            // Calificación
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Calificación",
                    tint = Color(0xFFFFC107),
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