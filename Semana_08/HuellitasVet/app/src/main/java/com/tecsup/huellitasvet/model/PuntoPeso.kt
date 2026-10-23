package com.tecsup.huellitasvet.model

// Un peso registrado en una atencion: resultado de la consulta de CitaDao.observarPesos
data class PuntoPeso(
    val fecha: String,     // 'aaaa-MM-dd'
    val peso: Double
)
