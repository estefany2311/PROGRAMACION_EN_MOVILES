package com.saludplus.citas.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.BotonTemporal
import com.saludplus.citas.ui.components.PantallaEnConstruccion

// TODO: Estados con remember/mutableStateOf para: nombre, teléfono, correo (opcional) y contraseña.
// TODO: 4 OutlinedTextField con su ícono. Validar: nombre no vacío, teléfono de 9 dígitos, contraseña de al menos 6 caracteres.
// TODO: Mostrar los mensajes de error y, si todo es válido, llamar a Repositorio.registrarUsuario.
// TODO: Si ya existe el teléfono, avisar al usuario. Enlace 'Términos y Condiciones' (va a Terminos) y 'Iniciar sesión' (va a Login).
// Al terminar esta pantalla, borra la llamada a PantallaEnConstruccion.
@Composable
fun RegistroScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        nombre = "Registro",
        botones = listOf(
            BotonTemporal("Ir a Inicio") { navController.navigate(Rutas.HOME) },
            BotonTemporal("Términos y Condiciones") { navController.navigate(Rutas.TERMINOS) },
            BotonTemporal("Ya tengo cuenta: Iniciar sesión") { navController.navigate(Rutas.LOGIN) }
        )
    )
}
