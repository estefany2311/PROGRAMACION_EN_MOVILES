package com.tecsup.huellitasvet.ui.veterinario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.model.Sexo
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.EstadoCitaChip
import com.tecsup.huellitasvet.ui.components.MascotaAvatar
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.components.VetTextField
import com.tecsup.huellitasvet.ui.dueno.textoEdad
import com.tecsup.huellitasvet.ui.theme.Rojo
import com.tecsup.huellitasvet.viewmodel.AtenderCitaViewModel
import java.time.LocalDate

@Composable
fun AtenderCitaScreen(vm: AtenderCitaViewModel, onTerminar: () -> Unit) {
    val cita by vm.cita.collectAsStateWithLifecycle()
    val errores by vm.errores.collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()
    val terminado by vm.terminado.collectAsStateWithLifecycle()
    var peso by rememberSaveable { mutableStateOf("") }
    var diagnostico by rememberSaveable { mutableStateOf("") }
    var tratamiento by rememberSaveable { mutableStateOf("") }
    var confirmarCancelar by remember { mutableStateOf(false) }
    LaunchedEffect(terminado) { if (terminado) onTerminar() }

    val cs = MaterialTheme.colorScheme
    Scaffold(topBar = { AppTopBar("Atender cita", onVolver = onTerminar) }, containerColor = cs.background) { padding ->
        val c = cita ?: return@Scaffold
        val m = c.mascota.mascota
        val estado = EstadoCita.desde(c.cita.estado)
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Cabecera: mascota, dueño, servicio y hora
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = cs.surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    MascotaAvatar(Especie.desde(m.especie), tamano = 60.dp)
                    Column(Modifier.weight(1f).padding(start = 12.dp)) {
                        Text("${m.nombre} · ${Especie.desde(m.especie).etiqueta} ${m.raza}", style = MaterialTheme.typography.titleSmall)
                        Text(
                            "${textoEdad(LocalDate.now().year - m.anioNacimiento)} · ${Sexo.desde(m.sexo).etiqueta} · Dueño: ${c.mascota.dueno.nombre}",
                            style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant
                        )
                        Text(
                            "${c.servicio.nombre} · ${Horarios.fechaCorta(c.cita.fecha)} · ${c.cita.hora}",
                            style = MaterialTheme.typography.labelMedium, color = cs.primary
                        )
                        Spacer(Modifier.height(4.dp))
                        EstadoCitaChip(estado)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Registro clínico", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
                VetTextField(
                    peso, { peso = it; vm.limpiarErrorPeso() }, "Peso (kg)",
                    modifier = Modifier.weight(1f), error = errores.peso, teclado = KeyboardType.Decimal
                )
                Column(
                    Modifier
                        .weight(1f)
                        .padding(top = 8.dp)
                        .background(cs.primaryContainer.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Favorite, null, tint = Rojo, modifier = Modifier.size(14.dp))
                        Text("  Peso anterior", style = MaterialTheme.typography.labelSmall, color = cs.onSurfaceVariant)
                    }
                    Text("${m.peso} kg", style = MaterialTheme.typography.titleSmall)
                }
            }
            VetTextField(
                diagnostico, { diagnostico = it; vm.limpiarErrorDiagnostico() }, "Diagnóstico",
                error = errores.diagnostico, lineas = 3
            )
            Spacer(Modifier.height(4.dp))
            VetTextField(tratamiento, { tratamiento = it }, "Tratamiento", lineas = 3)
            Spacer(Modifier.height(10.dp))

            val nuevo = peso.replace(',', '.').toDoubleOrNull()
            Row(
                Modifier.fillMaxWidth().background(cs.primaryContainer.copy(alpha = 0.6f), RoundedCornerShape(12.dp)).padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.CheckCircle, null, tint = cs.primary, modifier = Modifier.size(18.dp))
                Text(
                    "  Se actualizará el peso de ${m.nombre}" + (if (nuevo != null && nuevo > 0) " a $nuevo kg" else ""),
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onPrimaryContainer
                )
            }
            Spacer(Modifier.height(20.dp))
            VetButton("Registrar atención", onClick = { vm.registrar(diagnostico, tratamiento, peso) })
            Spacer(Modifier.height(10.dp))
            VetButton("Cancelar cita", onClick = { confirmarCancelar = true }, contorno = true, color = Rojo)
            Spacer(Modifier.height(20.dp))
        }
    }

    if (confirmarCancelar) {
        AlertDialog(
            onDismissRequest = { confirmarCancelar = false },
            title = { Text("Cancelar cita") },
            text = { Text("¿Seguro que quieres cancelar esta cita?") },
            confirmButton = { TextButton(onClick = { confirmarCancelar = false; vm.cancelar() }) { Text("Sí, cancelar", color = Rojo) } },
            dismissButton = { TextButton(onClick = { confirmarCancelar = false }) { Text("No") } }
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
