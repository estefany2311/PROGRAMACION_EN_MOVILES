package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.relation.CitaConDetalle
import com.tecsup.huellitasvet.data.repository.VetRepository
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Horarios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

// Mis citas del dueño: Proximas e Historial, y cancelar con la regla de las 2 horas
class MisCitasViewModel(
    private val repo: VetRepository,
    duenoId: Int
) : ViewModel() {

    private val citas = repo.observarCitasDeDueno(duenoId)

    // Proximas = PENDIENTE y CONFIRMADA desde ahora, la mas cercana primero
    val proximas: StateFlow<List<CitaConDetalle>> = citas
        .map { lista -> lista.filter { esProxima(it) }.sortedBy { fechaHora(it) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Historial = ATENDIDA, CANCELADA y las pasadas, la mas reciente primero
    val historial: StateFlow<List<CitaConDetalle>> = citas
        .map { lista -> lista.filterNot { esProxima(it) }.sortedByDescending { fechaHora(it) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private fun fechaHora(c: CitaConDetalle) = Horarios.fechaHora(c.cita.fecha, c.cita.hora)

    private fun esActiva(c: CitaConDetalle): Boolean {
        val estado = EstadoCita.desde(c.cita.estado)
        return estado == EstadoCita.PENDIENTE || estado == EstadoCita.CONFIRMADA
    }

    private fun esProxima(c: CitaConDetalle) = esActiva(c) && !fechaHora(c).isBefore(LocalDateTime.now())

    // Regla: solo PENDIENTE o CONFIRMADA y solo si faltan mas de 2 horas
    fun puedeCancelar(c: CitaConDetalle): Boolean =
        esActiva(c) && Duration.between(LocalDateTime.now(), fechaHora(c)) > Duration.ofHours(2)

    fun cancelar(c: CitaConDetalle) {
        if (!puedeCancelar(c)) {
            _mensaje.value = "Ya no puedes cancelar esta cita"
            return
        }
        viewModelScope.launch { repo.cambiarEstadoCita(c.cita.id, EstadoCita.CANCELADA) }
    }

    fun limpiarMensaje() { _mensaje.value = null }
}
