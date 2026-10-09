package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Vista NO dibujada en el diseño: usa el mismo estilo (ícono de check, mensaje y resumen de la cita con obtenerCita).
// TODO: Botón 'Ver mis citas' que lleva a MisCitas.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun CitaExitosaScreen(
    navController: NavController,
    citaId: Int
) {
    PantallaEnConstruccion(
        nombre = "Cita agendada (citaId = $citaId)",
        botones = listOf(
            BotonTemporal("Ver mis citas") { navController.navigate(Rutas.MIS_CITAS) },
            BotonTemporal("Ir a Inicio") { navController.navigate(Rutas.HOME) }
        )
    )
}
