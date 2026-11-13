package com.tecsup.valorant.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.valorant.AppContainer
import com.tecsup.valorant.data.auth.AuthRepository
import com.tecsup.valorant.domain.model.Sesion
import com.tecsup.valorant.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Exito(null) = no hay sesión (mostrar formulario)
// Exito(sesion) = hay sesión (entrar al catálogo)
class LoginViewModel(private val auth: AuthRepository) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<Sesion?>>(UiState.Cargando)
    val estado: StateFlow<UiState<Sesion?>> = _estado.asStateFlow()

    init {
        // R11: si ya había sesión, se entra directo
        viewModelScope.launch {
            val sesion = runCatching { auth.sesionActual() }.getOrNull()
            _estado.value = UiState.Exito(sesion)
        }
    }

    fun ingresar(correo: String, clave: String) {
        // PA-04: no llama a Firebase si hay campos vacíos
        if (correo.isBlank() || clave.isBlank()) {
            _estado.value = UiState.Error("Ingresa tu correo y tu contraseña")
            return
        }
        viewModelScope.launch {
            _estado.value = UiState.Cargando
            auth.iniciarSesion(correo.trim(), clave)
                .onSuccess { _estado.value = UiState.Exito(it) }
                .onFailure { _estado.value = UiState.Error("Correo o contraseña incorrectos") }
        }
    }

    fun cerrarSesion() {
        auth.cerrarSesion()
        _estado.value = UiState.Exito(null)
    }

    companion object {
        fun factory(c: AppContainer) = viewModelFactory {
            initializer { LoginViewModel(c.authRepository) }
        }
    }
}