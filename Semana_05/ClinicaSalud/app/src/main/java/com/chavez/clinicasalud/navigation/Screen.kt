package com.chavez.clinicasalud.navigation
// Rutas centrales de la aplicación
sealed class Screen(val route: String) {
    object Home : Screen("home")

    // Ruta con parámetro: ID del médico seleccionado
    object DoctorDetail : Screen("doctor_detail/{medicoId}") {
        fun createRoute(medicoId: Int) = "doctor_detail/$medicoId"
    }

    // Ruta para agendar cita con un médico específico
    object BookAppointment : Screen("book_appointment/{medicoId}") {
        fun createRoute(medicoId: Int) = "book_appointment/$medicoId"
    }

    // Ruta de confirmación recibiendo médico, fecha y hora seleccionadas
    object Confirmation : Screen("confirmation/{medicoId}/{fecha}/{hora}") {
        fun createRoute(medicoId: Int, fecha: String, hora: String) =
            "confirmation/$medicoId/$fecha/$hora"
    }

    // Ruta para la pantalla Mis Citas del menú lateral
    object MyAppointments : Screen("my_appointments")

    // Ruta para la pantalla Perfil
    object Profile : Screen("profile")
}