package com.tecsup.valorant.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.valorant.ui.theme.ValoMagenta
import com.tecsup.valorant.ui.theme.colorDeRol

// Fila de chips para filtrar los agentes por rol.
// No guarda estado: recibe el rol elegido y avisa con onElegir (null = "Todos").
@Composable
fun FiltroRoles(
    roles: List<String>,
    seleccionado: String?,
    onElegir: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChipRol("TODOS", ValoMagenta, activo = seleccionado == null) { onElegir(null) }
        roles.forEach { rol ->
            ChipRol(rol.uppercase(), colorDeRol(rol), activo = seleccionado == rol) { onElegir(rol) }
        }
    }
}

@Composable
private fun ChipRol(texto: String, color: Color, activo: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = activo,
        onClick = onClick,
        label = {
            Text(texto, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, fontSize = 12.sp)
        },
        shape = CutCornerShape(topEnd = 8.dp, bottomStart = 8.dp),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = color,
            selectedContainerColor = color.copy(alpha = 0.25f),
            selectedLabelColor = color
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = activo,
            borderColor = color.copy(alpha = 0.4f),
            selectedBorderColor = color
        )
    )
}
