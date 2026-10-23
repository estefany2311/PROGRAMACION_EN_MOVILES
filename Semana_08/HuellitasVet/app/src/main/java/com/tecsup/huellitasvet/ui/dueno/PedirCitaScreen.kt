package com.tecsup.huellitasvet.ui.dueno

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.entity.Servicio
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.FechaChip
import com.tecsup.huellitasvet.ui.components.HoraChip
import com.tecsup.huellitasvet.ui.components.IconoServicio
import com.tecsup.huellitasvet.ui.components.MascotaAvatar
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.components.soles
import com.tecsup.huellitasvet.ui.theme.GrisOcupado
import com.tecsup.huellitasvet.viewmodel.PedirCitaViewModel

@Composable
fun PedirCitaScreen(vm: PedirCitaViewModel, onVolver: () -> Unit, onCreada: () -> Unit) {
    val mascotas by vm.mascotas.collectAsStateWithLifecycle()
    val servicios by vm.servicios.collectAsStateWithLifecycle()
    val horarios by vm.horarios.collectAsStateWithLifecycle()
    val mascotaId by vm.mascotaId.collectAsStateWithLifecycle()
    val servicioId by vm.servicioId.collectAsStateWithLifecycle()
    val fecha by vm.fecha.collectAsStateWithLifecycle()
    val hora by vm.hora.collectAsStateWithLifecycle()
    val puedeConfirmar by vm.puedeConfirmar.collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()
    val creada by vm.creada.collectAsStateWithLifecycle()
    LaunchedEffect(creada) { if (creada) onCreada() }

    val cs = MaterialTheme.colorScheme
    Scaffold(
        topBar = { AppTopBar("Pedir cita", onVolver = onVolver) },
        containerColor = cs.background
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Titulo("¿Quién viene?")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(mascotas, key = { it.id }) { m ->
                    ChipMascota(m, m.id == mascotaId) { vm.elegirMascota(m.id) }
                }
            }

            Titulo("Servicio")
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                servicios.forEach { s -> FilaServicio(s, s.id == servicioId) { vm.elegirServicio(s.id) } }
            }

            Titulo("Fecha")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(vertical = 2.dp)) {
                items(vm.fechas) { d ->
                    FechaChip(Horarios.diaSemana(d), d.dayOfMonth, d.toString() == fecha, onClick = { vm.elegirFecha(d) })
                }
            }

            Row(Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Hora", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                Text("Gris = ocupado o pasado", style = MaterialTheme.typography.labelSmall, color = GrisOcupado)
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                horarios.chunked(4).forEach { fila ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        fila.forEach { h ->
                            HoraChip(
                                hora = h.hora,
                                seleccionado = h.hora == hora,
                                ocupado = !h.disponible,
                                onClick = { vm.elegirHora(h.hora) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        repeat(4 - fila.size) { Spacer(Modifier.weight(1f)) }
                    }
                }
            }

            // Resumen de lo elegido
            val m = mascotas.firstOrNull { it.id == mascotaId }
            val s = servicios.firstOrNull { it.id == servicioId }
            Spacer(Modifier.height(20.dp))
            Surface(shape = RoundedCornerShape(14.dp), color = cs.primaryContainer.copy(alpha = 0.6f), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Text(
                        "${m?.nombre ?: "Mascota"} · ${s?.nombre ?: "Servicio"}",
                        style = MaterialTheme.typography.titleSmall,
                        color = cs.primary
                    )
                    Text(
                        "${Horarios.fechaCorta(fecha)} · ${hora ?: "--:--"} · ${s?.let { soles(it.precio) } ?: "S/ --"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = cs.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            VetButton("Confirmar cita", onClick = vm::confirmar, enabled = puedeConfirmar)
            Spacer(Modifier.height(20.dp))
        }
    }

    if (mensaje != null) {
        AlertDialog(
            onDismissRequest = vm::limpiarMensaje,
            title = { Text("No se pudo crear la cita") },
            text = { Text(mensaje!!) },
            confirmButton = { TextButton(onClick = vm::limpiarMensaje) { Text("Entendido") } }
        )
    }
}

@Composable
private fun Titulo(texto: String) {
    Text(texto, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
}

@Composable
private fun ChipMascota(m: Mascota, seleccionada: Boolean, onClick: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = if (seleccionada) cs.primaryContainer else cs.surface,
        border = BorderStroke(if (seleccionada) 2.dp else 1.dp, if (seleccionada) cs.primary else cs.outline)
    ) {
        Row(Modifier.padding(start = 6.dp, end = 16.dp, top = 6.dp, bottom = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            MascotaAvatar(Especie.desde(m.especie), tamano = 32.dp)
            Text("  ${m.nombre}", style = MaterialTheme.typography.labelLarge, color = if (seleccionada) cs.primary else cs.onSurface)
        }
    }
}

@Composable
private fun FilaServicio(s: Servicio, seleccionado: Boolean, onClick: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = if (seleccionado) cs.primaryContainer.copy(alpha = 0.6f) else cs.surface,
        border = BorderStroke(if (seleccionado) 2.dp else 1.dp, if (seleccionado) cs.primary else cs.outlineVariant)
    ) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            IconoServicio(s.icono, tamano = 36.dp)
            Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(s.nombre, style = MaterialTheme.typography.titleSmall)
                Text("${s.duracionMin} min", style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
            }
            Text(
                soles(s.precio),
                style = MaterialTheme.typography.labelLarge,
                color = cs.primary,
                modifier = Modifier.background(cs.primaryContainer, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}
