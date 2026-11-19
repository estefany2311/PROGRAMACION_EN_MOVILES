package com.chavez.campusgps.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Opciones de radio: null = "Sin radio"; el resto, metros
private val opciones: List<Pair<Int?, String>> = listOf(
    null to "Sin radio",
    100 to "100 m",
    200 to "200 m",
    500 to "500 m"
)

// Botones segmentados para elegir el radio, dentro de una Surface para que se lean sobre el mapa.
// No conoce el ViewModel.
@Composable
fun SelectorRadio(
    seleccionado: Int?,
    onSeleccionar: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 3.dp,
        shadowElevation = 2.dp
    ) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            opciones.forEachIndexed { indice, (valor, texto) ->
                SegmentedButton(
                    selected = valor == seleccionado,
                    onClick = { onSeleccionar(valor) },
                    shape = SegmentedButtonDefaults.itemShape(index = indice, count = opciones.size)
                ) {
                    Text(texto, maxLines = 1)
                }
            }
        }
    }
}
