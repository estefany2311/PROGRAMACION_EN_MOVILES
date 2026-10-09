package com.saludplus.citas.ui.screens.notificaciones

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Reto extra: generar avisos con map sobre las citas del usuario.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun NotificacionesScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Notificaciones",
        botones = listOf(
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
