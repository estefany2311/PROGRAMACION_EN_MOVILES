package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.entity.Servicio
import com.tecsup.huellitasvet.data.prefs.PreferenciasStore
import com.tecsup.huellitasvet.data.repository.VetRepository
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
import java.time.LocalDate
import java.time.LocalDateTime

// Un horario del dia y si se puede elegir (no ocupado y no pasado)
data class HorarioUi(val hora: String, val ocupado: Boolean, val pasado: Boolean) {
    val disponible: Boolean get() = !ocupado && !pasado
}

// Pedir cita: mascota, servicio, fecha y hora sin chocar con otras citas
@OptIn(ExperimentalCoroutinesApi::class)
class PedirCitaViewModel(
    private val repo: VetRepository,
    prefs: PreferenciasStore,
    duenoId: Int,
    mascotaInicial: Int = 0
) : ViewModel() {

    val mascotas: StateFlow<List<Mascota>> = repo.observarMascotasDeDueno(duenoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Solo servicios ACTIVOS: los inactivos no aparecen al pedir cita
    val servicios: StateFlow<List<Servicio>> = repo.observarServiciosActivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Ajuste del veterinario: si la clinica no recibe citas, el dueño no puede pedir una
    val aceptaCitas: StateFlow<Boolean> = prefs.aceptarCitas
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    // De hoy hasta 7 dias
    val fechas: List<LocalDate> = (0L..7L).map { LocalDate.now().plusDays(it) }

    private val _mascotaId = MutableStateFlow(mascotaInicial)
    val mascotaId: StateFlow<Int> = _mascotaId.asStateFlow()

    private val _servicioId = MutableStateFlow(0)
    val servicioId: StateFlow<Int> = _servicioId.asStateFlow()

    private val _fecha = MutableStateFlow(LocalDate.now().toString())   // por defecto: hoy
    val fecha: StateFlow<String> = _fecha.asStateFlow()

    private val _hora = MutableStateFlow<String?>(null)
    val hora: StateFlow<String?> = _hora.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private val _creada = MutableStateFlow(false)
    val creada: StateFlow<Boolean> = _creada.asStateFlow()

    // Cambia cada vez que cambia la fecha
    val horasOcupadas: StateFlow<List<String>> = _fecha
        .flatMapLatest { repo.observarHorasOcupadas(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Los 18 horarios: ocupados (otra cita no cancelada) o pasados (si la fecha es hoy)
    val horarios: StateFlow<List<HorarioUi>> = combine(_fecha, horasOcupadas) { f, ocupadas ->
        val ahora = LocalDateTime.now()
        Horarios.todos.map { h ->
            HorarioUi(
                hora = h,
                ocupado = h in ocupadas,
                pasado = !Horarios.fechaHora(f, h).isAfter(ahora)
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val puedeConfirmar: StateFlow<Boolean> = combine(_mascotaId, _servicioId, _hora, aceptaCitas) { m, s, h, acepta ->
        acepta && m > 0 && s > 0 && h != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun elegirMascota(id: Int) { _mascotaId.value = id }
    fun elegirServicio(id: Int) { _servicioId.value = id }

    // Si la fecha cambia, se limpia la hora elegida
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
        val mascota = mascotas.value.firstOrNull { it.id == _mascotaId.value }
        val servicio = servicios.value.firstOrNull { it.id == _servicioId.value }
        val f = _fecha.value
        val h = _hora.value
        val dia = LocalDate.parse(f)
        when {
            !aceptaCitas.value -> { _mensaje.value = "La clínica no está recibiendo citas"; return }
            mascota == null -> { _mensaje.value = "Elige una de tus mascotas"; return }
            servicio == null || !servicio.activo -> { _mensaje.value = "Elige un servicio disponible"; return }
            dia.isBefore(LocalDate.now()) || dia.isAfter(LocalDate.now().plusDays(7)) -> {
                _mensaje.value = "La fecha debe estar entre hoy y los próximos 7 días"; return
            }
            h == null || h !in Horarios.todos -> { _mensaje.value = "Elige una hora"; return }
            !Horarios.fechaHora(f, h).isAfter(LocalDateTime.now()) -> {
                _mensaje.value = "Esa hora ya pasó. Elige otra"; _hora.value = null; return
            }
        }
        viewModelScope.launch {
            // Antes de crear se vuelve a consultar: dos citas no pueden compartir fecha y hora
            if (repo.horarioOcupado(f, h!!)) {
                _mensaje.value = "El horario de las $h se acaba de ocupar. Elige otro"
                _hora.value = null
                return@launch
            }
            repo.crearCita(mascota!!.id, servicio!!.id, f, h, nota = "")
            _creada.value = true
        }
    }
}
