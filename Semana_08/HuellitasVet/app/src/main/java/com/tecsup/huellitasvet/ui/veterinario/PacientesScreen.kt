package com.tecsup.huellitasvet.ui.veterinario

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.MascotaAvatar
import com.tecsup.huellitasvet.ui.components.VetTextField
import com.tecsup.huellitasvet.ui.dueno.textoEdad
import com.tecsup.huellitasvet.viewmodel.PacienteUi
import com.tecsup.huellitasvet.viewmodel.PacientesViewModel

@Composable
fun PacientesScreen(vm: PacientesViewModel, onVerPaciente: (Int) -> Unit) {
    val texto by vm.texto.collectAsStateWithLifecycle()
    val especie by vm.especie.collectAsStateWithLifecycle()
    val pacientes by vm.pacientes.collectAsStateWithLifecycle()
    val cs = MaterialTheme.colorScheme

    Scaffold(topBar = { AppTopBar("Pacientes") }, containerColor = cs.background) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)) {
            VetTextField(texto, vm::buscar, "Buscar mascota o dueño", icono = Icons.Outlined.Search)
            // Chips Todos / Perros / Gatos / Otros
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val opciones = listOf<Especie?>(null) + Especie.entries
                opciones.forEach { e ->
                    val sel = e == especie
                    Surface(
                        onClick = { vm.filtrar(e) },
                        shape = RoundedCornerShape(50),
                        color = if (sel) cs.primary else cs.surface,
                        contentColor = if (sel) cs.onPrimary else cs.onSurface,
                        border = if (sel) null else BorderStroke(1.dp, cs.outline)
                    ) {
                        Text(e?.plural ?: "Todos", style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp))
                    }
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (pacientes.isEmpty()) {
                    item { Text("No se encontraron pacientes", color = cs.onSurfaceVariant, modifier = Modifier.padding(8.dp)) }
                }
                items(pacientes, key = { it.datos.mascota.id }) { p ->
                    TarjetaPaciente(p, vm.edad(p.datos.mascota), onClick = { onVerPaciente(p.datos.mascota.id) })
                }
            }
        }
    }
}

@Composable
private fun TarjetaPaciente(p: PacienteUi, edad: Int, onClick: () -> Unit) {
    val m = p.datos.mascota
    val cs = MaterialTheme.colorScheme
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cs.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            MascotaAvatar(Especie.desde(m.especie), tamano = 52.dp)
            Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(m.nombre, style = MaterialTheme.typography.titleSmall)
                Text("${m.raza} · ${textoEdad(edad)}", style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                Text("Dueño: ${p.datos.dueno.nombre}", style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                Text(
                    p.ultimaVisita?.let { "Última visita: ${Horarios.diaMes(it)}" } ?: "Sin visitas",
                    style = MaterialTheme.typography.labelSmall,
                    color = cs.primary
                )
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = cs.onSurfaceVariant)
        }
    }
}
