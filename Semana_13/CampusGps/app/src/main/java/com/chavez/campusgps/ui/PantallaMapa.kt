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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.chavez.campusgps.model.UbicacionUi
import com.chavez.campusgps.ui.components.FiltroCategorias
import com.chavez.campusgps.ui.components.SelectorRadio
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
    val categoria by vm.categoria.collectAsState()
    val radio by vm.radio.collectAsState()
    val soloFavoritos by vm.soloFavoritos.collectAsState()
    val contexto = LocalContext.current

    // ¿tiene FINE o COARSE?
    fun revisarPermiso(): Boolean = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ).any {
        ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED
    }

    // Estado "tengo permiso": se actualiza al responder el diálogo (sin reiniciar la app)
    var permisoConcedido by remember { mutableStateOf(revisarPermiso()) }

    // Si el usuario cambia el permiso desde Ajustes y vuelve, se vuelve a revisar
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        permisoConcedido = revisarPermiso()
    }

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        permisoConcedido = resultado.values.any { it }
        // si algún valor es true -> buscar; si no -> permiso denegado
        if (permisoConcedido) vm.buscar() else vm.permisoDenegado()
    }

    fun alPulsar() {
        // ¿ya tiene FINE o COARSE? -> buscar sin preguntar
        if (permisoConcedido) {
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

            FiltroCategorias(
                categorias = vm.categorias,
                seleccionada = categoria,
                onSeleccionar = { vm.filtrarPorCategoria(it) },
                soloFavoritos = soloFavoritos,
                onAlternarFavoritos = { vm.alternarMisLugares() },
                modifier = Modifier.fillMaxWidth()
            )

            SelectorRadio(
                seleccionado = radio,
                onSeleccionar = { vm.seleccionarRadio(it) }
            )

            MapaUbicacion(
                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                masCercanoId = masCercano?.lugar?.id,
                radio = radio,
                permisoConcedido = permisoConcedido,
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
                is UbicacionUi.Error -> MensajeError(error = e, onReintentar = { alPulsar() })
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
                onComoLlegar = { abrirRutaAPie(contexto, it) },
                onFavorito = { vm.alternarFavorito(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }
    }
}