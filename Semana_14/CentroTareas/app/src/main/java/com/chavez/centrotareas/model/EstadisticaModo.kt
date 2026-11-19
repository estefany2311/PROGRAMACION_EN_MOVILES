package com.chavez.centrotareas.model

//Tiempos medidos (en ms) de un modo de carga, con sus datos derivados. mej4
data class EstadisticaModo(
    val modo: String,
    val mediciones: List<Long> = emptyList()
) {
    val cantidad: Int get() = mediciones.size
    val ultimo: Long? get() = mediciones.lastOrNull()
    val promedio: Long? get() = if (mediciones.isEmpty()) null else mediciones.average().toLong()
}
