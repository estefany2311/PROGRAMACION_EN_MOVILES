package com.saludplus.citas.ui.screens.perfil

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Vista NO dibujada. Mostrar los datos del usuario en sesión y la cantidad de citas (citasDelUsuario).
// TODO: Botón 'Cerrar sesión': llamar a Repositorio.cerrarSesion y volver a Splash limpiando el historial (popUpTo).
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun PerfilScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Perfil / Mis datos",
        botones = listOf(
            BotonTemporal("Cerrar sesión") { navController.navigate(Rutas.SPLASH) },
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
