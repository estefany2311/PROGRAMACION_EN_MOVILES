package com.chavez.tecsupfit.data

import androidx.compose.runtime.mutableStateListOf

data class ClaseGym(
    val id: Int,
    val nombre: String,
    val hora: String,
    val sala: String,
    val cuposTotales: Int,
    val cuposDisponibles: Int,
    val descripcion: String,
    val estadoReserva: String = "" // "Confirmada" o "Completada"
)

object DatosPruebaFit {
    val clasesDisponibles = listOf(
        ClaseGym(
            id = 1,
            nombre = "Yoga funcional",
            hora = "7:00 am",
            sala = "Sala 2",
            cuposTotales = 10,
            cuposDisponibles = 5,
            descripcion = "Mejora tu flexibilidad y fuerza postural con ejercicios guiados."
        ),
        ClaseGym(
            id = 2,
            nombre = "Cross Training",
            hora = "6:00 pm",
            sala = "Sala 1",
            cuposTotales = 12,
            cuposDisponibles = 8,
            descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados."
        ),
        ClaseGym(
            id = 3,
            nombre = "Spinning",
            hora = "7:30 pm",
            sala = "Sala 3",
            cuposTotales = 15,
            cuposDisponibles = 3,
            descripcion = "Sesión cardiovascular en bicicleta estática con ritmo dinámico."
        )
    )

    // Usamos mutableStateListOf para que Jetpack Compose detecte los cambios en vivo
    val misReservas = mutableStateListOf(
        ClaseGym(
            id = 2,
            nombre = "Cross Training",
            hora = "Hoy, 6:00 pm",
            sala = "Sala 1",
            cuposTotales = 12,
            cuposDisponibles = 8,
            descripcion = "",
            estadoReserva = "Confirmada"
        ),
        ClaseGym(
            id = 1,
            nombre = "Yoga funcional",
            hora = "Ayer, 7:00 am",
            sala = "Sala 2",
            cuposTotales = 10,
            cuposDisponibles = 5,
            descripcion = "",
            estadoReserva = "Completada"
        )
    )

    // Función para agregar la reserva seleccionada
    fun agregarReserva(claseId: Int) {
        val clase = clasesDisponibles.find { it.id == claseId }
        if (clase != null && misReservas.none { it.id == clase.id && it.estadoReserva == "Confirmada" }) {
            misReservas.add(
                0, // Agrega al inicio de la lista
                clase.copy(
                    hora = "Hoy, ${clase.hora}",
                    estadoReserva = "Confirmada"
                )
            )
        }
    }
}