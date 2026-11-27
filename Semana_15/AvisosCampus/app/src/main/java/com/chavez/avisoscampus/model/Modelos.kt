package com.chavez.avisoscampus.model

enum class Origen(val texto: String) {
    BOTON("Botón"),
    RECORDATORIO("Recordatorio"),
    MODO_AVION("Modo avión")
}

// Qué tan importante es un aviso. Lo decide el ViewModel; el Notificador elige el canal.
enum class Prioridad { NORMAL, URGENTE }

data class Aviso(
    val id: Int,
    val titulo: String,
    val texto: String,
    val origen: Origen,
    val hora: String,
    val prioridad: Prioridad = Prioridad.NORMAL
)

sealed interface PermisoUi {
    data object SinPedir : PermisoUi
    data object Concedido : PermisoUi
    data object Denegado : PermisoUi
}
