package com.tecsup.huellitasvet.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Grafico de lineas: un punto por dato, con el valor encima y la etiqueta debajo
@Composable
fun GraficoLineas(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    alto: Dp = 170.dp,
    unidad: String = ""
) {
    val medidor = rememberTextMeasurer()
    val color = MaterialTheme.colorScheme.primary
    val fondoPunto = MaterialTheme.colorScheme.surface
    val guia = MaterialTheme.colorScheme.outlineVariant
    val estiloValor = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurface)
    val estiloEtiqueta = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)

    Canvas(modifier.fillMaxWidth().height(alto)) {
        if (datos.isEmpty()) return@Canvas
        val arriba = 26.dp.toPx()
        val abajo = 24.dp.toPx()
        val lado = 28.dp.toPx()
        val altoUtil = size.height - arriba - abajo
        val anchoUtil = size.width - 2 * lado

        // Rango con un margen para que la linea no toque los bordes
        val min = datos.minOf { it.valor }
        val max = datos.maxOf { it.valor }
        val margen = ((max - min) * 0.2f).coerceAtLeast(0.5f)
        val bajo = min - margen
        val rango = (max + margen) - bajo

        val puntos = datos.mapIndexed { i, d ->
            val x = if (datos.size == 1) size.width / 2 else lado + anchoUtil * i / (datos.size - 1)
            val y = arriba + altoUtil * (1f - (d.valor - bajo) / rango)
            Offset(x, y)
        }

        // Lineas guia
        repeat(3) { k ->
            val y = arriba + altoUtil * k / 2f
            drawLine(guia, Offset(0f, y), Offset(size.width, y), strokeWidth = 1.dp.toPx())
        }

        // Area bajo la linea y la linea
        if (puntos.size > 1) {
            val area = Path().apply {
                moveTo(puntos.first().x, arriba + altoUtil)
                puntos.forEach { lineTo(it.x, it.y) }
                lineTo(puntos.last().x, arriba + altoUtil)
                close()
            }
            drawPath(area, Brush.verticalGradient(listOf(color.copy(alpha = 0.25f), color.copy(alpha = 0f))))
            val linea = Path().apply {
                moveTo(puntos.first().x, puntos.first().y)
                puntos.drop(1).forEach { lineTo(it.x, it.y) }
            }
            drawPath(linea, color, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
        }

        // Puntos, valores y etiquetas
        puntos.forEachIndexed { i, p ->
            drawCircle(color, radius = 6.dp.toPx(), center = p)
            drawCircle(fondoPunto, radius = 3.dp.toPx(), center = p)
            val valor = medidor.measure("${datos[i].valor}$unidad", estiloValor)
            drawText(valor, topLeft = Offset(p.x - valor.size.width / 2, p.y - valor.size.height - 8.dp.toPx()))
            val etiqueta = medidor.measure(datos[i].etiqueta, estiloEtiqueta)
            drawText(etiqueta, topLeft = Offset(p.x - etiqueta.size.width / 2, size.height - etiqueta.size.height))
        }
    }
}
