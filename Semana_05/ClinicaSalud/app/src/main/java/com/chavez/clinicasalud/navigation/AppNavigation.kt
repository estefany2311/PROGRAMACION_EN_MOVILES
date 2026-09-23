package com.chavez.clinicasalud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chavez.clinicasalud.screens.BookAppointmentScreen

// Contenedor principal del flujo de pantallas
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Pantalla 1: Inicio
        composable(Screen.Home.route) {
            // Se conectará con HomeScreen
        }

        // Pantalla 2: Perfil del médico
        composable(
            route = Screen.DoctorDetail.route,
            arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
        ) {
            // Se conectará con DoctorDetailScreen
        }

        // Pantalla 3: Agendar Cita
        composable(
            route = Screen.BookAppointment.route,
            arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            BookAppointmentScreen(
                medicoId = medicoId,
                onBackClick = { navController.popBackStack() },
                onConfirmarClick = { id, fecha, hora ->
                    navController.navigate(Screen.Confirmation.createRoute(id, fecha, hora))
                }
            )
        }

        // Pantalla 4: Confirmación de Cita
        composable(
            route = Screen.Confirmation.route,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.IntType },
                navArgument("fecha") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType }
            )
        ) {

        }

        // Pantalla 5: Mis Citas
        composable(Screen.MyAppointments.route) {
            // Se conectará con MyAppointmentsScreen
        }
    }
}