package com.chavez.NavLab5.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chavez.NavLab5.screens.DetailScreen
import com.chavez.NavLab5.screens.HomeScreen
import com.chavez.NavLab5.screens.ListScreen
import com.chavez.NavLab5.screens.ProfileScreen

@Composable
fun AppNavigation() {
    // NavController: Administra la pila de navegación (backstack) y los desplazamientos entre pantallas
    val navController = rememberNavController()

    // NavHost: Contenedor Compose que intercambia las pantallas según la ruta activa.
    // startDestination define la pantalla inicial al abrir la app (en este caso, Home).
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        // RUTA 1: Pantalla de inicio
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // RUTA 2: Pantalla de lista
        composable(route = Screen.List.route) {
            ListScreen(navController = navController)
        }

        // RUTA 3: Pantalla de detalle CON ARGUMENTO
        // "detail/{itemId}" espera que la URL traiga una variable llamada "itemId"
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType // Forzamos a que el parámetro sea un Int
                }
            )
        ) { backStackEntry ->
            // Extraemos el valor del argumento desde la ruta activa
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            DetailScreen(navController = navController, itemId = itemId)
        }

        // RUTA 4: Pantalla de perfil
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}