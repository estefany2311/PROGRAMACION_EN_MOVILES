package com.tecsup.huellitasvet.ui.veterinario

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.ICONOS_SERVICIO
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.components.VetTextField
import com.tecsup.huellitasvet.ui.components.iconoServicio
import com.tecsup.huellitasvet.ui.dueno.OpcionesChips
import com.tecsup.huellitasvet.ui.theme.Rojo
import com.tecsup.huellitasvet.viewmodel.ServiciosViewModel

// Sirve para crear (id = 0) y para editar un servicio
@Composable
fun FormularioServicioScreen(vm: ServiciosViewModel, servicioId: Int, onTerminar: () -> Unit) {
    LaunchedEffect(servicioId) { vm.cargar(servicioId) }
    val form by vm.form.collectAsStateWithLifecycle()
    val errores by vm.errores.collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()
    val terminado by vm.terminado.collectAsStateWithLifecycle()
    var confirmarEliminar by remember { mutableStateOf(false) }
    LaunchedEffect(terminado) { if (terminado) onTerminar() }
    val cs = MaterialTheme.colorScheme

    Scaffold(
        topBar = { AppTopBar(if (servicioId == 0) "Nuevo servicio" else "Editar servicio", onVolver = onTerminar) },
        containerColor = cs.background
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            VetTextField(form.nombre, vm::cambiarNombre, "Nombre del servicio", error = errores.nombre)

            Etiqueta("Duración")
            OpcionesChips(ServiciosViewModel.DURACIONES, form.duracionMin, { "$it min" }, vm::cambiarDuracion)
            errores.duracion?.let { Text(it, color = cs.error, style = MaterialTheme.typography.bodySmall) }
            Spacer(Modifier.height(10.dp))

            VetTextField(form.precio, vm::cambiarPrecio, "Precio (S/)", error = errores.precio, teclado = KeyboardType.Decimal)
            VetTextField(form.descripcion, vm::cambiarDescripcion, "Descripción", lineas = 2)

            Etiqueta("Ícono")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ICONOS_SERVICIO.forEach { ic ->
                    val sel = ic == form.icono
                    Surface(
                        onClick = { vm.cambiarIcono(ic) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = if (sel) cs.primaryContainer else cs.surface,
                        border = BorderStroke(if (sel) 2.dp else 1.dp, if (sel) cs.primary else cs.outline)
                    ) {
                        Box(Modifier.padding(vertical = 14.dp), contentAlignment = Alignment.Center) {
                            Icon(iconoServicio(ic), contentDescription = ic, tint = if (sel) cs.primary else cs.onSurfaceVariant,
                                modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = cs.surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Servicio activo", style = MaterialTheme.typography.titleSmall)
                        Text("Los dueños podrán pedirlo", style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                    }
                    Switch(checked = form.activo, onCheckedChange = vm::cambiarActivo)
                }
            }
            Spacer(Modifier.height(20.dp))
            VetButton("Guardar servicio", onClick = vm::guardar)
            Spacer(Modifier.height(10.dp))
            VetButton("Cancelar", onClick = onTerminar, contorno = true)
            if (servicioId != 0) {
                Spacer(Modifier.height(10.dp))
                VetButton("Eliminar servicio", onClick = { confirmarEliminar = true }, contorno = true, color = Rojo)
            }
            Spacer(Modifier.height(20.dp))
        }
    }

    if (confirmarEliminar) {
        AlertDialog(
            onDismissRequest = { confirmarEliminar = false },
            title = { Text("Eliminar servicio") },
            text = { Text("¿Seguro que quieres eliminar ${form.nombre}?") },
            confirmButton = { TextButton(onClick = { confirmarEliminar = false; vm.eliminarActual() }) { Text("Eliminar", color = Rojo) } },
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
private fun Etiqueta(texto: String) {
    Text(texto, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 12.dp, bottom = 8.dp))
}
