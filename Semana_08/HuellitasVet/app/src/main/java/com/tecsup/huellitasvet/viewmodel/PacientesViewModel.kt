package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.relation.MascotaConDueno
import com.tecsup.huellitasvet.data.repository.VetRepository
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Especie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

// Un paciente con la fecha de su ultima atencion (null = sin visitas)
data class PacienteUi(val datos: MascotaConDueno, val ultimaVisita: String?)

// Lista de pacientes para el veterinario, filtrada por texto y especie
class PacientesViewModel(repo: VetRepository) : ViewModel() {

    private val _texto = MutableStateFlow("")
    val texto: StateFlow<String> = _texto.asStateFlow()

    private val _especie = MutableStateFlow<Especie?>(null)   // null = Todos
    val especie: StateFlow<Especie?> = _especie.asStateFlow()

    // Ultima visita = fecha de la cita ATENDIDA mas reciente de cada mascota
    private val ultimasVisitas = repo.observarTodasLasCitas().map { citas ->
        citas.filter { it.cita.estado == EstadoCita.ATENDIDA.name }
            .groupBy { it.cita.mascotaId }
            .mapValues { (_, lista) -> lista.maxOf { it.cita.fecha } }
    }

    val pacientes: StateFlow<List<PacienteUi>> = combine(
        repo.observarTodasLasMascotas(), _texto, _especie, ultimasVisitas
    ) { mascotas, texto, especie, visitas ->
        val q = texto.trim()
        mascotas
            .filter { especie == null || it.mascota.especie == especie.name }
            .filter {
                q.isEmpty() ||
                    it.mascota.nombre.contains(q, ignoreCase = true) ||
                    it.dueno.nombre.contains(q, ignoreCase = true)
            }
            .map { PacienteUi(it, visitas[it.mascota.id]) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun buscar(t: String) { _texto.value = t }
    fun filtrar(e: Especie?) { _especie.value = e }

    fun edad(m: Mascota): Int = LocalDate.now().year - m.anioNacimiento
}
