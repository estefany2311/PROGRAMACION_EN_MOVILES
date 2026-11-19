package com.chavez.campusgps.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chavez.campusgps.R
import com.chavez.campusgps.model.LugarConDistancia
import com.chavez.campusgps.model.UbicacionUi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    masCercanoId: Int?,
    radio: Int?,
    permisoConcedido: Boolean,
    modifier: Modifier = Modifier
) {
    val colorRadio = MaterialTheme.colorScheme.primary
    val colorLinea = MaterialTheme.colorScheme.tertiary
    val lugarSeleccionado = lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }

    // mej6: ¿el celular está en modo oscuro? y contexto para leer el estilo desde res/raw
    val oscuro = isSystemInDarkTheme()
    val contexto = LocalContext.current

    //  cuando llega la ubicación, la cámara viaja hasta ahí
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(estado.posicion.latitud, estado.posicion.longitud),
                    16f
                )
            )
        }
    }

    // al seleccionar un lugar, la cámara viaja hasta él
    LaunchedEffect(seleccionado) {
        val lugar = lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar
        if (lugar != null) {
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(LatLng(lugar.latitud, lugar.longitud), 17f)
            )
        }
    }

    // Punto azul nativo y botón "mi ubicación" mej3
    // mej6: estilo oscuro del mapa (res/raw/map_dark.json) solo si el tema es oscuro
    GoogleMap(
        modifier = modifier,
        cameraPositionState = camara,
        properties = MapProperties(
            isMyLocationEnabled = permisoConcedido,
            mapStyleOptions = if (oscuro) {
                MapStyleOptions.loadRawResourceStyle(contexto, R.raw.map_dark)
            } else {
                null
            }
        ),
        uiSettings = MapUiSettings(myLocationButtonEnabled = permisoConcedido)
    ) {
        //  marcador "Estás aquí" solo si ya hay ubicación
        if (estado is UbicacionUi.Lista) {
            Marker(
                state = rememberUpdatedMarkerState(
                    position = LatLng(estado.posicion.latitud, estado.posicion.longitud)
                ),
                title = "Estás aquí"
            )
        }
        // línea punteada RECTA (no es ruta por calles) entre mi posición y el lugar seleccionado
        if (estado is UbicacionUi.Lista && lugarSeleccionado != null) {
            Polyline(
                points = listOf(
                    LatLng(estado.posicion.latitud, estado.posicion.longitud),
                    LatLng(lugarSeleccionado.latitud, lugarSeleccionado.longitud)
                ),
                color = colorLinea,
                width = 8f,
                pattern = listOf(Dash(24f), Gap(16f))
            )
        }
        // círculo del radio alrededor de mi posición (solo con ubicación y radio elegido)
        if (estado is UbicacionUi.Lista && radio != null) {
            Circle(
                center = LatLng(estado.posicion.latitud, estado.posicion.longitud),
                radius = radio.toDouble(),
                strokeColor = colorRadio,
                strokeWidth = 4f,
                fillColor = colorRadio.copy(alpha = 0.15f)
            )
        }
        // un marcador azul por cada lugar
        lugares.forEach { item ->
            key(item.lugar.id) {
                Marker(
                    state = rememberUpdatedMarkerState(
                        position = LatLng(item.lugar.latitud, item.lugar.longitud)
                    ),
                    title = item.lugar.nombre,
                    snippet = item.lugar.categoria,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (item.lugar.id == masCercanoId) {
                            BitmapDescriptorFactory.HUE_GREEN
                        } else {
                            BitmapDescriptorFactory.HUE_AZURE
                        }
                    )
                )
            }
        }
    }
}