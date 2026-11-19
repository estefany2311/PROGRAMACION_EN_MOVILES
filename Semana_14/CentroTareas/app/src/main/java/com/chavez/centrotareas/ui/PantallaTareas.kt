package com.chavez.centrotareas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chavez.centrotareas.model.CargaUi
import com.chavez.centrotareas.ui.components.SwitchEtiquetado
import com.chavez.centrotareas.model.PrimosUi
import com.chavez.centrotareas.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaProgreso(viewModel)
            TarjetaPrimos(viewModel)
            TarjetaCargas(viewModel)
        }
    }
}

@Composable
private fun TarjetaProgreso(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    val progreso by viewModel.progreso.collectAsState()
    val corriendo by viewModel.corriendo.collectAsState()

    var toques by remember { mutableIntStateOf(0) }
//paso1
    TarjetaTarea(titulo = "Tarea con progreso") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { toques++ }) { Text("Tócame: $toques") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.tareaLarga() }, enabled = !corriendo) {
                Text("Iniciar")
            }
            OutlinedButton(onClick = { viewModel.cancelar() }, enabled = corriendo) {
                Text("Cancelar")
            }
        }
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "${(progreso * 100).toInt()} %",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = mensaje, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun TarjetaPrimos(viewModel: TareasViewModel) {
    val primos by viewModel.primos.collectAsState()
    var texto by remember { mutableStateOf("500000") }
    val numero = texto.toIntOrNull()?.takeIf { it >= 2 } // ignora texto no numérico o < 2
    val calculando = primos == PrimosUi.Calculando

    TarjetaTarea(titulo = "Contar números primos") {
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Hasta") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { numero?.let { viewModel.contar(it) } },
                enabled = numero != null && !calculando
            ) { Text("Contar") }
            OutlinedButton(
                onClick = { viewModel.cancelarPrimos() },
                enabled = calculando
            ) { Text("Cancelar") }
        }
        when (val estado = primos) {
            PrimosUi.Inicial -> Unit
            PrimosUi.Calculando -> Text("Calculando...", style = MaterialTheme.typography.bodyMedium)
            PrimosUi.Cancelado -> Text("Cancelado", style = MaterialTheme.typography.bodyMedium)
            is PrimosUi.Listo -> CajaResultado(
                texto = "${estado.cantidad} primos en ${estado.milisegundos} ms",
                fondo = MaterialTheme.colorScheme.tertiaryContainer,
                contenido = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
private fun TarjetaCargas(viewModel: TareasViewModel) {
    val carga by viewModel.carga.collectAsState()
    val simularError by viewModel.simularError.collectAsState()
    val limiteActivo by viewModel.limiteActivo.collectAsState()
    val aviso by viewModel.aviso.collectAsState() //Mej3
    val cargando = carga == CargaUi.Cargando

    TarjetaTarea(titulo = "Dos cargas") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { viewModel.cargarUnaPorUna() }, enabled = !cargando) {
                Text("Una por una")
            }
            Button(onClick = { viewModel.cargarALaVez() }, enabled = !cargando) {
                Text("A la vez")
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = simularError,
                onCheckedChange = { viewModel.cambiarSimularError(it) }
            )
            Spacer(Modifier.width(8.dp))
            Text("Simular error", style = MaterialTheme.typography.bodyMedium)
        }
        SwitchEtiquetado(
            texto = "Límite de 2 s",
            checked = limiteActivo,
            onCheckedChange = { viewModel.cambiarLimite(it) }
        )
        //mej3
        when (val estado = carga) {
            CargaUi.Cargando -> {
                CircularProgressIndicator()
                aviso?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            }
            is CargaUi.Exito -> CajaResultado(
                texto = "Modo: ${estado.modo}\nPerfil: ${estado.perfil}\n" +
                    "Pedidos: ${estado.pedidos}\nTiempo: ${estado.milisegundos} ms",
                fondo = MaterialTheme.colorScheme.tertiaryContainer,
                contenido = MaterialTheme.colorScheme.onTertiaryContainer
            )
            is CargaUi.Error -> {
                CajaResultado(
                    texto = estado.mensaje,
                    fondo = MaterialTheme.colorScheme.errorContainer,
                    contenido = MaterialTheme.colorScheme.onErrorContainer
                )
                Button(onClick = { viewModel.cargarALaVez() }) { Text("Reintentar") }
            }
            CargaUi.Inicial -> Unit
        }
    }
}

@Composable
private fun CajaResultado(texto: String, fondo: Color, contenido: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(fondo, MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        Text(text = texto, color = contenido, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun TarjetaTarea(
    titulo: String,
    contenido: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            contenido()
        }
    }
}
