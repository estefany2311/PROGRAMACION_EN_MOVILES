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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.ui.components.graficos.GraficoBarras
import com.tecsup.huellitasvet.ui.components.graficos.GraficoDona
import com.tecsup.huellitasvet.ui.components.soles
import com.tecsup.huellitasvet.ui.theme.Ambar
import com.tecsup.huellitasvet.ui.theme.AmbarContenedor
import com.tecsup.huellitasvet.ui.theme.Gris
import com.tecsup.huellitasvet.ui.theme.GrisContenedor
import com.tecsup.huellitasvet.viewmodel.PanelViewModel
import java.time.LocalDate

@Composable
fun PanelScreen(vm: PanelViewModel, onVerPendientes: () -> Unit) {
    val citasHoy by vm.citasHoy.collectAsStateWithLifecycle()
    val pendientes by vm.pendientes.collectAsStateWithLifecycle()
    val atendidas by vm.atendidas.collectAsStateWithLifecycle()
    val ingresos by vm.ingresosMes.collectAsStateWithLifecycle()
    val atencionesMes by vm.atencionesMes.collectAsStateWithLifecycle()
    val porConfirmar by vm.porConfirmar.collectAsStateWithLifecycle()
    val porEstado by vm.porEstado.collectAsStateWithLifecycle()
    val porServicio by vm.porServicio.collectAsStateWithLifecycle()
    val cs = MaterialTheme.colorScheme

    Column(
        Modifier
            .fillMaxSize()
            .background(cs.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Panel de la clínica", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Hoy · ${Horarios.fechaCorta(LocalDate.now().toString())}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = cs.onSurfaceVariant
                )
            }
            Icon(Icons.Outlined.Notifications, null, tint = cs.onSurfaceVariant)
        }
        Spacer(Modifier.height(16.dp))

        // 3 contadores de color
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Contador(citasHoy, "Citas hoy", cs.primary, cs.primaryContainer, Modifier.weight(1f))
            Contador(pendientes, "Pendientes", Ambar, AmbarContenedor, Modifier.weight(1f))
            Contador(atendidas, "Atendidas", Gris, GrisContenedor, Modifier.weight(1f))
        }
        Spacer(Modifier.height(12.dp))

        // Ingresos del mes
        Surface(shape = RoundedCornerShape(18.dp), color = cs.primary, contentColor = cs.onPrimary, modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Ingresos del mes", style = MaterialTheme.typography.labelMedium, color = cs.onPrimary.copy(alpha = 0.8f))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(soles(ingresos), style = MaterialTheme.typography.headlineSmall, modifier = Modifier.weight(1f))
                    Text("$atencionesMes atenciones", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        Seccion("Citas por estado") { GraficoDona(porEstado) }
        Spacer(Modifier.height(16.dp))
        Seccion("Citas por servicio") { GraficoBarras(porServicio) }
        Spacer(Modifier.height(16.dp))

        // Alerta de citas por confirmar
        if (porConfirmar > 0) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(AmbarContenedor, RoundedCornerShape(16.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Warning, null, tint = Ambar, modifier = Modifier.size(22.dp))
                Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                    Text(
                        "$porConfirmar ${if (porConfirmar == 1) "cita" else "citas"} por confirmar",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFF92400E)
                    )
                    Text("para hoy y mañana", style = MaterialTheme.typography.bodySmall, color = Color(0xFF92400E))
                }
                Button(
                    onClick = onVerPendientes,
                    colors = ButtonDefaults.buttonColors(containerColor = Ambar),
                    shape = RoundedCornerShape(50)
                ) { Text("Ver") }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun Contador(valor: Int, etiqueta: String, color: Color, fondo: Color, modifier: Modifier) {
    Column(
        modifier
            .background(fondo, RoundedCornerShape(16.dp))
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(valor.toString(), style = MaterialTheme.typography.headlineMedium, color = color, fontWeight = FontWeight.Bold)
        Text(etiqueta, style = MaterialTheme.typography.labelSmall, color = color)
    }
}

@Composable
private fun Seccion(titulo: String, contenido: @Composable () -> Unit) {
    Text(titulo, style = MaterialTheme.typography.titleSmall)
    Spacer(Modifier.height(8.dp))
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) { contenido() }
    }
}
