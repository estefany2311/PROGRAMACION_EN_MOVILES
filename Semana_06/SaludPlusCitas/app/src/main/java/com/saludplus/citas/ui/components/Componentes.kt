package com.saludplus.citas.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

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