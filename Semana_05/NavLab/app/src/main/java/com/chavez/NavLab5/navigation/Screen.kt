package com.chavez.NavLab5.navigation

// Clase sellada que actúa como contrato central de navegación para el Portal Académico.
sealed class Screen(val route: String) {
    // Pantalla de Inicio de Sesión
    object Login : Screen(route = "login")
    
    // Pantalla del Menú Principal
    object Home : Screen(route = "home")
    
    // Directorio de Alumnos (reemplaza a List)
    object Directory : Screen(route = "directory")
    
    // Pantalla del Perfil del Alumno / Usuario
    object Profile : Screen(route = "profile")
    
    // Expediente del Alumno con parámetro {studentId}
    object Detail : Screen(route = "detail/{studentId}") {
        fun createRoute(studentId: Int): String = "detail/$studentId"
    }
}
