package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.relation.MascotaConDueno
import com.tecsup.huellitasvet.data.repository.VetRepository
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.Horarios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime

// Un paciente con su ultima atencion y su proxima cita (null = no tiene)
data class PacienteUi(
    val datos: MascotaConDueno,
    val ultimaVisita: String?,          // 'aaaa-MM-dd'
    val proximaCita: String?            // 'aaaa-MM-dd HH:mm'
)

enum class OrdenPacientes(val etiqueta: String) {
    NOMBRE("A-Z"),
    ULTIMA_VISITA("Última visita")
}

// Todos los filtros juntos: texto (mascota, dueño o raza), especie, solo con cita proxima y orden
data class FiltrosPacientes(
    val texto: String = "",
    val especie: Especie? = null,          // null = Todos
    val soloConCita: Boolean = false,
    val orden: OrdenPacientes = OrdenPacientes.NOMBRE
) {
    val activos: Boolean get() = this != FiltrosPacientes()
}

// Datos de las citas que necesita la lista: ultima atencion y proxima cita de cada mascota
private data class ResumenCitas(val ultimas: Map<Int, String>, val proximas: Map<Int, String>)

// Lista de pacientes para el veterinario
class PacientesViewModel(repo: VetRepository) : ViewModel() {

    private val _filtros = MutableStateFlow(FiltrosPacientes())
    val filtros: StateFlow<FiltrosPacientes> = _filtros.asStateFlow()

    private val resumen = repo.observarTodasLasCitas().map { citas ->
        val ahora = LocalDateTime.now()
        val ultimas = citas.filter { it.cita.estado == EstadoCita.ATENDIDA.name }
            .groupBy { it.cita.mascotaId }
            .mapValues { (_, lista) -> lista.maxOf { it.cita.fecha } }
        // Proxima = la cita PENDIENTE o CONFIRMADA mas cercana desde ahora
        val proximas = citas.filter {
            val estado = EstadoCita.desde(it.cita.estado)
            (estado == EstadoCita.PENDIENTE || estado == EstadoCita.CONFIRMADA) &&
                !Horarios.fechaHora(it.cita.fecha, it.cita.hora).isBefore(ahora)
        }.groupBy { it.cita.mascotaId }
            .mapValues { (_, lista) -> lista.minOf { "${it.cita.fecha} ${it.cita.hora}" } }
        ResumenCitas(ultimas, proximas)
    }

    val pacientes: StateFlow<List<PacienteUi>> = combine(
        repo.observarTodasLasMascotas(), _filtros, resumen
    ) { mascotas, f, r ->
        val q = f.texto.trim()
        val lista = mascotas
            .filter { f.especie == null || it.mascota.especie == f.especie.name }
            .filter {
                q.isEmpty() ||
                    it.mascota.nombre.contains(q, ignoreCase = true) ||
                    it.dueno.nombre.contains(q, ignoreCase = true) ||
                    it.mascota.raza.contains(q, ignoreCase = true)
            }
            .map { PacienteUi(it, r.ultimas[it.mascota.id], r.proximas[it.mascota.id]) }
            .filter { !f.soloConCita || it.proximaCita != null }
        when (f.orden) {
            OrdenPacientes.NOMBRE -> lista.sortedBy { it.datos.mascota.nombre.lowercase() }
            // La mas reciente primero; las mascotas sin visitas van al final
            OrdenPacientes.ULTIMA_VISITA -> lista.sortedByDescending { it.ultimaVisita ?: "" }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun buscar(t: String) = _filtros.update { it.copy(texto = t) }
    fun filtrar(e: Especie?) = _filtros.update { it.copy(especie = e) }
    fun alternarSoloConCita() = _filtros.update { it.copy(soloConCita = !it.soloConCita) }
    fun ordenar(o: OrdenPacientes) = _filtros.update { it.copy(orden = o) }
    fun limpiar() { _filtros.value = FiltrosPacientes() }

    fun edad(m: Mascota): Int = LocalDate.now().year - m.anioNacimiento
}
