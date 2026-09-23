package com.chavez.clinicasalud.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chavez.clinicasalud.data.DatosPrueba

@Composable
fun ConfirmationScreen(
    medicoId: Int,
    fecha: String,
    hora: String,
    onVolverInicioClick: () -> Unit
) {
    // Buscar el médico correspondiente
    val medico = DatosPrueba.medicos.find { it.id == medicoId } ?: DatosPrueba.medicos[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ícono de confirmación
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color(0xFFE8F5E9), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Éxito",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Cita Confirmada!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A247B)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tu cita con ${medico.nombre} ha sido agendada con éxito.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tarjeta con los detalles de la reserva
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F5F9)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Especialidad: ${medico.especialidad}", fontSize = 14.sp)
                Text(text = "Fecha: $fecha", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text(text = "Hora: $hora", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text(text = "Estado: Confirmada", fontSize = 14.sp, color = Color(0xFF4CAF50))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onVolverInicioClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A247B))
        ) {
            Text(text = "Volver al inicio", fontSize = 16.sp, color = Color.White)
        }
    }
}