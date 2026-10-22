package com.tecsup.huellitasvet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.huellitasvet.VetApp
import com.tecsup.huellitasvet.model.Rol
import com.tecsup.huellitasvet.ui.dueno.SplashScreen
import com.tecsup.huellitasvet.ui.theme.VetTheme
import com.tecsup.huellitasvet.ui.veterinario.ServiciosScreen
import com.tecsup.huellitasvet.viewmodel.ServiciosViewModel
import com.tecsup.huellitasvet.viewmodel.VetViewModelFactory

// Grafo de navegacion de la app
@Composable
fun NavVet(app: VetApp) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Rutas.SPLASH) {
        composable(Rutas.SPLASH) {
            VetTheme(Rol.CLIENTE) {
                SplashScreen(
                    onComenzar = { nav.navigate(Rutas.SERVICIOS) },
                    onTengoCuenta = { nav.navigate(Rutas.SERVICIOS) }   // TODO (Paso 6): ir al login
                )
            }
        }
        composable(Rutas.SERVICIOS) {
            VetTheme(Rol.ADMIN) {
                val vm: ServiciosViewModel = viewModel(factory = VetViewModelFactory { ServiciosViewModel(app.repository) })
                ServiciosScreen(vm, onEditar = { /* TODO (Paso 13): formulario de servicio */ })
            }
        }
        // TODO (Pasos 6 al 13): login, grafo del dueño y grafo del veterinario
    }
}
