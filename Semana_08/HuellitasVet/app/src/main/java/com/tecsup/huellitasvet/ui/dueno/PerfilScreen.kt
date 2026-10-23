package com.tecsup.huellitasvet.ui.dueno

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.model.Rol
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.theme.Rojo

// Perfil del dueño (se completa en el Paso 13 con contadores, preferencias y cambio de perfil)
@Composable
fun PerfilScreen(usuario: Usuario?, onCerrarSesion: () -> Unit) {
    Scaffold(
        topBar = { AppTopBar("Perfil") },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp)) {
            TarjetaUsuario(usuario)
            Spacer(Modifier.height(24.dp))
            VetButton("Cerrar sesión", onClick = onCerrarSesion, contorno = true, color = Rojo)
        }
    }
}

@Composable
fun TarjetaUsuario(usuario: Usuario?) {
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
                    usuario?.let { Rol.valueOf(it.rol).etiqueta } ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    color = cs.primary,
                    modifier = Modifier.background(cs.primaryContainer, RoundedCornerShape(50)).padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}
