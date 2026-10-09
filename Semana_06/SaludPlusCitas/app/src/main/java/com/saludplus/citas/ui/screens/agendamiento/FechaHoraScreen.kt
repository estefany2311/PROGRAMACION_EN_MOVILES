package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Recibe medicoId. Mostrar la tarjeta del médico (obtenerMedico).
// TODO: Fase 1: lista FIJA de 5 días hábiles (formato yyyy-MM-dd) con la selección guardada en un estado.
// TODO: LazyVerticalGrid con Repositorio.horariosDisponibles(medicoId, fechaSeleccionada); un horario reservado ya no debe aparecer.
// TODO: El botón 'Continuar' solo se habilita si hay día y hora elegidos; navega a ConfirmarCita con medicoId, fecha y hora.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun FechaHoraScreen(
    navController: NavController,
    medicoId: Int
) {
    PantallaEnConstruccion(
        nombre = "Fecha y hora (medicoId = $medicoId)",
        botones = listOf(
            BotonTemporal("Continuar") { navController.navigate(Rutas.confirmarCita(medicoId, "2026-09-16", "09:30")) },
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
