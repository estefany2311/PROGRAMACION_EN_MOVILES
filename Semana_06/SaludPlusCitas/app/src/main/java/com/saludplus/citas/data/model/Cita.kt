package com.saludplus.citas.data.model

// fecha usa el formato "yyyy-MM-dd" (ej. "2026-09-16") y hora el formato "HH:mm" (ej. "09:30")
data class Cita(
    val id: Int,
    val telefonoUsuario: String,
    val medicoId: Int,
    val fecha: String,
    val hora: String,
    val motivo: String
)
