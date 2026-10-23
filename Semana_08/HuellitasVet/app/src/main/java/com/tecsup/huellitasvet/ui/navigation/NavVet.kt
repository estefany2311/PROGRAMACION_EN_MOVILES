package com.tecsup.huellitasvet.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.tecsup.huellitasvet.VetApp
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.model.Rol
import com.tecsup.huellitasvet.ui.components.AppTopBar
import com.tecsup.huellitasvet.ui.components.VetButton
import com.tecsup.huellitasvet.ui.dueno.FichaMascotaScreen
import com.tecsup.huellitasvet.ui.dueno.FormularioMascotaScreen
import com.tecsup.huellitasvet.ui.dueno.InicioScreen
import com.tecsup.huellitasvet.ui.dueno.LoginScreen
import com.tecsup.huellitasvet.ui.dueno.MascotasScreen
import com.tecsup.huellitasvet.ui.dueno.SplashScreen
import com.tecsup.huellitasvet.ui.theme.VetTheme
import com.tecsup.huellitasvet.ui.veterinario.ServiciosScreen
import com.tecsup.huellitasvet.viewmodel.InicioViewModel
import com.tecsup.huellitasvet.viewmodel.MascotaViewModel
import com.tecsup.huellitasvet.viewmodel.ServiciosViewModel
import com.tecsup.huellitasvet.viewmodel.SesionViewModel
import com.tecsup.huellitasvet.viewmodel.VetViewModelFactory

private data class ItemBarra(val ruta: String, val etiqueta: String, val icono: ImageVector)

private val barraDueno = listOf(
    ItemBarra(Rutas.INICIO, "Inicio", Icons.Filled.Home),
    ItemBarra(Rutas.MASCOTAS, "Mascotas", Icons.Filled.Pets),
    ItemBarra(Rutas.MIS_CITAS, "Citas", Icons.Filled.CalendarMonth),
    ItemBarra(Rutas.PERFIL, "Perfil", Icons.Filled.Person)
)

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
    val rolTema = if (usuario?.rol == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE
    val usuarioId = usuario?.id ?: 0

    val entrada by nav.currentBackStackEntryAsState()
    val rutaActual = entrada?.destination?.route
    val barra = if (barraDueno.any { it.ruta == rutaActual }) barraDueno else null

    VetTheme(rolTema, oscuro) {
        Scaffold(
            contentWindowInsets = WindowInsets(0),
            bottomBar = { if (barra != null) BarraNavegacion(nav, barra, rutaActual) }
        ) { padding ->
            NavHost(
                navController = nav,
                startDestination = Rutas.SPLASH,
                modifier = Modifier.padding(padding).consumeWindowInsets(padding)
            ) {
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

                // ---------- Grafo del dueño ----------
                navigation(startDestination = Rutas.INICIO, route = Rutas.GRAFO_DUENO) {
                    composable(Rutas.INICIO) {
                        val vm: InicioViewModel = viewModel(
                            key = "inicio-$usuarioId",
                            factory = VetViewModelFactory { InicioViewModel(app.repository, usuarioId) }
                        )
                        InicioScreen(
                            vm = vm,
                            nombre = usuario?.nombre ?: "",
                            onVerMascota = { nav.navigate(Rutas.fichaMascota(it)) },
                            onAgregarMascota = { nav.navigate(Rutas.formMascota()) },
                            onVerMascotas = { irATab(nav, Rutas.MASCOTAS) },
                            onPedirCita = { nav.navigate(Rutas.pedirCita()) }
                        )
                    }
                    composable(Rutas.MASCOTAS) {
                        val vm: InicioViewModel = viewModel(
                            key = "mascotas-$usuarioId",
                            factory = VetViewModelFactory { InicioViewModel(app.repository, usuarioId) }
                        )
                        MascotasScreen(
                            vm = vm,
                            onVerMascota = { nav.navigate(Rutas.fichaMascota(it)) },
                            onAgregar = { nav.navigate(Rutas.formMascota()) }
                        )
                    }
                    composable(
                        Rutas.FICHA_MASCOTA,
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { e ->
                        val id = e.arguments?.getInt("id") ?: 0
                        val vm: MascotaViewModel = viewModel(
                            factory = VetViewModelFactory { MascotaViewModel(app.repository, usuarioId, id) }
                        )
                        FichaMascotaScreen(
                            vm = vm,
                            mascotaId = id,
                            onVolver = { nav.popBackStack() },
                            onPedirCita = { nav.navigate(Rutas.pedirCita(it)) },
                            onEditar = { nav.navigate(Rutas.formMascota(it)) }
                        )
                    }
                    composable(
                        Rutas.FORM_MASCOTA,
                        arguments = listOf(navArgument("id") { type = NavType.IntType; defaultValue = 0 })
                    ) { e ->
                        val id = e.arguments?.getInt("id") ?: 0
                        val vm: MascotaViewModel = viewModel(
                            factory = VetViewModelFactory { MascotaViewModel(app.repository, usuarioId, id) }
                        )
                        FormularioMascotaScreen(vm, esNueva = id == 0, onTerminar = { nav.popBackStack() })
                    }
                    // TODO (Paso 9): pedir cita
                    composable(Rutas.PEDIR_CITA) { PantallaPendiente("Pedir cita", onVolver = { nav.popBackStack() }) }
                    // TODO (Paso 10): mis citas
                    composable(Rutas.MIS_CITAS) { PantallaPendiente("Mis citas") }
                    // TODO (Paso 13): perfil completo
                    composable(Rutas.PERFIL) {
                        PantallaTemporal(usuario, onCerrarSesion = { cerrarSesion(nav, sesion) })
                    }
                }

                // ---------- Grafo del veterinario ----------
                navigation(startDestination = Rutas.PANEL, route = Rutas.GRAFO_VET) {
                    composable(Rutas.PANEL) {
                        // TODO (Paso 11): PanelScreen
                        PantallaTemporal(usuario, onCerrarSesion = { cerrarSesion(nav, sesion) })
                    }
                    composable(Rutas.SERVICIOS) {
                        val vm: ServiciosViewModel = viewModel(factory = VetViewModelFactory { ServiciosViewModel(app.repository) })
                        ServiciosScreen(vm, onEditar = { /* TODO (Paso 13): formulario de servicio */ })
                    }
                }
            }
        }
    }
}

@Composable
private fun BarraNavegacion(nav: NavHostController, items: List<ItemBarra>, rutaActual: String?) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { item ->
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = { irATab(nav, item.ruta, items.first().ruta) },
                icon = { Icon(item.icono, contentDescription = item.etiqueta) },
                label = { Text(item.etiqueta) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

// Cambia de pestaña sin apilar pantallas repetidas
private fun irATab(nav: NavHostController, ruta: String, raiz: String = Rutas.INICIO) {
    nav.navigate(ruta) {
        popUpTo(raiz) { saveState = true }
        launchSingleTop = true
        restoreState = true
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

// Pantallas provisionales: se reemplazan en los siguientes pasos
@Composable
private fun PantallaPendiente(titulo: String, onVolver: (() -> Unit)? = null) {
    Scaffold(
        topBar = { AppTopBar(titulo, onVolver = onVolver) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Text(
            "Se construye en el siguiente paso",
            modifier = Modifier.padding(padding).padding(24.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

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
