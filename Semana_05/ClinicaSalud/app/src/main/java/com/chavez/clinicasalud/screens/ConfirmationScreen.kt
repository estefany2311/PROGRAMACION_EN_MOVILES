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
    onVolverInicioClick: () -> Unit,
    onVerMisCitasClick: () -> Unit
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
        // Ícono de check dentro de un círculo verde
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(Color(0xFFE8F5E9), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Éxito",
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Título principal
        Text(
            text = "¡Cita agendada!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nombre del doctor
        Text(
            text = medico.nombre,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        // Fecha y hora
        Text(
            text = "$fecha, $hora",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón morado principal para ir al inicio
        Button(
            onClick = onVolverInicioClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A247B))
        ) {
            Text(
                text = "Ir al inicio",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Enlace / Texto secundario para ver mis citas
        TextButton(
            onClick = onVerMisCitasClick
        ) {
            Text(
                text = "Ver mis citas",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF4A247B)
            )
        }
    }
}