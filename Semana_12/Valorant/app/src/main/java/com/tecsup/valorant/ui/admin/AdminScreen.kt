package com.tecsup.valorant.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.components.BotonCortado
import com.tecsup.valorant.ui.components.FormaTarjeta
import com.tecsup.valorant.ui.theme.FuenteTecnica
import com.tecsup.valorant.ui.theme.ValoCoral
import com.tecsup.valorant.ui.theme.ValoFondo
import com.tecsup.valorant.ui.theme.ValoLavanda
import com.tecsup.valorant.ui.theme.ValoLinea
import com.tecsup.valorant.ui.theme.ValoMagenta
import com.tecsup.valorant.ui.theme.ValoSuperficie
import com.tecsup.valorant.ui.theme.ValoTexto
import com.tecsup.valorant.ui.theme.ValoTextoSuave

private val FormaBotonGrande = CutCornerShape(topEnd = 14.dp, bottomStart = 14.dp)

@Composable
fun AdminScreen(vm: AdminViewModel, onVolver: () -> Unit) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    val mensaje by vm.mensaje.collectAsStateWithLifecycle()

    Surface(color = ValoFondo, contentColor = ValoTexto, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Encabezado
            Row(verticalAlignment = Alignment.CenterVertically) {
                BotonCortado(onClick = onVolver) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = ValoTexto, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.width(12.dp))
                Text("// PANEL DE CONTROL", style = MaterialTheme.typography.labelSmall, color = ValoCoral)
            }
            Text("ADMIN", style = MaterialTheme.typography.headlineLarge)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            0f to ValoCoral, 0.4f to ValoMagenta, 0.7f to ValoLavanda, 1f to Color.Transparent
                        )
                    )
            )

            // Totales
            when (val e = estado) {
                is UiState.Cargando -> CircularProgressIndicator(color = ValoMagenta)
                is UiState.Error -> Text(e.mensaje, color = MaterialTheme.colorScheme.error)
                is UiState.Exito -> Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    TarjetaTotal("AGENTES\nGUARDADOS", e.datos.guardados, ValoCoral, Modifier.weight(1f))
                    TarjetaTotal("FAVORITOS", e.datos.favoritos, ValoMagenta, Modifier.weight(1f))
                }
            }

            Spacer(Modifier.height(4.dp))

            // Sincronizar: botón principal con degradado
            Button(
                onClick = vm::sincronizar,
                shape = FormaBotonGrande,
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(Brush.horizontalGradient(listOf(ValoCoral, ValoMagenta)), FormaBotonGrande)
            ) {
                Text("SINCRONIZAR", fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            }

            // Limpiar caché: botón secundario solo con borde
            OutlinedButton(
                onClick = vm::limpiarCache,
                shape = FormaBotonGrande,
                border = BorderStroke(1.dp, ValoLinea),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ValoTexto),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text("LIMPIAR CACHÉ", fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            }

            // Mensaje de estado tipo consola
            mensaje?.let {
                Text(
                    "> $it",
                    fontFamily = FuenteTecnica,
                    fontSize = 13.sp,
                    color = ValoTextoSuave
                )
            }
        }
    }
}

@Composable
private fun TarjetaTotal(titulo: String, valor: Int, acento: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = FormaTarjeta,
        color = ValoSuperficie,
        border = BorderStroke(1.dp, ValoLinea),
        modifier = modifier
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo, style = MaterialTheme.typography.labelSmall, color = ValoTextoSuave)
            Spacer(Modifier.height(8.dp))
            Text(
                "$valor",
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = acento
            )
        }
    }
}
