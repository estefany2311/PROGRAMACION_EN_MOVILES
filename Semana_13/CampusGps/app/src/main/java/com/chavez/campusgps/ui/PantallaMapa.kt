package com.chavez.campusgps.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chavez.campusgps.model.UbicacionUi
import com.chavez.campusgps.ui.components.BarraAcciones
import com.chavez.campusgps.ui.components.BarraSuperiorFlotante
import com.chavez.campusgps.ui.components.EstadoUbicacion
import com.chavez.campusgps.ui.components.FiltroCategorias
import com.chavez.campusgps.ui.components.PanelInferior
import com.chavez.campusgps.ui.components.SelectorRadio
import com.chavez.campusgps.viewmodel.MapaViewModel

// Pantalla principal: mapa a pantalla completa con la barra arriba y el panel abajo flotando.
// Solo arma los componentes y los conecta con el ViewModel.
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

    val permiso = rememberPermisoUbicacion(
        onConcedido = { vm.buscar() },
        onDenegado = { vm.permisoDenegado() }
    )

    // Alto de lo que flota arriba y abajo: se usa como padding del mapa para que el logo de Google,
    // el zoom y el botón de mi ubicación no queden tapados
    val densidad = LocalDensity.current
    var altoSuperior by remember { mutableStateOf(0.dp) }
    var altoInferior by remember { mutableStateOf(0.dp) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val altoMaximoPanel = maxHeight * 0.45f

        // Fondo: mapa a pantalla completa
        MapaUbicacion(
            estado = estado,
            lugares = lugares,
            seleccionado = seleccionado,
            masCercanoId = masCercano?.lugar?.id,
            radio = radio,
            permisoConcedido = permiso.concedido,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = altoSuperior, bottom = altoInferior)
        )

        // Arriba: título, filtros y radio
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .onSizeChanged { altoSuperior = with(densidad) { it.height.toDp() } }
                .statusBarsPadding()
                .padding(top = 16.dp)
        ) {
            BarraSuperiorFlotante(
                titulo = "CampusGPS",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
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
                onSeleccionar = { vm.seleccionarRadio(it) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Abajo: acciones, estado y lista
        PanelInferior(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                // se mide con los márgenes incluidos: es lo que el mapa debe dejar libre abajo
                .onSizeChanged { altoInferior = with(densidad) { it.height.toDp() } }
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .padding(bottom = 16.dp)
                .wrapContentHeight()
                .heightIn(max = altoMaximoPanel)
        ) {
            BarraAcciones(
                buscando = estado is UbicacionUi.Buscando,
                hayMasCercano = masCercano != null,
                onBuscar = permiso.pedir,
                onIrAlMasCercano = { masCercano?.let { vm.seleccionar(it.lugar.id) } }
            )
            EstadoUbicacion(
                estado = estado,
                masCercano = masCercano,
                onReintentar = permiso.pedir
            )
            ListaLugares(
                lugares = lugares,
                seleccionado = seleccionado,
                onClick = { vm.seleccionar(it) },
                onComoLlegar = { abrirRutaAPie(contexto, it) },
                onFavorito = { vm.alternarFavorito(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )
        }
    }
}
