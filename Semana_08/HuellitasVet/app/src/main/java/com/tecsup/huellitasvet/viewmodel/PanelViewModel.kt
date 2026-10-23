package com.tecsup.huellitasvet.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.repository.VetRepository
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.ui.components.graficos.DatoGrafico
import com.tecsup.huellitasvet.ui.theme.EstadoAtendida
import com.tecsup.huellitasvet.ui.theme.EstadoCancelada
import com.tecsup.huellitasvet.ui.theme.EstadoConfirmada
import com.tecsup.huellitasvet.ui.theme.EstadoPendiente
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

// Panel de la clinica: contadores, ingresos del mes y datos para los graficos
class PanelViewModel(repo: VetRepository) : ViewModel() {

    private val hoy = LocalDate.now().toString()
    private val inicioMes = LocalDate.now().withDayOfMonth(1).toString()
    private val manana = LocalDate.now().plusDays(1).toString()

    private fun <T> comoEstado(flujo: Flow<T>, inicial: T): StateFlow<T> =
        flujo.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), inicial)

    val citasHoy: StateFlow<Int> = comoEstado(repo.observarCitasDeFecha(hoy), 0)
    val pendientes: StateFlow<Int> = comoEstado(repo.observarCantidadPorEstado(EstadoCita.PENDIENTE), 0)
    val atendidas: StateFlow<Int> = comoEstado(repo.observarCantidadPorEstado(EstadoCita.ATENDIDA), 0)

    // Suma del precio de las citas ATENDIDAS desde el dia 1 del mes
    val ingresosMes: StateFlow<Double> = comoEstado(repo.observarIngresosDesde(inicioMes), 0.0)

    private val todas = repo.observarTodasLasCitas()

    val atencionesMes: StateFlow<Int> = comoEstado(
        todas.map { l -> l.count { it.cita.estado == EstadoCita.ATENDIDA.name && it.cita.fecha >= inicioMes } }, 0
    )

    // Alerta: citas PENDIENTES de hoy y mañana que el veterinario aun no confirma
    val porConfirmar: StateFlow<Int> = comoEstado(
        todas.map { l -> l.count { it.cita.estado == EstadoCita.PENDIENTE.name && (it.cita.fecha == hoy || it.cita.fecha == manana) } }, 0
    )

    val porEstado: StateFlow<List<DatoGrafico>> = comoEstado(
        combine(
            repo.observarCantidadPorEstado(EstadoCita.PENDIENTE),
            repo.observarCantidadPorEstado(EstadoCita.CONFIRMADA),
            repo.observarCantidadPorEstado(EstadoCita.ATENDIDA),
            repo.observarCantidadPorEstado(EstadoCita.CANCELADA)
        ) { p, c, a, x ->
            listOf(
                DatoGrafico(EstadoCita.PENDIENTE.etiqueta, p.toFloat(), EstadoPendiente),
                DatoGrafico(EstadoCita.CONFIRMADA.etiqueta, c.toFloat(), EstadoConfirmada),
                DatoGrafico(EstadoCita.ATENDIDA.etiqueta, a.toFloat(), EstadoAtendida),
                DatoGrafico(EstadoCita.CANCELADA.etiqueta, x.toFloat(), EstadoCancelada)
            )
        },
        emptyList()
    )

    private val paleta = listOf(Color(0xFF1D4ED8), Color(0xFF60A5FA), Color(0xFF93C5FD), Color(0xFFBFDBFE))

    // Datos de Room (ConteoServicio) convertidos al formato del grafico
    val porServicio: StateFlow<List<DatoGrafico>> = comoEstado(
        repo.observarCitasPorServicio().map { lista ->
            lista.mapIndexed { i, c ->
                DatoGrafico(c.nombre.substringBefore(" "), c.cantidad.toFloat(), paleta[i % paleta.size])
            }
        },
        emptyList()
    )
}
