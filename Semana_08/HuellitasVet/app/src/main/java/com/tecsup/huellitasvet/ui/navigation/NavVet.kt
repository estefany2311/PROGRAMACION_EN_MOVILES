package com.tecsup.huellitasvet.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.tecsup.huellitasvet.ui.dueno.FichaMascotaScreen
import com.tecsup.huellitasvet.ui.dueno.FormularioMascotaScreen
import com.tecsup.huellitasvet.ui.dueno.InicioScreen
import com.tecsup.huellitasvet.ui.dueno.LoginScreen
import com.tecsup.huellitasvet.ui.dueno.MascotasScreen
import com.tecsup.huellitasvet.ui.dueno.MisCitasScreen
import com.tecsup.huellitasvet.ui.dueno.PedirCitaScreen
import com.tecsup.huellitasvet.ui.dueno.PerfilScreen
import com.tecsup.huellitasvet.ui.dueno.SplashScreen
import com.tecsup.huellitasvet.ui.theme.VetTheme
import com.tecsup.huellitasvet.ui.veterinario.AgendaScreen
import com.tecsup.huellitasvet.ui.veterinario.AjustesScreen
import com.tecsup.huellitasvet.ui.veterinario.AtenderCitaScreen
import com.tecsup.huellitasvet.ui.veterinario.FormularioServicioScreen
import com.tecsup.huellitasvet.ui.veterinario.PacientesScreen
import com.tecsup.huellitasvet.ui.veterinario.PanelScreen
import com.tecsup.huellitasvet.ui.veterinario.ServiciosScreen
import com.tecsup.huellitasvet.viewmodel.AgendaViewModel
import com.tecsup.huellitasvet.viewmodel.AtenderCitaViewModel
import com.tecsup.huellitasvet.viewmodel.PacientesViewModel
import com.tecsup.huellitasvet.viewmodel.PanelViewModel
import com.tecsup.huellitasvet.viewmodel.InicioViewModel
import com.tecsup.huellitasvet.viewmodel.MascotaViewModel
import com.tecsup.huellitasvet.viewmodel.MisCitasViewModel
import com.tecsup.huellitasvet.viewmodel.PedirCitaViewModel
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

