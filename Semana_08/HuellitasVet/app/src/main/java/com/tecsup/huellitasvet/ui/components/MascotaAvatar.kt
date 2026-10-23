package com.tecsup.huellitasvet.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecsup.huellitasvet.model.Especie

private val FondoPerro = Color(0xFFFDEBD3)
private val FondoGato = Color(0xFFE2E8F0)
private val FondoOtro = Color(0xFFE0F2E9)

// Carita de la mascota dibujada con Canvas (perro, gato u otro animal)
@Composable
fun MascotaAvatar(
    especie: Especie,
    modifier: Modifier = Modifier,
    tamano: Dp = 56.dp,
    conFondo: Boolean = true
) {
    Canvas(modifier = modifier.size(tamano)) {
        val u = size.minDimension / 100f
        if (conFondo) {
            val fondo = when (especie) {
                Especie.PERRO -> FondoPerro
                Especie.GATO -> FondoGato
                Especie.OTRO -> FondoOtro
            }
            drawCircle(fondo, radius = 50 * u, center = Offset(50 * u, 50 * u))
        }
        when (especie) {
            Especie.PERRO -> dibujarPerro(u)
            Especie.GATO -> dibujarGato(u)
            Especie.OTRO -> dibujarConejo(u)
        }
    }
}

private fun DrawScope.p(x: Float, y: Float, u: Float) = Offset(x * u, y * u)

private fun DrawScope.ojos(u: Float, y: Float, sep: Float = 10f) {
    val ojo = Color(0xFF2B2118)
    drawCircle(ojo, 3.8f * u, p(50 - sep, y, u))
    drawCircle(ojo, 3.8f * u, p(50 + sep, y, u))
    drawCircle(Color.White, 1.2f * u, p(51 - sep, y - 1.3f, u))
    drawCircle(Color.White, 1.2f * u, p(51 + sep, y - 1.3f, u))
}

private fun DrawScope.dibujarPerro(u: Float) {
    val oreja = Color(0xFF8B5A2B)
    val cara = Color(0xFFD9A066)
    val hocico = Color(0xFFF6E3C6)
    // Orejas caidas
    rotate(20f, pivot = p(30f, 40f, u)) {
        drawOval(oreja, topLeft = p(20f, 28f, u), size = Size(18 * u, 34 * u))
    }
    rotate(-20f, pivot = p(70f, 40f, u)) {
        drawOval(oreja, topLeft = p(62f, 28f, u), size = Size(18 * u, 34 * u))
    }
    drawCircle(cara, 26 * u, p(50f, 54f, u))
    drawOval(hocico, topLeft = p(36f, 58f, u), size = Size(28 * u, 20 * u))
    ojos(u, 49f)
    drawOval(Color(0xFF2B2118), topLeft = p(44.5f, 59f, u), size = Size(11 * u, 7.5f * u))
    drawLine(Color(0xFF2B2118), p(50f, 66f, u), p(50f, 70f, u), strokeWidth = 1.6f * u)
}

private fun DrawScope.dibujarGato(u: Float) {
    val pelaje = Color(0xFF9CA3AF)
    val interior = Color(0xFFF9A8D4)
    val orejaIzq = Path().apply {
        moveTo(26 * u, 46 * u); lineTo(30 * u, 18 * u); lineTo(48 * u, 34 * u); close()
    }
    val orejaDer = Path().apply {
        moveTo(74 * u, 46 * u); lineTo(70 * u, 18 * u); lineTo(52 * u, 34 * u); close()
    }
    drawPath(orejaIzq, pelaje)
    drawPath(orejaDer, pelaje)
    val internaIzq = Path().apply {
        moveTo(31 * u, 40 * u); lineTo(33 * u, 25 * u); lineTo(42 * u, 33 * u); close()
    }
    val internaDer = Path().apply {
        moveTo(69 * u, 40 * u); lineTo(67 * u, 25 * u); lineTo(58 * u, 33 * u); close()
    }
    drawPath(internaIzq, interior)
    drawPath(internaDer, interior)
    drawCircle(pelaje, 26 * u, p(50f, 56f, u))
    drawOval(Color(0xFFE5E7EB), topLeft = p(39f, 59f, u), size = Size(22 * u, 15 * u))
    ojos(u, 52f)
    val nariz = Path().apply {
        moveTo(46 * u, 60 * u); lineTo(54 * u, 60 * u); lineTo(50 * u, 65 * u); close()
    }
    drawPath(nariz, Color(0xFFEC4899))
    val bigote = Color(0xFF4B5563)
    drawLine(bigote, p(36f, 63f, u), p(22f, 60f, u), strokeWidth = 1.2f * u)
    drawLine(bigote, p(36f, 66f, u), p(22f, 68f, u), strokeWidth = 1.2f * u)
    drawLine(bigote, p(64f, 63f, u), p(78f, 60f, u), strokeWidth = 1.2f * u)
    drawLine(bigote, p(64f, 66f, u), p(78f, 68f, u), strokeWidth = 1.2f * u)
}

private fun DrawScope.dibujarConejo(u: Float) {
    val pelaje = Color(0xFFE7D3C0)
    val interior = Color(0xFFF9A8D4)
    drawOval(pelaje, topLeft = p(33f, 10f, u), size = Size(13 * u, 38 * u))
    drawOval(pelaje, topLeft = p(54f, 10f, u), size = Size(13 * u, 38 * u))
    drawOval(interior, topLeft = p(36.5f, 15f, u), size = Size(6 * u, 26 * u))
    drawOval(interior, topLeft = p(57.5f, 15f, u), size = Size(6 * u, 26 * u))
    drawCircle(pelaje, 25 * u, p(50f, 60f, u))
    ojos(u, 56f, sep = 9f)
    drawCircle(Color(0xFFEC4899), 3 * u, p(50f, 65f, u))
    drawLine(Color(0xFF8D7A68), p(50f, 68f, u), p(50f, 72f, u), strokeWidth = 1.4f * u)
}
