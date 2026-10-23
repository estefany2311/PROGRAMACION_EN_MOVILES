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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.model.Rol
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.components.VetTextField
import com.tecsup.huellitasvet.ui.theme.VetTheme
import com.tecsup.huellitasvet.viewmodel.SesionViewModel

// Un solo Composable para ambos roles: el color cambia segun la pestaña elegida
@Composable
fun LoginScreen(vm: SesionViewModel, onExito: (Usuario) -> Unit) {
    val usuario by vm.usuarioActual.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val oscuro by vm.modoOscuro.collectAsStateWithLifecycle()
    var rol by rememberSaveable { mutableStateOf(Rol.CLIENTE) }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(usuario) { usuario?.let(onExito) }

    VetTheme(rol, oscuro) {
        val cs = MaterialTheme.colorScheme
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(cs.background)
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(Modifier.height(24.dp))
            Text("Bienvenido", style = MaterialTheme.typography.headlineMedium, color = cs.onBackground)
            Text("Ingresa para continuar", style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant)
            Spacer(Modifier.height(28.dp))

            SelectorRol(rol) {
                rol = it
                vm.limpiarError()
            }
            Spacer(Modifier.height(24.dp))

            VetTextField(
                valor = correo,
                onValorChange = { correo = it; vm.limpiarError() },
                etiqueta = "Correo",
                icono = Icons.Outlined.Email,
                teclado = KeyboardType.Email
            )
            Spacer(Modifier.height(8.dp))
            VetTextField(
                valor = clave,
                onValorChange = { clave = it; vm.limpiarError() },
                etiqueta = "Contraseña",
                icono = Icons.Outlined.Lock,
                esClave = true
            )
            Text(
                "¿Olvidaste tu contraseña?",
                style = MaterialTheme.typography.labelMedium,
                color = cs.primary,
                modifier = Modifier.align(Alignment.End).padding(vertical = 8.dp)
            )

            if (error != null) {
                Text(
                    error!!,
                    color = cs.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(cs.error.copy(alpha = 0.08f), RoundedCornerShape(10.dp))
                        .padding(12.dp)
                )
                Spacer(Modifier.height(12.dp))
            }

            VetButton("Ingresar", onClick = { vm.login(correo, clave, rol) })
            Spacer(Modifier.height(24.dp))

            Surface(shape = RoundedCornerShape(14.dp), color = cs.primaryContainer.copy(alpha = 0.6f)) {
                Column(Modifier.fillMaxWidth().padding(14.dp)) {
                    Text("Modo práctica", style = MaterialTheme.typography.titleSmall, color = cs.primary)
                    Text(
                        "Ambos perfiles conviven en este dispositivo. Puedes cambiar de perfil desde Perfil o Ajustes.",
                        style = MaterialTheme.typography.bodySmall,
                        color = cs.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Pestañas Dueño / Veterinario
@Composable
private fun SelectorRol(seleccionado: Rol, onCambio: (Rol) -> Unit) {
    val cs = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cs.surfaceVariant, RoundedCornerShape(14.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Rol.entries.forEach { r ->
            val activo = r == seleccionado
            Surface(
                onClick = { onCambio(r) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                color = if (activo) cs.primary else cs.surfaceVariant,
                contentColor = if (activo) cs.onPrimary else cs.onSurfaceVariant
            ) {
                Box(Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                    Text(r.etiqueta, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
