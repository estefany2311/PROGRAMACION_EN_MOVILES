package com.tecsup.huellitasvet.ui.dueno

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.relation.CitaConDetalle
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Horarios
import com.tecsup.huellitasvet.ui.components.IconoServicio
import com.tecsup.huellitasvet.ui.components.MascotaAvatar
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.viewmodel.InicioViewModel

@Composable
fun InicioScreen(
    vm: InicioViewModel,
    nombre: String,
    onVerMascota: (Int) -> Unit,
    onAgregarMascota: () -> Unit,
    onVerMascotas: () -> Unit,
    onPedirCita: () -> Unit
) {
    val mascotas by vm.mascotas.collectAsStateWithLifecycle()
    val proxima by vm.proximaCita.collectAsStateWithLifecycle()
    val servicios by vm.servicios.collectAsStateWithLifecycle()
    val cs = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cs.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Hola, ${nombre.substringBefore(" ")}", style = MaterialTheme.typography.headlineSmall, color = cs.onBackground)
                Text("¿Cómo están tus mascotas hoy?", style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant)
            }
            Icon(Icons.Outlined.Notifications, contentDescription = null, tint = cs.onSurfaceVariant)
        }
        Spacer(Modifier.height(16.dp))

        TarjetaProximaCita(proxima)
        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Mis mascotas", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            TextButton(onClick = onVerMascotas) { Text("Ver todas") }
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 4.dp)) {
            items(mascotas, key = { it.id }) { m ->
                TarjetaMascotaMini(m, onClick = { onVerMascota(m.id) })
            }
            item { TarjetaAgregar(onAgregarMascota) }
        }
        Spacer(Modifier.height(20.dp))

        Text("Servicios", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            servicios.take(3).forEach { s ->
                Card(
                    onClick = onPedirCita,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = cs.surface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(
                        Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconoServicio(s.icono, tamano = 36.dp)
                        Spacer(Modifier.height(6.dp))
                        Text(s.nombre, style = MaterialTheme.typography.labelMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        VetButton("Pedir una cita", onClick = onPedirCita)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun TarjetaProximaCita(proxima: CitaConDetalle?) {
    val cs = MaterialTheme.colorScheme
    Surface(shape = RoundedCornerShape(20.dp), color = cs.primary, contentColor = cs.onPrimary, modifier = Modifier.fillMaxWidth()) {
        if (proxima == null) {
            Column(Modifier.padding(18.dp)) {
                Text("Próxima cita", style = MaterialTheme.typography.labelMedium, color = cs.onPrimary.copy(alpha = 0.8f))
                Spacer(Modifier.height(4.dp))
                Text("Aún no tienes citas", style = MaterialTheme.typography.titleMedium)
                Text("Pide una cita para tu mascota", style = MaterialTheme.typography.bodySmall, color = cs.onPrimary.copy(alpha = 0.8f))
            }
        } else {
            val estado = EstadoCita.desde(proxima.cita.estado)
            Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Próxima cita", style = MaterialTheme.typography.labelMedium, color = cs.onPrimary.copy(alpha = 0.8f))
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "${proxima.mascota.mascota.nombre} · ${proxima.servicio.nombre}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.AccessTime, null, modifier = Modifier.size(14.dp))
                        Text(
                            " ${Horarios.fechaCorta(proxima.cita.fecha)} · ${proxima.cita.hora}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        estado.etiqueta,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = cs.primary,
                        modifier = Modifier
                            .background(cs.onPrimary, RoundedCornerShape(50))
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    )
                }
                Box(Modifier.background(Color.White, RoundedCornerShape(14.dp)).padding(4.dp)) {
                    MascotaAvatar(Especie.desde(proxima.mascota.mascota.especie), tamano = 48.dp)
                }
            }
        }
    }
}

@Composable
private fun TarjetaMascotaMini(m: Mascota, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(96.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MascotaAvatar(Especie.desde(m.especie), tamano = 52.dp)
            Spacer(Modifier.height(6.dp))
            Text(m.nombre, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Text("Ver ficha", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun TarjetaAgregar(onClick: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        modifier = Modifier.width(96.dp).height(118.dp),
        shape = RoundedCornerShape(16.dp),
        color = cs.surface,
        border = BorderStroke(1.5.dp, cs.primary)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Filled.Add, contentDescription = null, tint = cs.primary, modifier = Modifier.size(28.dp))
            Text("Agregar", style = MaterialTheme.typography.labelMedium, color = cs.primary)
        }
    }
}
