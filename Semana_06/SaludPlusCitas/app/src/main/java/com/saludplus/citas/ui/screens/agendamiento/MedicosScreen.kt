package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Recibe especialidadId. Mostrar el nombre de la especialidad en la barra superior (obtenerEspecialidad).
// TODO: LazyColumn con Repositorio.buscarMedicos(especialidadId, texto), mejor calificados primero.
// TODO: Cada tarjeta muestra calificación, reseñas y disponibilidad. Al tocar ir a FechaHora con el medicoId.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun MedicosScreen(
    navController: NavController,
    especialidadId: Int
) {
    PantallaEnConstruccion(
        nombre = "Médicos (especialidadId = $especialidadId)",
        botones = listOf(
            BotonTemporal("Dra. Ana Torres (id 1)") { navController.navigate(Rutas.fechaHora(1)) },
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
