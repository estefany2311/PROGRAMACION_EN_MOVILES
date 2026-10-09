package com.saludplus.citas.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas

private data class DestinoNavegacion(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
)

@Composable
fun BotonPrincipal(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = texto)
    }
}

@Composable
fun CampoTexto(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    icono: ImageVector,
    modifier: Modifier = Modifier,
    error: String? = null,
    teclado: KeyboardType = KeyboardType.Text,
    oculto: Boolean = false
) {
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        leadingIcon = { Icon(imageVector = icono, contentDescription = null) },
        isError = error != null,
        supportingText = {
            if (error != null) {
                Text(text = error)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        visualTransformation = if (oculto) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun TarjetaAcceso(
    titulo: String,
    icono: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun BarraInferior(
    rutaActual: String,
    navController: NavController
) {
    val destinos = listOf(
        DestinoNavegacion(Rutas.HOME, "Inicio", Icons.Default.Home),
        DestinoNavegacion(Rutas.MIS_CITAS, "Citas", Icons.Default.CalendarMonth),
        DestinoNavegacion(Rutas.RESULTADOS, "Resultados", Icons.Default.Description),
        DestinoNavegacion(Rutas.PERFIL, "Perfil", Icons.Default.Person)
    )

    NavigationBar {
        destinos.forEach { destino ->
            NavigationBarItem(
                selected = rutaActual == destino.ruta,
                onClick = {
                    if (rutaActual != destino.ruta) {
                        navController.navigate(destino.ruta) {
                            popUpTo(Rutas.HOME)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(imageVector = destino.icono, contentDescription = destino.titulo) },
                label = { Text(text = destino.titulo) }
            )
        }
    }
}