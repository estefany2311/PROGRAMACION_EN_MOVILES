package com.saludplus.citas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saludplus.citas.ui.screens.agendamiento.CitaExitosaScreen
import com.saludplus.citas.ui.screens.agendamiento.ConfirmarCitaScreen
import com.saludplus.citas.ui.screens.agendamiento.EspecialidadesScreen
import com.saludplus.citas.ui.screens.agendamiento.FechaHoraScreen
import com.saludplus.citas.ui.screens.agendamiento.MedicosScreen
import com.saludplus.citas.ui.screens.auth.LoginScreen
import com.saludplus.citas.ui.screens.auth.RegistroScreen
import com.saludplus.citas.ui.screens.auth.SplashScreen
import com.saludplus.citas.ui.screens.auth.TerminosScreen
import com.saludplus.citas.ui.screens.citas.DetalleCitaScreen
import com.saludplus.citas.ui.screens.citas.MisCitasScreen
import com.saludplus.citas.ui.screens.home.HomeScreen
import com.saludplus.citas.ui.screens.notificaciones.NotificacionesScreen
import com.saludplus.citas.ui.screens.perfil.PerfilScreen
import com.saludplus.citas.ui.screens.resultados.ResultadosScreen
import com.saludplus.citas.ui.screens.agendamiento.LocalesScreen
import com.saludplus.citas.ui.screens.doctores.MisDoctoresScreen
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.SPLASH) {

        composable(Rutas.SPLASH) { SplashScreen(navController) }
        composable(Rutas.REGISTRO) { RegistroScreen(navController) }
        composable(Rutas.LOGIN) { LoginScreen(navController) }
        composable(Rutas.TERMINOS) { TerminosScreen(navController) }
        composable(Rutas.HOME) { HomeScreen(navController) }
        composable(Rutas.LOCALES) {
            LocalesScreen(navController)
        }

        composable(Rutas.MIS_DOCTORES) {
            MisDoctoresScreen(navController)
        }
        composable(Rutas.ESPECIALIDADES) { EspecialidadesScreen(navController) }
        composable(Rutas.MIS_CITAS) { MisCitasScreen(navController) }
        composable(Rutas.PERFIL) { PerfilScreen(navController) }
        composable(Rutas.RESULTADOS) { ResultadosScreen(navController) }
        composable(Rutas.NOTIFICACIONES) { NotificacionesScreen(navController) }


        composable(
            route = Rutas.MEDICOS,
            arguments = listOf(navArgument("especialidadId") { type = NavType.IntType })
        ) { entrada ->
            val especialidadId = entrada.arguments?.getInt("especialidadId") ?: 0
            MedicosScreen(navController, especialidadId)
        }

        composable(
            route = Rutas.FECHA_HORA,
            arguments = listOf(
                navArgument("medicoId") {
                    type = NavType.IntType
                }
            )
        ) { entrada ->
            val medicoId = entrada.arguments?.getInt("medicoId") ?: 0
            FechaHoraScreen(navController, medicoId)
        }

        composable(
            route = Rutas.CONFIRMAR_CITA,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.IntType },
                navArgument("fecha") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType }
            )
        ) { entrada ->
            val medicoId = entrada.arguments?.getInt("medicoId") ?: 0
            val fecha = entrada.arguments?.getString("fecha") ?: ""
            val hora = entrada.arguments?.getString("hora") ?: ""
            ConfirmarCitaScreen(navController, medicoId, fecha, hora)
        }

        composable(
            route = Rutas.CITA_EXITOSA,
            arguments = listOf(navArgument("citaId") { type = NavType.IntType })
        ) { entrada ->
            val citaId = entrada.arguments?.getInt("citaId") ?: 0
            CitaExitosaScreen(navController, citaId)
        }

        composable(
            route = Rutas.DETALLE_CITA,
            arguments = listOf(navArgument("citaId") { type = NavType.IntType })
        ) { entrada ->
            val citaId = entrada.arguments?.getInt("citaId") ?: 0
            DetalleCitaScreen(navController, citaId)
        }
    }
}
