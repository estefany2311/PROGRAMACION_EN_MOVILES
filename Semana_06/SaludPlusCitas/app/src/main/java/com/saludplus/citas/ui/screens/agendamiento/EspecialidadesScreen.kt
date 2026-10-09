package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Barra superior con flecha de volver y buscador (OutlinedTextField).
// TODO: LazyColumn con Repositorio.buscarEspecialidades(texto): la lista debe cambiar sola al escribir.
// TODO: Al tocar una especialidad navegar a Medicos pasando su id (Rutas.medicos(id)).
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun EspecialidadesScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Especialidades",
        botones = listOf(
            BotonTemporal("Ginecología (id 3)") { navController.navigate(Rutas.medicos(3)) },
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