private val barraVet = listOf(
    ItemBarra(Rutas.PANEL, "Panel", Icons.Filled.BarChart),
    ItemBarra(Rutas.AGENDA, "Agenda", Icons.Filled.Event),
    ItemBarra(Rutas.PACIENTES, "Pacientes", Icons.Filled.Pets),
    ItemBarra(Rutas.SERVICIOS, "Servicios", Icons.Filled.MedicalServices),
    ItemBarra(Rutas.AJUSTES, "Ajustes", Icons.Filled.Settings)
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
    val notificaciones by sesion.notificaciones.collectAsStateWithLifecycle()
    val aceptarCitas by sesion.aceptarCitas.collectAsStateWithLifecycle()
    val rolTema = if (usuario?.rol == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE
    val usuarioId = usuario?.id ?: 0

    val entrada by nav.currentBackStackEntryAsState()
    val rutaActual = entrada?.destination?.route
    val barra = when {
        barraDueno.any { it.ruta == rutaActual } -> barraDueno
        barraVet.any { it.ruta == rutaActual } -> barraVet
        else -> null
    }

    // Cambiar de perfil: si el rol de la sesion ya no es el del grafo actual, se va al otro grafo
    LaunchedEffect(usuario) {
        val u = usuario ?: return@LaunchedEffect
        val grafoActual = nav.currentBackStackEntry?.destination?.parent?.route
        val esperado = if (u.rol == Rol.ADMIN.name) Rutas.GRAFO_VET else Rutas.GRAFO_DUENO
        if ((grafoActual == Rutas.GRAFO_DUENO || grafoActual == Rutas.GRAFO_VET) && grafoActual != esperado) {
            irAGrafo(nav, u)
        }
    }

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
                    composable(
                        Rutas.PEDIR_CITA,
                        arguments = listOf(navArgument("mascotaId") { type = NavType.IntType; defaultValue = 0 })
                    ) { e ->
                        val mascotaId = e.arguments?.getInt("mascotaId") ?: 0
                        val vm: PedirCitaViewModel = viewModel(
                            factory = VetViewModelFactory { PedirCitaViewModel(app.repository, app.preferencias, usuarioId, mascotaId) }
                        )
                        PedirCitaScreen(
                            vm = vm,
                            onVolver = { nav.popBackStack() },
                            // Vuelve a Mis citas con la nueva cita como PENDIENTE
                            onCreada = {
                                nav.navigate(Rutas.MIS_CITAS) {
                                    popUpTo(Rutas.INICIO)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable(Rutas.MIS_CITAS) {
                        val vm: MisCitasViewModel = viewModel(
                            key = "miscitas-$usuarioId",
                            factory = VetViewModelFactory { MisCitasViewModel(app.repository, usuarioId) }
                        )
                        MisCitasScreen(vm, onPedirCita = { nav.navigate(Rutas.pedirCita()) })
                    }
                    composable(Rutas.PERFIL) {
                        val inicio: InicioViewModel = viewModel(
                            key = "mascotas-$usuarioId",
                            factory = VetViewModelFactory { InicioViewModel(app.repository, usuarioId) }
                        )
                        val citas: MisCitasViewModel = viewModel(
                            key = "miscitas-$usuarioId",
                            factory = VetViewModelFactory { MisCitasViewModel(app.repository, usuarioId) }
                        )
                        val mascotas by inicio.mascotas.collectAsStateWithLifecycle()
                        val proximas by citas.proximas.collectAsStateWithLifecycle()
                        val atenciones by citas.atenciones.collectAsStateWithLifecycle()
                        PerfilScreen(
                            usuario = usuario,
                            mascotas = mascotas.size,
                            proximas = proximas.size,
                            atenciones = atenciones,
                            modoOscuro = oscuro,
                            recordatorios = notificaciones,
                            onModoOscuro = sesion::cambiarModoOscuro,
                            onRecordatorios = sesion::cambiarNotificaciones,
                            onCambiarPerfil = sesion::cambiarPerfil,
                            onCerrarSesion = { cerrarSesion(nav, sesion) }
                        )
                    }
                }

                // ---------- Grafo del veterinario ----------
                navigation(startDestination = Rutas.PANEL, route = Rutas.GRAFO_VET) {
                    composable(Rutas.PANEL) {
                        val vm: PanelViewModel = viewModel(factory = VetViewModelFactory { PanelViewModel(app.repository) })
                        PanelScreen(vm, onVerPendientes = { irATab(nav, Rutas.AGENDA, Rutas.PANEL) })
                    }
                    composable(Rutas.AGENDA) {
                        val vm: AgendaViewModel = viewModel(factory = VetViewModelFactory { AgendaViewModel(app.repository) })
                        AgendaScreen(vm, onAtender = { nav.navigate(Rutas.atenderCita(it)) })
                    }
                    composable(
                        Rutas.ATENDER_CITA,
                        arguments = listOf(navArgument("citaId") { type = NavType.IntType })
                    ) { e ->
                        val citaId = e.arguments?.getInt("citaId") ?: 0
                        val vm: AtenderCitaViewModel = viewModel(
                            factory = VetViewModelFactory { AtenderCitaViewModel(app.repository, citaId) }
                        )
                        AtenderCitaScreen(vm, onTerminar = { nav.popBackStack() })
                    }
                    composable(Rutas.PACIENTES) {
                        val vm: PacientesViewModel = viewModel(factory = VetViewModelFactory { PacientesViewModel(app.repository) })
                        PacientesScreen(vm, onVerPaciente = { nav.navigate(Rutas.fichaPaciente(it)) })
                    }
                    // Ficha con historial para el veterinario (solo lectura)
                    composable(
                        Rutas.FICHA_PACIENTE,
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { e ->
                        val id = e.arguments?.getInt("id") ?: 0
                        val vm: MascotaViewModel = viewModel(
                            factory = VetViewModelFactory { MascotaViewModel(app.repository, 0, id) }
                        )
                        FichaMascotaScreen(
                            vm = vm,
                            mascotaId = id,
                            onVolver = { nav.popBackStack() },
                            onPedirCita = {},
                            onEditar = {},
                            soloLectura = true
                        )
                    }
                    composable(Rutas.SERVICIOS) {
                        val vm: ServiciosViewModel = viewModel(factory = VetViewModelFactory { ServiciosViewModel(app.repository) })
                        ServiciosScreen(vm, onEditar = { nav.navigate(Rutas.formServicio(it)) })
                    }
                    composable(
                        Rutas.FORM_SERVICIO,
                        arguments = listOf(navArgument("id") { type = NavType.IntType; defaultValue = 0 })
                    ) { e ->
                        val id = e.arguments?.getInt("id") ?: 0
                        val vm: ServiciosViewModel = viewModel(factory = VetViewModelFactory { ServiciosViewModel(app.repository) })
                        FormularioServicioScreen(vm, servicioId = id, onTerminar = { nav.popBackStack() })
                    }
                    composable(Rutas.AJUSTES) {
                        AjustesScreen(
                            usuario = usuario,
                            modoOscuro = oscuro,
                            notificaciones = notificaciones,
                            aceptarCitas = aceptarCitas,
                            onModoOscuro = sesion::cambiarModoOscuro,
                            onNotificaciones = sesion::cambiarNotificaciones,
                            onAceptarCitas = sesion::cambiarAceptarCitas,
                            onCambiarPerfil = sesion::cambiarPerfil,
                            onCerrarSesion = { cerrarSesion(nav, sesion) }
                        )
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
        popUpTo(raiz)
        launchSingleTop = true
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
