package com.saludplus.citas.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Campos de teléfono y contraseña con estados.
// TODO: Al pulsar 'Ingresar' llamar a Repositorio.iniciarSesion; si es correcto ir a Inicio, si no mostrar error.
// TODO: Enlace para crear cuenta (va a Registro). Diseña esta vista con el mismo estilo que el Registro.
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun LoginScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Iniciar sesión",
        botones = listOf(
            BotonTemporal("Ingresar") { navController.navigate(Rutas.HOME) },
            BotonTemporal("Crear cuenta") { navController.navigate(Rutas.REGISTRO) }
        )
    )
}
