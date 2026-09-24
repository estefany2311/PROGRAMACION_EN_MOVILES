package com.chavez.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chavez.tecsupfit.data.DatosPruebaFit

@Composable
fun HomeScreen(
    onClaseClick: (Int) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("Hoy") }
    val filtros = listOf("Hoy", "Esta semana")

    // RF08: Estado reactivo para la búsqueda dinámicas de clases
    var searchQuery by remember { mutableStateOf("") }

    // RF08: Lista filtrada reactivamente evaluando nombre o sala de la clase
    val clasesFiltradas = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            DatosPruebaFit.clasesDisponibles
        } else {
            DatosPruebaFit.clasesDisponibles.filter { clase ->
                clase.nombre.contains(searchQuery, ignoreCase = true) ||
                        clase.sala.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // TopBar verde corporativo estilo TECSUP Fit
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF00695C))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Column {
                Text(
                    text = "TECSUP Fit",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Hola, Karla",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // RF07: Banner "Tu Próxima Clase" antes del filtro de listas
        ProximaClaseBanner(
            modifier = Modifier.padding(horizontal = 16.dp),
            onBannerClick = { onClaseClick(1) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Requisito: LazyRow con chips de filtro
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filtros) { filtro ->
                FilterChip(
                    selected = selectedFilter == filtro,
                    onClick = { selectedFilter = filtro },
                    label = { Text(filtro) },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF00695C),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFE0E0E0),
                        labelColor = Color.Black
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Clases disponibles",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // RF08: Buscador dinámico en tiempo real justo antes de la lista de clases
        OutlinedTextField(
            value = searchQuery, // RF08: Valor enlazado al estado de búsqueda
            onValueChange = { searchQuery = it }, // RF08: Actualiza el estado reactivo
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // RF08: Ajuste de márgenes laterales
            placeholder = { Text("Buscar clase o disciplina...") }, // RF08: Placeholder indicado
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                ) // RF08: Ícono al inicio
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) { // RF08: Acción para limpiar texto
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Limpiar texto"
                        ) // RF08: Ícono de limpiar al final
                    }
                }
            },
            shape = RoundedCornerShape(16.dp), // RF08: Bordes redondeados de 16.dp
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00695C),
                unfocusedBorderColor = Color(0xFFCCCCCC),
                focusedContainerColor = Color(0xFFF9F9F9),
                unfocusedContainerColor = Color(0xFFF9F9F9)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Requisito: LazyColumn con lista de clases filtradas dinámicamente
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(clasesFiltradas) { clase -> // RF08: Muestra las clases filtradas por nombre o sala
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClaseClick(clase.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFE0F2F1)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.FitnessCenter,
                                    contentDescription = null,
                                    tint = Color(0xFF00695C)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = clase.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${clase.hora} · ${clase.sala}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Componente RF07: Banner "Tu Próxima Clase"
 */
@Composable
fun ProximaClaseBanner(
    modifier: Modifier = Modifier,
    onBannerClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onBannerClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF00695C), // Inicio: #00695C
                            Color(0xFF004D40)  // Final: #004D40
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Column {
                // Etiqueta pequeña
                Surface(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "TU PRÓXIMA CLASE",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Nombre de la clase e ícono decorativo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cross Training",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White.copy(alpha = 0.15f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Horario acompañado del ícono Icons.Default.Schedule
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Horario",
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Hoy, 6:00 pm · Sala 1",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}