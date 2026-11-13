package com.tecsup.valorant.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tecsup.valorant.ui.theme.ValoLinea
import com.tecsup.valorant.ui.theme.ValoMagenta
import com.tecsup.valorant.ui.theme.ValoTextoSuave

// Campo para buscar agentes por nombre.
// No guarda estado: recibe el texto y avisa cada cambio con onCambiar.
@Composable
fun Buscador(texto: String, onCambiar: (String) -> Unit, modifier: Modifier = Modifier) {
    val foco = LocalFocusManager.current
    OutlinedTextField(
        value = texto,
        onValueChange = onCambiar,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        placeholder = { Text("Buscar agente...", color = ValoTextoSuave) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = ValoTextoSuave) },
        trailingIcon = {
            // La X solo aparece cuando hay algo escrito
            if (texto.isNotEmpty()) {
                IconButton(onClick = { onCambiar("") }) {
                    Icon(Icons.Filled.Close, contentDescription = "Borrar búsqueda", tint = ValoTextoSuave)
                }
            }
        },
        // Al tocar "Buscar" en el teclado, se oculta el teclado
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { foco.clearFocus() }),
        shape = CutCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ValoMagenta,
            unfocusedBorderColor = ValoLinea,
            cursorColor = ValoMagenta
        )
    )
}
