package com.chavez.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

// concedido: ¿la app tiene permiso de ubicación? pedir(): lo usa si ya lo tiene, si no lanza el diálogo.
class PermisoUbicacion(val concedido: Boolean, val pedir: () -> Unit)

private val PERMISOS = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

// Maneja el permiso de ubicación: si ya está concedido llama a onConcedido sin preguntar;
// si no, muestra el diálogo y llama a onConcedido u onDenegado según la respuesta.
@Composable
fun rememberPermisoUbicacion(
    onConcedido: () -> Unit,
    onDenegado: () -> Unit
): PermisoUbicacion {
    val contexto = LocalContext.current
    val alConceder by rememberUpdatedState(onConcedido)
    val alDenegar by rememberUpdatedState(onDenegado)

    // ¿tiene FINE o COARSE?
    fun revisar(): Boolean = PERMISOS.any {
        ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED
    }

    // Se actualiza al responder el diálogo (sin reiniciar la app)
    var concedido by remember { mutableStateOf(revisar()) }

    // Si el usuario cambia el permiso desde Ajustes y vuelve, se vuelve a revisar
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { concedido = revisar() }

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        concedido = resultado.values.any { it }
        if (concedido) alConceder() else alDenegar()
    }

    return PermisoUbicacion(
        concedido = concedido,
        pedir = { if (concedido) alConceder() else lanzador.launch(PERMISOS) }
    )
}
