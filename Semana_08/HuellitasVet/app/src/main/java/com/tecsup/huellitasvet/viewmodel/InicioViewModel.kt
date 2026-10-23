package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.entity.Servicio
import com.tecsup.huellitasvet.data.local.relation.CitaConDetalle
import com.tecsup.huellitasvet.data.repository.VetRepository
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Horarios
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.LocalDateTime

// Inicio del dueño: sus mascotas y su proxima cita
class InicioViewModel(
    repo: VetRepository,
    duenoId: Int
) : ViewModel() {

    val mascotas: StateFlow<List<Mascota>> = repo.observarMascotasDeDueno(duenoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Regla: la primera cita PENDIENTE o CONFIRMADA desde ahora, ordenada por fecha y hora
    val proximaCita: StateFlow<CitaConDetalle?> = repo.observarCitasDeDueno(duenoId)
        .map { citas ->
            val ahora = LocalDateTime.now()
            citas.filter {
                val estado = EstadoCita.desde(it.cita.estado)
                (estado == EstadoCita.PENDIENTE || estado == EstadoCita.CONFIRMADA) &&
                    !Horarios.fechaHora(it.cita.fecha, it.cita.hora).isBefore(ahora)
            }.minByOrNull { Horarios.fechaHora(it.cita.fecha, it.cita.hora) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Accesos rapidos: solo servicios activos
    val servicios: StateFlow<List<Servicio>> = repo.observarServiciosActivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Edad = año actual - año de nacimiento
    fun edad(m: Mascota): Int = LocalDate.now().year - m.anioNacimiento
}
