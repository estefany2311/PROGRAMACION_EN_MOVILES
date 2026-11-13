package com.tecsup.valorant.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta inspirada en los pósters de Valorant
val ValoFondo = Color(0xFF110D18)          // ciruela casi negro
val ValoSuperficie = Color(0xFF1C1626)     // tarjetas
val ValoSuperficieAlta = Color(0xFF2A2236) // fondos de íconos
val ValoLinea = Color(0xFF3A3048)          // bordes finos
val ValoTexto = Color(0xFFF3EEF8)
val ValoTextoSuave = Color(0xFFA99BB8)
val ValoTextoTenue = Color(0xFF7E7090)     // números de agente

val ValoCoral = Color(0xFFFF4655)
val ValoMagenta = Color(0xFFE5489B)
val ValoLavanda = Color(0xFFC6A8F0)
val ValoTurquesa = Color(0xFF3CCFC6)
val ValoAmarillo = Color(0xFFF5C451)

val ValoErrorFondo = Color(0xFF5C1A2E)
val ValoErrorTexto = Color(0xFFFFD9E2)

// Color de cada rol (los nombres llegan de la API en español)
fun colorDeRol(rol: String): Color = when (rol) {
    "Duelista" -> ValoCoral
    "Controlador" -> ValoLavanda
    "Iniciador" -> ValoTurquesa
    "Centinela" -> ValoAmarillo
    else -> ValoTextoSuave
}
