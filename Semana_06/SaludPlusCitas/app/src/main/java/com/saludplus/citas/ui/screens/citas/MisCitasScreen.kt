package com.saludplus.citas.ui.screens.citas

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Vista NO dibujada. LazyColumn con Repositorio.citasDelUsuario().
// TODO: Si no hay citas mostrar un mensaje de lista vacía. Cada cita muestra médico, fecha y hora; al tocarla ir a DetalleCita.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun MisCitasScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Mis citas",
        botones = listOf(
            BotonTemporal("Detalle de cita (id 1)") { navController.navigate(Rutas.detalleCita(1)) },
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
