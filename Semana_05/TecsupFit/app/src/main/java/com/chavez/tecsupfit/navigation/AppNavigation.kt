package com.chavez.tecsupfit.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chavez.tecsupfit.screens.*

data class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(Screen.Home.route, "Inicio", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem(Screen.Reservations.route, "Reservas", Icons.Filled.DateRange, Icons.Outlined.DateRange),
        BottomNavItem(Screen.Routines.route, "Rutinas", Icons.Filled.FitnessCenter, Icons.Outlined.FitnessCenter),
        BottomNavItem(Screen.Profile.route, "Perfil", Icons.Filled.Person, Icons.Outlined.Person)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Ocultar bottomBar en pantallas de Detalle y Confirmación
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Reservations.route,
        Screen.Routines.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.White
                ) {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF00695C),
                                selectedTextColor = Color(0xFF00695C),
                                indicatorColor = Color(0xFFE0F2F1),
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onClaseClick = { claseId ->
                        navController.navigate(Screen.ClassDetail.createRoute(claseId))
                    }
                )
            }

            composable(
                route = Screen.ClassDetail.route,
                arguments = listOf(navArgument("claseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val claseId = backStackEntry.arguments?.getInt("claseId") ?: 1
                ClassDetailScreen(
                    claseId = claseId,
                    onBackClick = { navController.popBackStack() },
                    onReservarClick = { id ->
                        navController.navigate(Screen.Confirmation.createRoute(id))
                    }
                )
            }

            composable(
                route = Screen.Confirmation.route,
                arguments = listOf(navArgument("claseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val claseId = backStackEntry.arguments?.getInt("claseId") ?: 1
                ConfirmationScreen(
                    claseId = claseId,
                    onVerReservasClick = {
                        navController.navigate(Screen.Reservations.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                )
            }

            composable(Screen.Reservations.route) {
                ReservationsScreen()
            }

            composable(Screen.Routines.route) {
                RoutinesScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}