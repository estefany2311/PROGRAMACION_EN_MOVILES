package com.tecsup.huellitasvet.ui.dueno

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tecsup.huellitasvet.R
import com.tecsup.huellitasvet.ui.components.VetButton

// Pantalla de bienvenida
@Composable
fun SplashScreen(onComenzar: () -> Unit, onTengoCuenta: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(cs.primaryContainer, cs.background)))
            .systemBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier.size(96.dp).background(cs.primary, RoundedCornerShape(26.dp)),
            contentAlignment = Alignment.Center
        ) {
            Huella(Modifier.size(56.dp), cs.onPrimary)
        }
        Spacer(Modifier.height(24.dp))
        Text("HuellitasVet", style = MaterialTheme.typography.headlineMedium, color = cs.onPrimaryContainer)
        Text("Cuidamos a tu mejor amigo", style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant)
        Spacer(Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.perro_gato),
            contentDescription = "Perro y gato",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(24.dp))
        )
        Spacer(Modifier.weight(1.4f))
        VetButton("Comenzar", onClick = onComenzar)
        TextButton(onClick = onTengoCuenta) { Text("Ya tengo una cuenta", color = cs.primary) }
    }
}

// Huella dibujada con Canvas: almohadilla y cuatro dedos
@Composable
fun Huella(modifier: Modifier, color: Color) {
    Canvas(modifier) {
        val u = size.minDimension / 100f
        drawOval(color, topLeft = Offset(28 * u, 50 * u), size = androidx.compose.ui.geometry.Size(44 * u, 38 * u))
        drawCircle(color, 10 * u, Offset(18 * u, 42 * u))
        drawCircle(color, 11 * u, Offset(38 * u, 22 * u))
        drawCircle(color, 11 * u, Offset(62 * u, 22 * u))
        drawCircle(color, 10 * u, Offset(82 * u, 42 * u))
    }
}
