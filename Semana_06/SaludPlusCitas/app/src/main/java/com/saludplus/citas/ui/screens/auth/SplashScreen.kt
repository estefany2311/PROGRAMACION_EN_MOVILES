package com.saludplus.citas.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Image con el logo, nombre y frase de la clínica, dentro de una Column centrada.
// TODO: Botón 'Comenzar' (va a Registro) y texto 'Ya tengo una cuenta' (va a Login).
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun SplashScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Splash",
        botones = listOf(
            BotonTemporal("Comenzar") { navController.navigate(Rutas.REGISTRO) },
            BotonTemporal("Ya tengo una cuenta") { navController.navigate(Rutas.LOGIN) }
        )
    )
}
