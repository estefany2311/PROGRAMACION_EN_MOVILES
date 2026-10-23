package com.tecsup.huellitasvet.ui.dueno

import androidx.compose.foundation.background
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.model.Rol
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.theme.Ambar
import com.tecsup.huellitasvet.ui.theme.AmbarContenedor
import com.tecsup.huellitasvet.ui.theme.Azul
import com.tecsup.huellitasvet.ui.theme.AzulContenedor
import com.tecsup.huellitasvet.ui.theme.Rojo

// Perfil del dueño: datos, contadores, preferencias y cambio de perfil
@Composable
fun PerfilScreen(
    usuario: Usuario?,
    mascotas: Int,
    proximas: Int,
    atenciones: Int,
    modoOscuro: Boolean,
    recordatorios: Boolean,
    onModoOscuro: (Boolean) -> Unit,
    onRecordatorios: (Boolean) -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    Scaffold(topBar = { AppTopBar("Perfil") }, containerColor = cs.background) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            TarjetaUsuario(usuario)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Contador(mascotas, "Mascotas", cs.primary, cs.primaryContainer, Modifier.weight(1f))
                Contador(proximas, "Próximas", Ambar, AmbarContenedor, Modifier.weight(1f))
                Contador(atenciones, "Atenciones", Azul, AzulContenedor, Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
            TarjetaCambioPerfil("Modo práctica: ir a la vista Veterinario", onCambiarPerfil)
            Spacer(Modifier.height(16.dp))
            Text("Preferencias", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            TarjetaPreferencias {
                FilaSwitch("Modo oscuro", "Cambia el tema de la app", modoOscuro, onModoOscuro)
                HorizontalDivider(color = cs.outlineVariant)
                FilaSwitch("Recordatorios", "Avisar citas y vacunas", recordatorios, onRecordatorios)
            }
            NotaDataStore()
            Spacer(Modifier.height(16.dp))
            VetButton("Cerrar sesión", onClick = onCerrarSesion, contorno = true, color = Rojo)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun TarjetaUsuario(usuario: Usuario?, etiquetaRol: String? = null) {
    val cs = MaterialTheme.colorScheme
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cs.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(52.dp).background(cs.primaryContainer, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Person, null, tint = cs.primary, modifier = Modifier.size(28.dp))
            }
            Column(Modifier.padding(start = 14.dp)) {
                Text(usuario?.nombre ?: "", style = MaterialTheme.typography.titleMedium)
                Text(usuario?.correo ?: "", style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                Spacer(Modifier.height(4.dp))
                Text(
                    etiquetaRol ?: usuario?.let { Rol.valueOf(it.rol).etiqueta } ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    color = cs.primary,
                    modifier = Modifier.background(cs.primaryContainer, RoundedCornerShape(50)).padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}

// Tarjeta del color del rol para cambiar de perfil
@Composable
fun TarjetaCambioPerfil(detalle: String, onClick: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Surface(onClick = onClick, shape = RoundedCornerShape(16.dp), color = cs.primary, contentColor = cs.onPrimary,
        modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.SwapHoriz, null)
            Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text("Cambiar de perfil", style = MaterialTheme.typography.titleSmall)
                Text(detalle, style = MaterialTheme.typography.bodySmall, color = cs.onPrimary.copy(alpha = 0.85f))
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
        }
    }
}

@Composable
fun TarjetaPreferencias(contenido: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) { Column(Modifier.padding(horizontal = 16.dp)) { contenido() } }
}

@Composable
fun FilaSwitch(titulo: String, detalle: String, valor: Boolean, onCambio: (Boolean) -> Unit) {
    // Toda la fila cambia el switch
    Row(
        Modifier
            .fillMaxWidth()
            .toggleable(value = valor, role = Role.Switch, onValueChange = onCambio)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(titulo, style = MaterialTheme.typography.titleSmall)
            Text(detalle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = valor, onCheckedChange = null)
    }
}

@Composable
fun NotaDataStore() {
    val cs = MaterialTheme.colorScheme
    Text(
        "Se guardan en el dispositivo con DataStore",
        style = MaterialTheme.typography.labelSmall,
        color = cs.primary,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .background(cs.primaryContainer.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .padding(10.dp)
    )
}

@Composable
private fun Contador(valor: Int, etiqueta: String, color: Color, fondo: Color, modifier: Modifier) {
    Column(
        modifier.background(fondo, RoundedCornerShape(16.dp)).padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(valor.toString(), style = MaterialTheme.typography.headlineMedium, color = color, fontWeight = FontWeight.Bold)
        Text(etiqueta, style = MaterialTheme.typography.labelSmall, color = color)
    }
}
