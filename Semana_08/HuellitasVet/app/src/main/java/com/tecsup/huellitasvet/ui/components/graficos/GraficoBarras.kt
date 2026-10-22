package com.tecsup.huellitasvet.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Grafico de barras verticales con el valor encima y la etiqueta debajo
@Composable
fun GraficoBarras(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    alto: Dp = 120.dp
) {
    val medidor: TextMeasurer = rememberTextMeasurer()
    val estiloValor = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurface)
    val maximo = (datos.maxOfOrNull { it.valor } ?: 0f).coerceAtLeast(1f)
    Column(modifier) {
        Canvas(Modifier.fillMaxWidth().height(alto)) {
            if (datos.isEmpty()) return@Canvas
            val ancho = size.width / datos.size
            val barra = ancho * 0.45f
            val espacioTexto = 16.dp.toPx()
            val altoUtil = size.height - espacioTexto
            datos.forEachIndexed { i, d ->
                val h = altoUtil * (d.valor / maximo)
                val x = i * ancho + (ancho - barra) / 2
                val y = size.height - h
                drawRoundRect(
                    color = d.color,
                    topLeft = Offset(x, y),
                    size = Size(barra, h),
                    cornerRadius = CornerRadius(6.dp.toPx())
                )
                val texto = medidor.measure(d.valor.toInt().toString(), estiloValor)
                drawText(
                    texto,
                    topLeft = Offset(i * ancho + (ancho - texto.size.width) / 2, y - texto.size.height - 2.dp.toPx())
                )
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            datos.forEach { d ->
                Text(
                    d.etiqueta,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
