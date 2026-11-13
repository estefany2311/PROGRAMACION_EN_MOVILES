package com.tecsup.valorant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ValorantColores = darkColorScheme(
    primary = ValoCoral,
    onPrimary = Color.White,
    secondary = ValoMagenta,
    onSecondary = Color.White,
    tertiary = ValoLavanda,
    background = ValoFondo,
    onBackground = ValoTexto,
    surface = ValoFondo,
    onSurface = ValoTexto,
    surfaceVariant = ValoSuperficie,
    onSurfaceVariant = ValoTextoSuave,
    surfaceContainerLowest = ValoFondo,
    surfaceContainerLow = ValoSuperficie,
    surfaceContainer = ValoSuperficie,
    surfaceContainerHigh = ValoSuperficieAlta,
    surfaceContainerHighest = ValoSuperficie,
    inverseSurface = ValoSuperficieAlta,
    outline = ValoLinea,
    outlineVariant = ValoLinea,
    error = Color(0xFFFF8A93),
    errorContainer = ValoErrorFondo,
    onErrorContainer = ValoErrorTexto
)

// La app siempre usa el tema oscuro de Valorant (los parámetros se mantienen
// para no romper llamadas existentes, pero ya no cambian el resultado)
@Suppress("UNUSED_PARAMETER")
@Composable
fun ValorantTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ValorantColores,
        typography = Typography,
        content = content
    )
}
