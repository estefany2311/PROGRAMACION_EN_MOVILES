package com.chavez.tecsupfit.data

import androidx.compose.runtime.mutableStateListOf

// RF09: Se agregan los campos 'nivel' y 'calorias' al modelo ClaseGym
data class ClaseGym(
    val id: Int,
    val nombre: String,
    val hora: String,
    val sala: String,
    val cuposTotales: Int,
    val cuposDisponibles: Int,
    val descripcion: String,
    val nivel: String = "Intermedio", // RF09: "Principiante", "Intermedio", "Avanzado"
    val calorias: String = "400 kcal", // RF09: Calorías estimadas, ej: "250 kcal"
    val estadoReserva: String = "" // "Confirmada" o "Completada"
)

object DatosPruebaFit {
    // RF09: Actualización de datos de prueba asignando nivel y calorias a cada clase
    val clasesDisponibles = listOf(
        ClaseGym(
            id = 1,
            nombre = "Yoga funcional",
            hora = "7:00 am",
            sala = "Sala 2",
            cuposTotales = 10,
            cuposDisponibles = 5,
            descripcion = "Mejora tu flexibilidad y fuerza postural con ejercicios guiados.",
            nivel = "Principiante",
            calorias = "250 kcal"
        ),
        ClaseGym(
            id = 2,
            nombre = "Cross Training",
            hora = "6:00 pm",
            sala = "Sala 1",
            cuposTotales = 12,
            cuposDisponibles = 8,
            descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
            nivel = "Avanzado",
            calorias = "500 kcal"
        ),
        ClaseGym(
            id = 3,
            nombre = "Spinning",
            hora = "7:30 pm",
            sala = "Sala 3",
            cuposTotales = 15,
            cuposDisponibles = 3,
            descripcion = "Sesión cardiovascular en bicicleta estática con ritmo dinámico.",
            nivel = "Intermedio",
            calorias = "450 kcal"
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
            nivel = "Avanzado",
            calorias = "500 kcal",
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
            nivel = "Principiante",
            calorias = "250 kcal",
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
