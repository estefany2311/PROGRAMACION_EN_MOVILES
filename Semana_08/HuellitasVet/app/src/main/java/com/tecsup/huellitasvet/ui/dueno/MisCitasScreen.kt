package com.tecsup.huellitasvet.ui.dueno

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.data.local.relation.CitaConDetalle
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.CitaCard
import com.tecsup.huellitasvet.ui.components.IconoServicio
import com.tecsup.huellitasvet.ui.components.soles
import com.tecsup.huellitasvet.ui.theme.Ambar
import com.tecsup.huellitasvet.ui.theme.AmbarContenedor
import com.tecsup.huellitasvet.ui.theme.Rojo
import com.tecsup.huellitasvet.viewmodel.MisCitasViewModel

@Composable
fun MisCitasScreen(vm: MisCitasViewModel, onPedirCita: () -> Unit) {
    val proximas by vm.proximas.collectAsStateWithLifecycle()
    val historial by vm.historial.collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()
    var pestana by rememberSaveable { mutableIntStateOf(0) }
    var aCancelar by remember { mutableStateOf<CitaConDetalle?>(null) }

    Scaffold(
        topBar = { AppTopBar("Mis citas") },
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onPedirCita,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) { Icon(Icons.Filled.Add, contentDescription = "Pedir cita") }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)) {
            OpcionesChips(
                listOf(0, 1), pestana,
                { if (it == 0) "Próximas (${proximas.size})" else "Historial (${historial.size})" },
                { pestana = it }
            )
            val lista = if (pestana == 0) proximas else historial
            LazyColumn(
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (pestana == 0) {
                    item { AvisoCancelacion() }
                }
                if (lista.isEmpty()) {
                    item {
                        Text(
                            if (pestana == 0) "No tienes citas próximas" else "Aún no hay citas en tu historial",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                items(lista, key = { it.cita.id }) { c ->
                    val estado = EstadoCita.desde(c.cita.estado)
                    val activa = estado == EstadoCita.PENDIENTE || estado == EstadoCita.CONFIRMADA
                    CitaCard(
                        titulo = c.servicio.nombre,
                        subtitulo = "${c.mascota.mascota.nombre} · ${Horarios.fechaCorta(c.cita.fecha)} · ${c.cita.hora}",
                        detalle = soles(c.cita.precio),
                        estado = estado,
                        lider = { IconoServicio(c.servicio.icono) },
                        accion = if (pestana == 0 && activa) {
                            {
                                TextButton(onClick = {
                                    // La regla la decide el ViewModel
                                    if (vm.puedeCancelar(c)) aCancelar = c else vm.cancelar(c)
                                }) { Text("Cancelar", color = Rojo) }
                            }
                        } else null
                    )
                }
            }
        }
    }

    aCancelar?.let { c ->
        AlertDialog(
            onDismissRequest = { aCancelar = null },
            title = { Text("Cancelar cita") },
            text = {
                Text("¿Cancelar ${c.servicio.nombre} de ${c.mascota.mascota.nombre} el ${Horarios.fechaCorta(c.cita.fecha)} a las ${c.cita.hora}?")
            },
            confirmButton = {
                TextButton(onClick = { vm.cancelar(c); aCancelar = null }) { Text("Sí, cancelar", color = Rojo) }
            },
            dismissButton = { TextButton(onClick = { aCancelar = null }) { Text("No") } }
        )
    }
    if (mensaje != null) {
        AlertDialog(
            onDismissRequest = vm::limpiarMensaje,
            title = { Text("No se puede cancelar") },
            text = { Text("${mensaje!!}. Solo se puede cancelar hasta 2 horas antes.") },
            confirmButton = { TextButton(onClick = vm::limpiarMensaje) { Text("Entendido") } }
        )
    }
}

@Composable
private fun AvisoCancelacion() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(AmbarContenedor, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Outlined.Info, null, tint = Ambar, modifier = Modifier.size(18.dp))
        Text("  Puedes cancelar hasta 2 horas antes", style = MaterialTheme.typography.bodySmall, color = Ambar)
    }
}
