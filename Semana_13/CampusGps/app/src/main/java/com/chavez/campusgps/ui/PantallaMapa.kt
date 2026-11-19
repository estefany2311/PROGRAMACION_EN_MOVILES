package com.chavez.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.chavez.campusgps.model.UbicacionUi
import com.chavez.campusgps.util.formatearDistancia
import com.chavez.campusgps.viewmodel.MapaViewModel

// Pantalla principal: mapa arriba, botón y texto debajo.
// Los siguientes pasos agregan lista, más cercano y errores.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugares.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val masCercano by vm.masCercano.collectAsState()
    val contexto = LocalContext.current

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        // si algún valor es true -> buscar; si no -> permiso denegado
        if (resultado.values.any { it }) vm.buscar() else vm.permisoDenegado()
    }

    fun alPulsar() {
        // ¿ya tiene FINE o COARSE? -> buscar sin preguntar
        val tienePermiso = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).any {
            ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED
        }
        if (tienePermiso) {
            vm.buscar()
        } else {
            // si no, pedir los permisos
            lanzador.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CampusGPS") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MapaUbicacion(
                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                masCercanoId = masCercano?.lugar?.id,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            // Botón y texto debajo del mapa
            Button(
                onClick = { alPulsar() },
                enabled = estado !is UbicacionUi.Buscando,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Obtener mi ubicación")
            }

            when (val e = estado) {
                UbicacionUi.Inicial -> Text("Pulsa el botón para obtener tu ubicación")
                UbicacionUi.Buscando -> Text("Buscando…")
                is UbicacionUi.Lista ->
                    Text("Lat: ${e.posicion.latitud}  Lon: ${e.posicion.longitud}")
                is UbicacionUi.Error -> Text(e.mensaje, color = MaterialTheme.colorScheme.error)
            }

            OutlinedButton(
                onClick = { masCercano?.let { vm.seleccionar(it.lugar.id) } },
                enabled = masCercano != null
            ) {
                Text("Ir al más cercano")
            }

            masCercano?.let { cercano ->
                Text(
                    "Más cercano: ${cercano.lugar.nombre} " +
                        "(${cercano.metros?.let { formatearDistancia(it) } ?: "Sin calcular"})"
                )
            }

            // Lista con altura fija debajo; al tocar una tarjeta se selecciona el lugar
            ListaLugares(
                lugares = lugares,
                seleccionado = seleccionado,
                onClick = { vm.seleccionar(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }
    }
}