package com.saludplus.citas.data.model

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidadId: Int,
    val calificacion: Double,
    val resenas: Int,
    val cmp: String,
    val disponibilidad: String
)
