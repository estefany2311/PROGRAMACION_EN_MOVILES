package com.chavez.tecsupfit.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chavez.tecsupfit.data.DatosPruebaFit

@Composable
fun ConfirmationScreen(
    claseId: Int,
    onVerReservasClick: () -> Unit
) {
    val clase = DatosPruebaFit.clasesDisponibles.find { it.id == claseId }
        ?: DatosPruebaFit.clasesDisponibles.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icono circular verde con Check
        Surface(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color = Color(0xFFE0F2F1)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color(0xFF00695C)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Cupo reservado!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = clase.nombre,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Text(
            text = "Hoy, ${clase.hora} · ${clase.sala}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón gris simple para redirigir a Mis Reservas
        Button(
            onClick = onVerReservasClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE0E0E0),
                contentColor = Color.DarkGray
            )
        ) {
            Text(
                text = "Ver mis reservas",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}