package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Recibe medicoId, fecha y hora. Mostrar el resumen con los datos del médico (obtenerMedico).
// TODO: Campo opcional 'Motivo de consulta'.
// TODO: 'Agendar cita' llama a Repositorio.agendarCita y navega a CitaExitosa con popUpTo para borrar el flujo de agendamiento del historial.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun ConfirmarCitaScreen(
    navController: NavController,
    medicoId: Int,
    fecha: String,
    hora: String
) {
    PantallaEnConstruccion(
        nombre = "Confirmar cita (medicoId = $medicoId, fecha = $fecha, hora = $hora)",
        botones = listOf(
            BotonTemporal("Agendar cita") { navController.navigate(Rutas.citaExitosa(1)) },
            BotonTemporal("Volver") { navController.popBackStack() }
        )
    )
}
