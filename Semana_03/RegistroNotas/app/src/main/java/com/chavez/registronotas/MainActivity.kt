package com.chavez.registronotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chavez.registronotas.ui.theme.RegistroNotasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroNotasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaRegistroNotas(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaRegistroNotas(modifier: Modifier = Modifier) {

    var notaFundamentos by remember { mutableFloatStateOf(0f) }
    var notaPoo by remember { mutableFloatStateOf(0f) }
    var notaMoviles by remember { mutableFloatStateOf(0f) }
    var notaBd by remember { mutableFloatStateOf(0f) }


    var redondear by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }
    var calculado by remember { mutableStateOf(false) }

    val colorMorado = Color(0xFF6750A4)
    val fondoDegradado = Brush.verticalGradient(
        colors = listOf(Color(0xFFEADDFF), Color(0xFFF6F2FF))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fondoDegradado)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorMorado)
                .padding(vertical = 18.dp, horizontal = 20.dp)
        ) {
            Text(
                text = "Registro de Notas",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Notas del ciclo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Desliza para asignar cada nota (0 a 20)", fontSize = 13.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(12.dp))

            FilaCurso("Fundamentos de Programación", "(20%)", notaFundamentos) { notaFundamentos = it }
            FilaCurso("Programación Orientada a Objetos", "(25%)", notaPoo) { notaPoo = it }
            FilaCurso("Programación en Móviles", "(30%)", notaMoviles) { notaMoviles = it }
            FilaCurso("Base de Datos", "(25%)", notaBd) { notaBd = it }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Redondear promedio final")
                Switch(checked = redondear, onCheckedChange = { redondear = it })
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = confirmado, onCheckedChange = { confirmado = it })
                Text("Confirmo que las notas son correctas")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { calculado = true },
                enabled = confirmado,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("CALCULAR PROMEDIO")
            }
        }
    }
}

@Composable
fun FilaCurso(nombre: String, pesoText: String, nota: Float, onValueChange: (Float) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("$nombre $pesoText", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text("${nota.toInt()}", fontWeight = FontWeight.Bold)
        }
        Slider(
            value = nota,
            onValueChange = onValueChange,
            valueRange = 0f..20f,
            steps = 19
        )
    }
}