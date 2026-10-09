package com.saludplus.citas.ui.screens.resultados

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Reto extra: modelo propio de resultado y una lista fija.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun ResultadosScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Resultados",
        botones = listOf(
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
