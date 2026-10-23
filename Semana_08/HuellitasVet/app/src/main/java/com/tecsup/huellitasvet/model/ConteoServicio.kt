package com.tecsup.huellitasvet.model

// Resultado de la consulta con JOIN + GROUP BY: cuantas citas tiene cada servicio
data class ConteoServicio(
    val nombre: String,
    val cantidad: Int
)
