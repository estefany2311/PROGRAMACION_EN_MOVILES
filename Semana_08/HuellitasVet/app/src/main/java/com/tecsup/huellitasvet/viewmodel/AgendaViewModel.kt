package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.relation.CitaConDetalle
import com.tecsup.huellitasvet.data.repository.VetRepository
import com.tecsup.huellitasvet.model.EstadoCita
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

// Agenda del dia para el veterinario
@OptIn(ExperimentalCoroutinesApi::class)
class AgendaViewModel(private val repo: VetRepository) : ViewModel() {

    val fechas: List<LocalDate> = (0L..6L).map { LocalDate.now().plusDays(it) }

    private val _fechaSeleccionada = MutableStateFlow(LocalDate.now().toString())   // por defecto: hoy
    val fechaSeleccionada: StateFlow<String> = _fechaSeleccionada.asStateFlow()

    // Citas del dia elegido, ordenadas por hora (lo hace la consulta)
    val citas: StateFlow<List<CitaConDetalle>> = _fechaSeleccionada
        .flatMapLatest { repo.observarCitasPorFecha(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun elegirFecha(f: LocalDate) { _fechaSeleccionada.value = f.toString() }

    // Regla de estados: PENDIENTE -> CONFIRMADA
    fun confirmar(c: CitaConDetalle) {
        if (EstadoCita.desde(c.cita.estado) != EstadoCita.PENDIENTE) {
            _mensaje.value = "Solo se puede confirmar una cita PENDIENTE"
            return
        }
        viewModelScope.launch { repo.cambiarEstadoCita(c.cita.id, EstadoCita.CONFIRMADA) }
    }

    // Desde PENDIENTE o CONFIRMADA se puede cancelar. ATENDIDA y CANCELADA son finales
    fun cancelar(c: CitaConDetalle) {
        if (EstadoCita.desde(c.cita.estado).esFinal) {
            _mensaje.value = "Una cita ${EstadoCita.desde(c.cita.estado).etiqueta.lowercase()} ya no se puede cancelar"
            return
        }
        viewModelScope.launch { repo.cambiarEstadoCita(c.cita.id, EstadoCita.CANCELADA) }
    }

    fun limpiarMensaje() { _mensaje.value = null }
}
