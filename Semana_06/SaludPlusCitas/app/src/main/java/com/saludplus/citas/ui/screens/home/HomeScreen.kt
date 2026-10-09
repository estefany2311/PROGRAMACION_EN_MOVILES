package com.saludplus.citas.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Scaffold con NavigationBar (Inicio, Citas, Resultados, Perfil) como bottomBar.
// TODO: Saludo con el nombre del usuario en sesión (Repositorio.usuarioActual).
// TODO: 4 tarjetas de acceso: Agendar cita, Mis citas, Mis datos, Resultados.
// TODO: LazyRow con Repositorio.especialidadesDestacadas() y enlace 'Ver todas'.
// TODO: Ícono de campana que va a Notificaciones.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun HomeScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Inicio",
        botones = listOf(
            BotonTemporal("Agendar cita") { navController.navigate(Rutas.ESPECIALIDADES) },
            BotonTemporal("Mis citas") { navController.navigate(Rutas.MIS_CITAS) },
            BotonTemporal("Resultados") { navController.navigate(Rutas.RESULTADOS) },
            BotonTemporal("Perfil") { navController.navigate(Rutas.PERFIL) },
            BotonTemporal("Notificaciones") { navController.navigate(Rutas.NOTIFICACIONES) }
        )
    )
}
