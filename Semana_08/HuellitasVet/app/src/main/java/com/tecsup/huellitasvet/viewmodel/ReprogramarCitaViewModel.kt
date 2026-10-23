package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.relation.CitaConDetalle
import com.tecsup.huellitasvet.data.repository.VetRepository
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Horarios
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

// Reprogramar una cita del dueño: nueva fecha y hora libres, con la regla de las 2 horas
@OptIn(ExperimentalCoroutinesApi::class)
class ReprogramarCitaViewModel(
    private val repo: VetRepository,
    citaId: Int
) : ViewModel() {

    val cita: StateFlow<CitaConDetalle?> = repo.observarCita(citaId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // De hoy hasta 7 dias, igual que al pedir cita
    val fechas: List<LocalDate> = (0L..7L).map { LocalDate.now().plusDays(it) }

    private val _fecha = MutableStateFlow(LocalDate.now().toString())
    val fecha: StateFlow<String> = _fecha.asStateFlow()

    private val _hora = MutableStateFlow<String?>(null)
    val hora: StateFlow<String?> = _hora.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private val _terminado = MutableStateFlow(false)
    val terminado: StateFlow<Boolean> = _terminado.asStateFlow()

    private val horasOcupadas: StateFlow<List<String>> = _fecha
        .flatMapLatest { repo.observarHorasOcupadas(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Ocupado = otra cita no cancelada (incluye el horario actual de esta cita); pasado = ya no se puede elegir
    val horarios: StateFlow<List<HorarioUi>> = combine(_fecha, horasOcupadas) { f, ocupadas ->
        val ahora = LocalDateTime.now()
        Horarios.todos.map { h ->
            HorarioUi(h, ocupado = h in ocupadas, pasado = !Horarios.fechaHora(f, h).isAfter(ahora))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Regla: solo PENDIENTE o CONFIRMADA y solo si faltan mas de 2 horas para la cita actual
    fun puedeReprogramar(c: CitaConDetalle): Boolean {
        val estado = EstadoCita.desde(c.cita.estado)
        val activa = estado == EstadoCita.PENDIENTE || estado == EstadoCita.CONFIRMADA
        val faltan = Duration.between(LocalDateTime.now(), Horarios.fechaHora(c.cita.fecha, c.cita.hora))
        return activa && faltan > Duration.ofHours(2)
    }

    fun elegirFecha(f: LocalDate) {
        if (f.toString() != _fecha.value) {
            _fecha.value = f.toString()
            _hora.value = null
        }
    }

    fun elegirHora(h: String) {
        if (horarios.value.firstOrNull { it.hora == h }?.disponible == true) _hora.value = h
    }

    fun limpiarMensaje() { _mensaje.value = null }

    fun confirmar() {
        val c = cita.value ?: return
        val f = _fecha.value
        val h = _hora.value
        when {
            !puedeReprogramar(c) -> { _mensaje.value = "Ya no puedes reprogramar esta cita"; return }
            h == null -> { _mensaje.value = "Elige la nueva hora"; return }
            !Horarios.fechaHora(f, h).isAfter(LocalDateTime.now()) -> {
                _mensaje.value = "Esa hora ya pasó. Elige otra"; _hora.value = null; return
            }
        }
        viewModelScope.launch {
            // Se vuelve a consultar: el horario pudo ocuparse mientras se elegia
            if (repo.horarioOcupado(f, h!!)) {
                _mensaje.value = "El horario de las $h se acaba de ocupar. Elige otro"
                _hora.value = null
                return@launch
            }
            repo.reprogramarCita(c.cita.id, f, h)
            _terminado.value = true
        }
    }
}
