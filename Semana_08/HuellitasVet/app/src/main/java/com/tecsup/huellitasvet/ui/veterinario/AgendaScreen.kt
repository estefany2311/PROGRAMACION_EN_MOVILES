package com.tecsup.huellitasvet.ui.veterinario

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.data.local.relation.CitaConDetalle
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.EstadoCitaChip
import com.tecsup.huellitasvet.ui.components.MascotaAvatar
import com.tecsup.huellitasvet.ui.theme.Rojo
import com.tecsup.huellitasvet.viewmodel.AgendaViewModel
import java.time.LocalDate

@Composable
fun AgendaScreen(vm: AgendaViewModel, onAtender: (Int) -> Unit) {
    val fecha by vm.fechaSeleccionada.collectAsStateWithLifecycle()
    val citas by vm.citas.collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()
    var aCancelar by remember { mutableStateOf<CitaConDetalle?>(null) }
    val cs = MaterialTheme.colorScheme

    Scaffold(topBar = { AppTopBar("Agenda") }, containerColor = cs.background) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            // Chips de los proximos dias
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(vm.fechas) { d ->
                    val sel = d.toString() == fecha
                    val texto = if (d == LocalDate.now()) "Hoy ${d.dayOfMonth}" else "${Horarios.diaSemana(d)} ${d.dayOfMonth}"
                    Surface(
                        onClick = { vm.elegirFecha(d) },
                        shape = RoundedCornerShape(50),
                        color = if (sel) cs.primary else cs.surface,
                        contentColor = if (sel) cs.onPrimary else cs.onSurface,
                        border = if (sel) null else BorderStroke(1.dp, cs.outline)
                    ) {
                        Text(texto, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp))
                    }
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (citas.isEmpty()) {
                    item { Text("No hay citas para este día", color = cs.onSurfaceVariant, modifier = Modifier.padding(8.dp)) }
                }
                // Linea de tiempo: la hora a la izquierda y la tarjeta a la derecha
                items(citas, key = { it.cita.id }) { c ->
                    Row {
                        Text(
                            c.cita.hora,
                            style = MaterialTheme.typography.labelLarge,
                            color = cs.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(56.dp).padding(top = 14.dp)
                        )
                        TarjetaAgenda(
                            c,
                            onConfirmar = { vm.confirmar(c) },
                            onAtender = { onAtender(c.cita.id) },
                            onCancelar = { aCancelar = c },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }

    aCancelar?.let { c ->
        AlertDialog(
            onDismissRequest = { aCancelar = null },
            title = { Text("Cancelar cita") },
            text = { Text("¿Cancelar la cita de ${c.mascota.mascota.nombre} a las ${c.cita.hora}?") },
            confirmButton = { TextButton(onClick = { vm.cancelar(c); aCancelar = null }) { Text("Sí, cancelar", color = Rojo) } },
            dismissButton = { TextButton(onClick = { aCancelar = null }) { Text("No") } }
        )
    }
    if (mensaje != null) {
        AlertDialog(
            onDismissRequest = vm::limpiarMensaje,
            text = { Text(mensaje!!) },
            confirmButton = { TextButton(onClick = vm::limpiarMensaje) { Text("Entendido") } }
        )
    }
}

@Composable
private fun TarjetaAgenda(
    c: CitaConDetalle,
    onConfirmar: () -> Unit,
    onAtender: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier
) {
    val estado = EstadoCita.desde(c.cita.estado)
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                MascotaAvatar(Especie.desde(c.mascota.mascota.especie), tamano = 44.dp)
                Column(Modifier.weight(1f).padding(start = 10.dp)) {
                    Text("${c.mascota.mascota.nombre} · ${c.servicio.nombre}", style = MaterialTheme.typography.titleSmall)
                    Text(
                        "Dueño: ${c.mascota.dueno.nombre}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    EstadoCitaChip(estado)
                }
            }
            if (!estado.esFinal) {
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = onCancelar) { Text("Cancelar", color = Rojo) }
                    Box(Modifier.width(4.dp))
                    // 'Confirmar' si esta PENDIENTE, 'Atender' si esta CONFIRMADA
                    Button(
                        onClick = if (estado == EstadoCita.PENDIENTE) onConfirmar else onAtender,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) { Text(if (estado == EstadoCita.PENDIENTE) "Confirmar" else "Atender") }
                }
            }
        }
    }
}
