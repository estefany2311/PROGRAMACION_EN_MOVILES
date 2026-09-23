package com.chavez.NavLab5.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chavez.NavLab5.screens.DetailScreen
import com.chavez.NavLab5.screens.DirectoryScreen
import com.chavez.NavLab5.screens.HomeScreen
import com.chavez.NavLab5.screens.LoginScreen
import com.chavez.NavLab5.screens.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // RUTA 1: Login (Portal Académico)
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        // RUTA 2: Menú Principal (Home)
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // RUTA 3: Directorio de Alumnos
        composable(route = Screen.Directory.route) {
            DirectoryScreen(navController = navController)
        }

        // RUTA 4: Expediente Académico del Alumno (Detail)
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("studentId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: 0
            DetailScreen(navController = navController, studentId = studentId)
        }

        // RUTA 5: Perfil del Usuario
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}
