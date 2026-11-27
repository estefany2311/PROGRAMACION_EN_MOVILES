package com.chavez.avisoscampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Azul80,
    onPrimary = Color(0xFF002F65),
    secondary = AzulGris80,
    tertiary = Verde80
)

private val LightColorScheme = lightColorScheme(
    primary = Azul40,
    onPrimary = Color.White,
    secondary = AzulGris40,
    tertiary = Verde40,
    onTertiary = Color.White
)

// Sin color dinámico: la app siempre usa el azul del tema.
@Composable
fun AvisosCampusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
