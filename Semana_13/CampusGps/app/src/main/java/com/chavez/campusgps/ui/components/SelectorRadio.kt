package com.chavez.campusgps.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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

// Botones segmentados para elegir el radio. No conoce el ViewModel.
@Composable
fun SelectorRadio(
    seleccionado: Int?,
    onSeleccionar: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        opciones.forEachIndexed { indice, (valor, texto) ->
            SegmentedButton(
                selected = valor == seleccionado,
                onClick = { onSeleccionar(valor) },
                shape = SegmentedButtonDefaults.itemShape(index = indice, count = opciones.size)
            ) {
                Text(texto)
            }
        }
    }
}
