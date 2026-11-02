package com.tecsup.huellitasvet.ui.theme

import android.app.Activity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.tecsup.huellitasvet.model.Rol

// Administrador: morado
val Morado = Color(0xFF7C3AED)
val MoradoContenedor = Color(0xFFEDE9FE)
val MoradoOscuro = Color(0xFF4C1D95)
val FondoAdmin = Color(0xFFFAF8FF)
val MoradoClaro = Color(0xFFC4B5FD)
val FondoOscuroAdmin = Color(0xFF13111C)
val SuperficieOscuraAdmin = Color(0xFF1F1B2E)

private val DuenoClaro = lightColorScheme(
    primary = Esmeralda,
    onPrimary = Color.White,
    primaryContainer = EsmeraldaContenedor,
    onPrimaryContainer = EsmeraldaOscuro,
    secondary = Esmeralda,
    secondaryContainer = EsmeraldaContenedor,
    onSecondaryContainer = EsmeraldaOscuro,
    background = FondoDueno,
    surface = Color.White,
    surfaceVariant = Color(0xFFEFF5F2),
    onSurfaceVariant = Color(0xFF5B6B64),
    outline = Color(0xFFD5E2DC),
    outlineVariant = Color(0xFFE5EEEA),
    error = Rojo
)

private val DuenoOscuro = darkColorScheme(
    primary = EsmeraldaClaro,
    onPrimary = Color(0xFF00382A),
    primaryContainer = EsmeraldaOscuro,
    onPrimaryContainer = EsmeraldaContenedor,
    secondary = EsmeraldaClaro,
    secondaryContainer = EsmeraldaOscuro,
    onSecondaryContainer = EsmeraldaContenedor,
    background = FondoOscuro,
    surface = SuperficieOscura,
    surfaceVariant = Color(0xFF26302D),
    onSurfaceVariant = Color(0xFFB5C4BE),
    outline = Color(0xFF3B4743),
    outlineVariant = Color(0xFF2C3633),
    error = Color(0xFFF87171)
)

private val VetClaro = lightColorScheme(
    primary = Azul,
    onPrimary = Color.White,
    primaryContainer = AzulContenedor,
    onPrimaryContainer = AzulOscuro,
    secondary = Azul,
    secondaryContainer = AzulContenedor,
    onSecondaryContainer = AzulOscuro,
    background = FondoVet,
    surface = Color.White,
    surfaceVariant = Color(0xFFEEF2FA),
    onSurfaceVariant = Color(0xFF5A6478),
    outline = Color(0xFFD4DBEA),
    outlineVariant = Color(0xFFE4E9F3),
    error = Rojo
)

private val VetOscuro = darkColorScheme(
    primary = AzulClaro,
    onPrimary = Color(0xFF0B2A6B),
    primaryContainer = AzulOscuro,
    onPrimaryContainer = AzulContenedor,
    secondary = AzulClaro,
    secondaryContainer = AzulOscuro,
    onSecondaryContainer = AzulContenedor,
    background = FondoOscuroVet,
    surface = SuperficieOscuraVet,
    surfaceVariant = Color(0xFF262D3D),
    onSurfaceVariant = Color(0xFFB4BED3),
    outline = Color(0xFF3A4458),
    outlineVariant = Color(0xFF2B3344),
    error = Color(0xFFF87171)
)

private val AdminClaro = lightColorScheme(
    primary = Morado,
    onPrimary = Color.White,
    primaryContainer = MoradoContenedor,
    onPrimaryContainer = MoradoOscuro,
    secondary = Morado,
    secondaryContainer = MoradoContenedor,
    onSecondaryContainer = MoradoOscuro,
    background = FondoAdmin,
    surface = Color.White,
    surfaceVariant = Color(0xFFF1EEFA),
    onSurfaceVariant = Color(0xFF625B78),
    outline = Color(0xFFDDD6EE),
    outlineVariant = Color(0xFFEAE5F5),
    error = Rojo
)

private val AdminOscuro = darkColorScheme(
    primary = MoradoClaro,
    onPrimary = Color(0xFF2E1065),
    primaryContainer = MoradoOscuro,
    onPrimaryContainer = MoradoContenedor,
    secondary = MoradoClaro,
    secondaryContainer = MoradoOscuro,
    onSecondaryContainer = MoradoContenedor,
    background = FondoOscuroAdmin,
    surface = SuperficieOscuraAdmin,
    surfaceVariant = Color(0xFF2B2640),
    onSurfaceVariant = Color(0xFFC0B8D6),
    outline = Color(0xFF443C5C),
    outlineVariant = Color(0xFF332D47),
    error = Color(0xFFF87171)
)

fun esquemaPara(rol: Rol, oscuro: Boolean): ColorScheme = when (rol) {
    Rol.CLIENTE -> if (oscuro) DuenoOscuro else DuenoClaro
    Rol.ADMIN -> if (oscuro) VetOscuro else VetClaro
    Rol.ADMINISTRADOR -> if (oscuro) AdminOscuro else AdminClaro
}

// Entrega un ColorScheme segun el perfil: en las pantallas usa MaterialTheme.colorScheme
@Composable
fun VetTheme(
    rol: Rol = Rol.CLIENTE,
    oscuro: Boolean = false,
    content: @Composable () -> Unit
) {
    // Iconos de la barra de estado oscuros sobre fondo claro (y al reves en modo oscuro)
    val vista = LocalView.current
    if (!vista.isInEditMode) {
        SideEffect {
            val ventana = (vista.context as? Activity)?.window ?: return@SideEffect
            WindowCompat.getInsetsController(ventana, vista).apply {
                isAppearanceLightStatusBars = !oscuro
                isAppearanceLightNavigationBars = !oscuro
            }
        }
    }
    MaterialTheme(
        colorScheme = esquemaPara(rol, oscuro),
        typography = Typography,
        content = content
    )
}
