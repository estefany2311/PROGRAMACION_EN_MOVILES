package com.chavez.NavLab5.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val PortalColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = TextOnPrimary,
    primaryContainer = PurpleContainer,
    onPrimaryContainer = PurpleDark,
    secondary = PurpleLight,
    background = BackgroundLavender,
    onBackground = TextDark,
    surface = SurfaceWhite,
    onSurface = TextDark,
    error = ErrorRed
)

@Composable
fun NabLav05Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PortalColorScheme,
        typography = Typography,
        content = content
    )
}
