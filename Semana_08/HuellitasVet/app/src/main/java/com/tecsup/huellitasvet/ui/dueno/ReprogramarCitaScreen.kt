package com.tecsup.huellitasvet.ui.dueno

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.CitaCard
import com.tecsup.huellitasvet.ui.components.FechaChip
import com.tecsup.huellitasvet.ui.components.HoraChip
import com.tecsup.huellitasvet.ui.components.IconoServicio
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.theme.GrisOcupado
import com.tecsup.huellitasvet.viewmodel.ReprogramarCitaViewModel

@Composable
fun ReprogramarCitaScreen(vm: ReprogramarCitaViewModel, onTerminar: () -> Unit) {
    val cita by vm.cita.collectAsStateWithLifecycle()
    val fecha by vm.fecha.collectAsStateWithLifecycle()
    val hora by vm.hora.collectAsStateWithLifecycle()
    val horarios by vm.horarios.collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()
    val terminado by vm.terminado.collectAsStateWithLifecycle()
    LaunchedEffect(terminado) { if (terminado) onTerminar() }
    val cs = MaterialTheme.colorScheme

    Scaffold(topBar = { AppTopBar("Reprogramar cita", onVolver = onTerminar) }, containerColor = cs.background) { padding ->
        val c = cita ?: return@Scaffold
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text("Cita actual", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 8.dp))
            CitaCard(
                titulo = c.servicio.nombre,
                subtitulo = "${c.mascota.mascota.nombre} · ${Horarios.fechaCorta(c.cita.fecha)} · ${c.cita.hora}",
                estado = EstadoCita.desde(c.cita.estado),
                lider = { IconoServicio(c.servicio.icono) }
            )

            Text("Nueva fecha", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(vertical = 2.dp)) {
                items(vm.fechas) { d ->
                    FechaChip(Horarios.diaSemana(d), d.dayOfMonth, d.toString() == fecha, onClick = { vm.elegirFecha(d) })
                }
            }

            Row(Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Nueva hora", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
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

            Spacer(Modifier.height(20.dp))
            Surface(shape = RoundedCornerShape(14.dp), color = cs.primaryContainer.copy(alpha = 0.6f), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Text("Pasará a", style = MaterialTheme.typography.labelMedium, color = cs.onSurfaceVariant)
                    Text(
                        "${Horarios.fechaCorta(fecha)} · ${hora ?: "--:--"}",
                        style = MaterialTheme.typography.titleSmall,
                        color = cs.primary
                    )
                    Text(
                        "La cita vuelve a quedar Pendiente hasta que el veterinario la confirme",
                        style = MaterialTheme.typography.bodySmall,
                        color = cs.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            VetButton("Reprogramar cita", onClick = vm::confirmar, enabled = hora != null)
            Spacer(Modifier.height(20.dp))
        }
    }

    if (mensaje != null) {
        AlertDialog(
            onDismissRequest = vm::limpiarMensaje,
            title = { Text("No se pudo reprogramar") },
            text = { Text(mensaje!!) },
            confirmButton = { TextButton(onClick = vm::limpiarMensaje) { Text("Entendido") } }
        )
    }
}
