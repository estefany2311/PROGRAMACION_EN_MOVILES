package com.chavez.clinicasalud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chavez.clinicasalud.screens.*

// Contenedor principal del flujo de pantallas
@Composable
fun AppNavigation(
    navController: NavHostController,
    onOpenDrawer: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Pantalla 1: Inicio
        composable(Screen.Home.route) {
            HomeScreen(
                onMedicoClick = { medicoId ->
                    navController.navigate(Screen.DoctorDetail.createRoute(medicoId))
                },
                onOpenDrawer = onOpenDrawer
            )
        }

        // Pantalla 2: Perfil del médico
        composable(
            route = Screen.DoctorDetail.route,
            arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            DoctorDetailScreen(
                medicoId = medicoId,
                onBackClick = { navController.popBackStack() },
                onAgendarClick = { id ->
                    navController.navigate(Screen.BookAppointment.createRoute(id))
                }
            )
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
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
            val hora = backStackEntry.arguments?.getString("hora") ?: ""

            ConfirmationScreen(
                medicoId = medicoId,
                fecha = fecha,
                hora = hora,
                onVerMisCitasClick = {
                    navController.navigate(Screen.MyAppointments.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }

        // Pantalla 5: Mis Citas
        composable(Screen.MyAppointments.route) {
            MyAppointmentsScreen(onOpenDrawer = onOpenDrawer)
        }
    }
}