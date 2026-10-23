package com.tecsup.huellitasvet.ui.veterinario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.dueno.FilaSwitch
import com.tecsup.huellitasvet.ui.dueno.NotaDataStore
import com.tecsup.huellitasvet.ui.dueno.TarjetaCambioPerfil
import com.tecsup.huellitasvet.ui.dueno.TarjetaPreferencias
import com.tecsup.huellitasvet.ui.dueno.TarjetaUsuario
import com.tecsup.huellitasvet.ui.theme.Rojo

// Ajustes del veterinario
@Composable
fun AjustesScreen(
    usuario: Usuario?,
    modoOscuro: Boolean,
    notificaciones: Boolean,
    aceptarCitas: Boolean,
    onModoOscuro: (Boolean) -> Unit,
    onNotificaciones: (Boolean) -> Unit,
    onAceptarCitas: (Boolean) -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    Scaffold(topBar = { AppTopBar("Ajustes") }, containerColor = cs.background) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            TarjetaUsuario(usuario, etiquetaRol = "Perfil activo")
            Spacer(Modifier.height(12.dp))
            TarjetaCambioPerfil("Modo práctica: ir a la vista Dueño", onCambiarPerfil)
            Spacer(Modifier.height(16.dp))
            Text("Preferencias", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            TarjetaPreferencias {
                FilaSwitch("Modo oscuro", "Cambia el tema de la app", modoOscuro, onModoOscuro)
                HorizontalDivider(color = cs.outlineVariant)
                FilaSwitch("Notificaciones", "Avisar citas nuevas", notificaciones, onNotificaciones)
                HorizontalDivider(color = cs.outlineVariant)
                FilaSwitch("Aceptar citas", "Abrir o cerrar la agenda", aceptarCitas, onAceptarCitas)
            }
            NotaDataStore()
            Spacer(Modifier.height(16.dp))
            VetButton("Cerrar sesión", onClick = onCerrarSesion, contorno = true, color = Rojo)
            Spacer(Modifier.height(20.dp))
        }
    }
}
