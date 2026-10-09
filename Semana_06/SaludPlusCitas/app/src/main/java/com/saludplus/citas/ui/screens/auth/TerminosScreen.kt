package com.saludplus.citas.ui.screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saludplus.citas.ui.components.BarraSuperior

@Composable
fun TerminosScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            BarraSuperior(
                titulo = "Términos y Condiciones",
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
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Entendido")
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
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Términos de Servicio y Privacidad",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "1. Uso de la Plataforma:\nAl utilizar SaludPlus, el usuario acepta que los datos proporcionados son verídicos y que el servicio de agendamiento depende de la disponibilidad asignada por la red médica asociada.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "2. Gestión de Citas y Cancelaciones:\nLas citas pueden ser agendadas y canceladas de manera directa en la plataforma. Es responsabilidad del paciente cancelar con anticipación razonable cualquier cita a la que no pueda asistir.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "3. Proteccion de Datos Personales:\nSaludPlus garantiza la confidencialidad de la información médica e historial clínico recopilado, asegurando que solo el personal autorizado tendrá acceso a los registros de agendamiento.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "4. Limitaciones del Servicio:\nEsta aplicación funciona como intermediario de reservas informativas y no sustituye de ninguna forma el servicio de urgencias médicas inmediatas ni ambulancias.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "5. Actualizaciones de Términos:\nNos reservamos el derecho de modificar los presentes términos en cualquier momento para mantener la conformidad con la normativa médica y digital vigente.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}