package com.tecsup.huellitasvet.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.tecsup.huellitasvet.VetApp
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.model.Rol
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.dueno.LoginScreen
import com.tecsup.huellitasvet.ui.dueno.SplashScreen
import com.tecsup.huellitasvet.ui.theme.VetTheme
import com.tecsup.huellitasvet.ui.veterinario.ServiciosScreen
import com.tecsup.huellitasvet.viewmodel.ServiciosViewModel
import com.tecsup.huellitasvet.viewmodel.SesionViewModel
import com.tecsup.huellitasvet.viewmodel.VetViewModelFactory

// Grafo de navegacion de la app
@Composable
fun NavVet(app: VetApp) {
    val nav = rememberNavController()
    // La sesion vive a nivel de la Activity: la comparten todas las pantallas
    val sesion: SesionViewModel = viewModel(
        factory = VetViewModelFactory { SesionViewModel(app.repository, app.preferencias) }
    )
    val usuario by sesion.usuarioActual.collectAsStateWithLifecycle()
    val revisada by sesion.sesionRevisada.collectAsStateWithLifecycle()
    val oscuro by sesion.modoOscuro.collectAsStateWithLifecycle()

    NavHost(navController = nav, startDestination = Rutas.SPLASH) {
        composable(Rutas.SPLASH) {
            // Si ya hay perfil guardado, salta el login y va directo a su pantalla principal
            LaunchedEffect(revisada, usuario) {
                val u = usuario
                if (revisada && u != null) irAGrafo(nav, u)
            }
            VetTheme(Rol.CLIENTE, oscuro) {
                SplashScreen(
                    onComenzar = { nav.navigate(Rutas.LOGIN) },
                    onTengoCuenta = { nav.navigate(Rutas.LOGIN) }
                )
            }
        }
        composable(Rutas.LOGIN) {
            LoginScreen(sesion, onExito = { irAGrafo(nav, it) })
        }

        // Grafo del dueño
        navigation(startDestination = Rutas.INICIO, route = Rutas.GRAFO_DUENO) {
            composable(Rutas.INICIO) {
                VetTheme(Rol.CLIENTE, oscuro) {
                    // TODO (Paso 7): InicioScreen
                    PantallaTemporal(usuario, onCerrarSesion = { cerrarSesion(nav, sesion) })
                }
            }
        }

        // Grafo del veterinario
        navigation(startDestination = Rutas.PANEL, route = Rutas.GRAFO_VET) {
            composable(Rutas.PANEL) {
                VetTheme(Rol.ADMIN, oscuro) {
                    // TODO (Paso 11): PanelScreen
                    PantallaTemporal(usuario, onCerrarSesion = { cerrarSesion(nav, sesion) })
                }
            }
            composable(Rutas.SERVICIOS) {
                VetTheme(Rol.ADMIN, oscuro) {
                    val vm: ServiciosViewModel = viewModel(factory = VetViewModelFactory { ServiciosViewModel(app.repository) })
                    ServiciosScreen(vm, onEditar = { /* TODO (Paso 13): formulario de servicio */ })
                }
            }
        }
    }
}

// Navega al grafo del rol y limpia la pila para que "atras" no vuelva al login
private fun irAGrafo(nav: NavHostController, u: Usuario) {
    val destino = if (u.rol == Rol.ADMIN.name) Rutas.GRAFO_VET else Rutas.GRAFO_DUENO
    nav.navigate(destino) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

private fun cerrarSesion(nav: NavHostController, sesion: SesionViewModel) {
    sesion.cerrarSesion()
    nav.navigate(Rutas.LOGIN) {
        popUpTo(0) { inclusive = true }
    }
}

// Pantalla provisional hasta que existan Inicio (Paso 7) y Panel (Paso 11)
@Composable
private fun PantallaTemporal(usuario: Usuario?, onCerrarSesion: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hola, ${usuario?.nombre ?: ""}", style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary)
        Text("Sesión iniciada como ${usuario?.let { Rol.valueOf(it.rol).etiqueta } ?: ""}",
            color = MaterialTheme.colorScheme.onBackground)
        VetButton("Cerrar sesión", onClick = onCerrarSesion, contorno = true)
    }
}
