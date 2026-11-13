package com.tecsup.valorant.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.valorant.R
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.theme.ValoCoral
import com.tecsup.valorant.ui.theme.ValoFondo
import com.tecsup.valorant.ui.theme.ValoLinea
import com.tecsup.valorant.ui.theme.ValoMagenta
import com.tecsup.valorant.ui.theme.ValoSuperficie
import com.tecsup.valorant.ui.theme.ValoTexto
import com.tecsup.valorant.ui.theme.ValoTextoSuave

private val FormaCampo = CutCornerShape(topEnd = 12.dp)
private val FormaBotonIngresar = CutCornerShape(topEnd = 14.dp, bottomStart = 14.dp)

@Composable
fun LoginScreen(vm: LoginViewModel, onIngreso: () -> Unit) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(estado) {
        val s = estado
        if (s is UiState.Exito && s.datos != null) onIngreso()
    }

    // Surface pone el fondo y el color de texto claro para todo lo de adentro
    Surface(color = ValoFondo, contentColor = ValoTexto, modifier = Modifier.fillMaxSize()) {
    Box(Modifier.fillMaxSize()) {
        // 1. Póster de fondo: ocupa todo el ancho y se ve completo arriba (con el logo)
        Image(
            painter = painterResource(R.drawable.fondo_login),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )
        // 2. Degradado: transparente arriba, toque magenta al medio, ciruela abajo
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.38f to Color.Transparent,
                        0.52f to Color(0x993C1437),
                        0.66f to ValoFondo
                    )
                )
        )
        // 3. Formulario en la parte de abajo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 24.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                "// CATÁLOGO DE AGENTES",
                style = MaterialTheme.typography.labelSmall,
                color = ValoCoral
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "INICIA\nSESIÓN",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = ValoTexto
            )
            Spacer(Modifier.height(20.dp))

            val coloresCampo = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ValoMagenta,
                unfocusedBorderColor = ValoLinea,
                focusedContainerColor = ValoSuperficie.copy(alpha = 0.9f),
                unfocusedContainerColor = ValoSuperficie.copy(alpha = 0.9f),
                focusedLabelColor = ValoMagenta,
                unfocusedLabelColor = ValoTextoSuave,
                cursorColor = ValoMagenta
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                singleLine = true,
                shape = FormaCampo,
                colors = coloresCampo,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it },
                label = { Text("Contraseña") },
                singleLine = true,
                shape = FormaCampo,
                colors = coloresCampo,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(22.dp))

            // Botón con degradado coral → magenta y esquinas cortadas
            val cargando = estado is UiState.Cargando
            Button(
                onClick = { vm.ingresar(correo, clave) },
                enabled = !cargando,
                shape = FormaBotonIngresar,
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(
                        Brush.horizontalGradient(listOf(ValoCoral, ValoMagenta)),
                        FormaBotonIngresar
                    )
            ) {
                if (cargando) {
                    CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp, color = Color.White)
                } else {
                    Text("INGRESAR", fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                }
            }

            val s = estado
            if (s is UiState.Error) {
                Spacer(Modifier.height(12.dp))
                Text(s.mensaje, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
}
