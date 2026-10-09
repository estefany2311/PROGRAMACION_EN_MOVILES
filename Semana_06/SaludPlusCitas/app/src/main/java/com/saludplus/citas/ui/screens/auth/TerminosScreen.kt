package com.saludplus.citas.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Reto extra: texto largo con scroll (verticalScroll) o un AlertDialog.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun TerminosScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Términos y condiciones",
        botones = listOf(
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
