package com.saludplus.citas.ui.screens.citas

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Reto extra: datos de la cita y botón 'Cancelar' con AlertDialog de confirmación (Repositorio.cancelarCita).
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun DetalleCitaScreen(
    navController: NavController,
    citaId: Int
) {
    PantallaEnConstruccion(
        nombre = "Detalle de cita (citaId = $citaId)",
        botones = listOf(
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
