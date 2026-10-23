package com.tecsup.huellitasvet.ui.dueno

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.model.Sexo
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.HistorialItem
import com.tecsup.huellitasvet.ui.components.MascotaAvatar
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.theme.Rojo
import com.tecsup.huellitasvet.viewmodel.MascotaViewModel

// Ficha de la mascota con pestañas Datos / Historial clinico
@Composable
fun FichaMascotaScreen(
    vm: MascotaViewModel,
    mascotaId: Int,
    onVolver: () -> Unit,
    onPedirCita: (Int) -> Unit,
    onEditar: (Int) -> Unit,
    soloLectura: Boolean = false
) {
    val mascota by vm.mascota.collectAsStateWithLifecycle()
    val historial by vm.historial(mascotaId).collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()
    val terminado by vm.terminado.collectAsStateWithLifecycle()
    var pestana by rememberSaveable { mutableIntStateOf(0) }
    var confirmarEliminar by remember { mutableStateOf(false) }
    LaunchedEffect(terminado) { if (terminado) onVolver() }

    Scaffold(
        topBar = { AppTopBar(mascota?.nombre ?: "", onVolver = onVolver) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        val m = mascota ?: return@Scaffold
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item { Cabecera(m, vm.edad(m)) }
            if (!soloLectura) {
                item {
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        VetButton("Pedir cita", onClick = { onPedirCita(m.id) }, modifier = Modifier.weight(1f))
                        VetButton("Editar", onClick = { onEditar(m.id) }, modifier = Modifier.weight(1f), contorno = true)
                    }
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
                OpcionesChips(listOf(0, 1), pestana, { if (it == 0) "Datos" else "Historial clínico" }, { pestana = it })
                Spacer(Modifier.height(16.dp))
            }
            if (pestana == 0) {
                item { Datos(m, vm.edad(m)) }
                if (!soloLectura) {
                    item {
                        Spacer(Modifier.height(16.dp))
                        VetButton("Eliminar mascota", onClick = { confirmarEliminar = true }, contorno = true, color = Rojo)
                        Spacer(Modifier.height(16.dp))
                    }
                }
            } else if (historial.isEmpty()) {
                item {
                    Text(
                        "Aún no tiene atenciones",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth().padding(24.dp)
                    )
                }
            } else {
                itemsIndexed(historial, key = { _, c -> c.cita.id }) { i, c ->
                    HistorialItem(
                        fecha = Horarios.diaMes(c.cita.fecha),
                        servicio = c.servicio.nombre,
                        diagnostico = c.cita.diagnostico ?: "",
                        tratamiento = c.cita.tratamiento,
                        peso = c.cita.pesoRegistrado,
                        esUltimo = i == historial.lastIndex
                    )
                }
            }
        }
    }

    if (confirmarEliminar) {
        AlertDialog(
            onDismissRequest = { confirmarEliminar = false },
            title = { Text("Eliminar mascota") },
            text = { Text("¿Seguro que quieres eliminar a ${mascota?.nombre}?") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarEliminar = false
                    mascota?.let(vm::eliminar)
                }) { Text("Eliminar", color = Rojo) }
            },
            dismissButton = { TextButton(onClick = { confirmarEliminar = false }) { Text("Cancelar") } }
        )
    }
    if (mensaje != null) {
        AlertDialog(
            onDismissRequest = vm::limpiarMensaje,
            title = { Text("No se puede eliminar") },
            text = { Text(mensaje!!) },
            confirmButton = { TextButton(onClick = vm::limpiarMensaje) { Text("Entendido") } }
        )
    }
}

@Composable
private fun Cabecera(m: Mascota, edad: Int) {
    val especie = Especie.desde(m.especie)
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            MascotaAvatar(especie, tamano = 72.dp)
            Column(Modifier.padding(start = 16.dp)) {
                Text(m.nombre, style = MaterialTheme.typography.titleLarge)
                Text(
                    "${especie.etiqueta} · ${m.raza} · ${textoEdad(edad)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "${Sexo.desde(m.sexo).etiqueta} · ${m.peso} kg",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun Datos(m: Mascota, edad: Int) {
    val filas = listOf(
        "Especie" to Especie.desde(m.especie).etiqueta,
        "Raza" to m.raza,
        "Sexo" to Sexo.desde(m.sexo).etiqueta,
        "Año de nacimiento" to m.anioNacimiento.toString(),
        "Edad" to textoEdad(edad),
        "Peso actual" to "${m.peso} kg"
    )
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            filas.forEachIndexed { i, (k, v) ->
                Row(Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                    Text(k, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
                    Text(v, style = MaterialTheme.typography.titleSmall)
                }
                if (i < filas.lastIndex) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}
