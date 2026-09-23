package com.chavez.tecsupfit.data

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

    val misReservas = listOf(
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
}