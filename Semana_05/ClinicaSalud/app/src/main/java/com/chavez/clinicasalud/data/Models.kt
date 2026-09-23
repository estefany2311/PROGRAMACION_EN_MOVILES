package com.chavez.clinicasalud.data

// Modelo de datos para los médicos
data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val resenas: Int,
    val anosExp: Int,
    val biografia: String
)

// Modelo de datos para las citas agendadas
data class Cita(
    val id: Int,
    val medicoNombre: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val estado: String // "Confirmada" o "Completada"
)

// Datos estáticos de prueba (SIN MVVM / SIN ViewModel)
object DatosPrueba {
    val especialidades = listOf("Cardiología", "Pediatría", "Dermatología")

    val medicos = listOf(
        Medico(
            id = 1,
            nombre = "Dra. Ana Torres",
            especialidad = "Cardiología",
            calificacion = 4.9,
            resenas = 128,
            anosExp = 12,
            biografia = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
        ),
        Medico(
            id = 2,
            nombre = "Dr. Luis Vega",
            especialidad = "Pediatría",
            calificacion = 4.7,
            resenas = 95,
            anosExp = 8,
            biografia = "Especialista en pediatría general y desarrollo infantil."
        ),
        Medico(
            id = 3,
            nombre = "Dra. Rosa Díaz",
            especialidad = "Dermatología",
            calificacion = 4.8,
            resenas = 110,
            anosExp = 10,
            biografia = "Especialista en dermatología clínica y procedimientos estéticos."
        )
    )

    val citasIniciales = listOf(
        Cita(1, "Dra. Ana Torres", "Cardiología", "Viernes 27, 10:30 am", "10:30 am", "Confirmada"),
        Cita(2, "Dr. Luis Vega", "Pediatría", "Miércoles 15, 3:00 pm", "3:00 pm", "Completada")
    )

    val fechasDisponibles = listOf("Jue 26", "Vie 27", "Sáb 28")
    val horasDisponibles = listOf("9:00", "10:30", "3:00")
}