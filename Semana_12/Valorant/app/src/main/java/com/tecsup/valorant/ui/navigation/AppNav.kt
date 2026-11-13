package com.tecsup.valorant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.valorant.AppContainer
import com.tecsup.valorant.domain.model.Rol
import com.tecsup.valorant.ui.admin.AdminScreen
import com.tecsup.valorant.ui.admin.AdminViewModel
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.detalle.DetalleScreen
import com.tecsup.valorant.ui.detalle.DetalleViewModel
import com.tecsup.valorant.ui.favoritos.FavoritosScreen
import com.tecsup.valorant.ui.favoritos.FavoritosViewModel
import com.tecsup.valorant.ui.lista.ListaScreen
import com.tecsup.valorant.ui.lista.ListaViewModel
import com.tecsup.valorant.ui.login.LoginScreen
import com.tecsup.valorant.ui.login.LoginViewModel

@Composable
fun AppNav(container: AppContainer) {
    val nav = rememberNavController()

    // Un solo LoginViewModel para toda la app: guarda la sesión y el rol
    val loginVm: LoginViewModel = viewModel(factory = LoginViewModel.factory(container))
    val estadoLogin by loginVm.estado.collectAsStateWithLifecycle()
    val s = estadoLogin
    val sesion = if (s is UiState.Exito) s.datos else null
    val esAdmin = sesion?.rol == Rol.ADMIN

    val cerrarSesion = {
        loginVm.cerrarSesion()
        nav.navigate("login") { popUpTo(nav.graph.id) { inclusive = true } }
    }

    NavHost(navController = nav, startDestination = "login") {

        composable("login") {
            LoginScreen(loginVm, onIngreso = {
                nav.navigate("lista") { popUpTo("login") { inclusive = true } }
            })
        }

        composable("lista") {
            val vm: ListaViewModel = viewModel(factory = ListaViewModel.factory(container))
            ListaScreen(
                vm = vm,
                esAdmin = esAdmin,
                onAbrirDetalle = { id -> nav.navigate("detalle/$id") },
                onFavoritos = { nav.navigate("favoritos") },
                onAdmin = { nav.navigate("admin") },
                onCerrarSesion = cerrarSesion
            )
        }

        composable("detalle/{id}") { entrada ->
            val id = entrada.arguments?.getString("id") ?: return@composable
            val vm: DetalleViewModel = viewModel(factory = DetalleViewModel.factory(container, id))
            DetalleScreen(vm, onVolver = { nav.popBackStack() })
        }

        composable("favoritos") {
            val vm: FavoritosViewModel = viewModel(factory = FavoritosViewModel.factory(container))
            FavoritosScreen(
                vm = vm,
                onAbrirDetalle = { id -> nav.navigate("detalle/$id") },
                onVolver = { nav.popBackStack() }
            )
        }

        composable("admin") {
            // Doble seguridad: aunque alguien llegue aquí, solo el admin ve el panel
            if (esAdmin) {
                val vm: AdminViewModel = viewModel(factory = AdminViewModel.factory(container))
                AdminScreen(vm, onVolver = { nav.popBackStack() })
            }
        }
    }
}