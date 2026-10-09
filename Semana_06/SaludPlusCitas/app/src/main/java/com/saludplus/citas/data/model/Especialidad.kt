package com.saludplus.citas.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Especialidad(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val icono: ImageVector
)
