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
import kotlin.math.roundToInt

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

    var promPonderado by remember { mutableDoubleStateOf(0.0) }
    var promFinalStr by remember { mutableStateOf("") }
    var observacion by remember { mutableStateOf("") }
    var colorChip by remember { mutableStateOf(Color.Unspecified) }

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
            Text(text = "Notas del ciclo", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Desliza para asignar cada nota (0 a 20)", fontSize = 13.sp, color = Color.Gray)

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
                Text(text = "Redondear promedio final", color = Color.Black, fontSize = 15.sp)
                Switch(
                    checked = redondear,
                    onCheckedChange = { redondear = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = colorMorado)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = confirmado,
                    onCheckedChange = { confirmado = it },
                    colors = CheckboxDefaults.colors(checkedColor = colorMorado)
                )
                Text(text = "Confirmo que las notas son correctas", color = Color.Black, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val n1 = notaFundamentos.toInt()
                    val n2 = notaPoo.toInt()
                    val n3 = notaMoviles.toInt()
                    val n4 = notaBd.toInt()

                    val ponderado = (n1 * 0.20) + (n2 * 0.25) + (n3 * 0.30) + (n4 * 0.25)
                    promPonderado = ponderado

                    val valorFinal = if (redondear) ponderado.roundToInt().toDouble() else ponderado
                    promFinalStr = if (redondear) "${ponderado.roundToInt()}" else String.format("%.2f", ponderado)

                    when {
                        valorFinal >= 17.0 -> {
                            observacion = "EXCELENTE"
                            colorChip = Color(0xFF1B5E20)
                        }
                        valorFinal >= 13.0 -> {
                            observacion = "APROBADO"
                            colorChip = Color(0xFF2E7D32)
                        }
                        valorFinal >= 10.0 -> {
                            observacion = "EN RECUPERACIÓN"
                            colorChip = Color(0xFFF57F17)
                        }
                        else -> {
                            observacion = "DESAPROBADO"
                            colorChip = Color(0xFFC62828)
                        }
                    }
                    calculado = true
                },
                enabled = confirmado,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorMorado,
                    disabledContainerColor = Color(0xFFC4C0D0)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "CALCULAR PROMEDIO", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!calculado) {
                Text(
                    text = "Asigna las notas y confirma para calcular",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Promedio ponderado:  ${String.format("%.2f", promPonderado)}",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Promedio final:  $promFinalStr",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorMorado
                            )
                        }
                        if (redondear) {
                            Text(
                                text = "(redondeado)",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            color = colorChip.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = observacion,
                                color = colorChip,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "✓ Promedio calculado correctamente",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Desarrollado por: Karla Chavez",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun FilaCurso(nombre: String, pesoText: String, nota: Float, onValueChange: (Float) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = nombre, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = pesoText, fontSize = 11.sp, color = Color(0xFF9C80B7))
            }
            Surface(
                color = Color(0xFFEADDFF),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${nota.toInt()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFF4F378B),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                )
            }
        }
        Slider(
            value = nota,
            onValueChange = onValueChange,
            valueRange = 0f..20f,
            steps = 19,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF6750A4),
                activeTrackColor = Color(0xFF6750A4)
            )
        )
    }
}