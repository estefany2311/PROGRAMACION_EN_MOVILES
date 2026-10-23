package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.data.prefs.PreferenciasStore
import com.tecsup.huellitasvet.data.repository.VetRepository
import com.tecsup.huellitasvet.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Sesion de la app: quien entro, con que rol, y el modo oscuro
class SesionViewModel(
    private val repo: VetRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // true cuando ya se reviso si habia un perfil guardado en DataStore
    private val _sesionRevisada = MutableStateFlow(false)
    val sesionRevisada: StateFlow<Boolean> = _sesionRevisada.asStateFlow()

    val modoOscuro: StateFlow<Boolean> = prefs.modoOscuro
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val notificaciones: StateFlow<Boolean> = prefs.notificaciones
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val aceptarCitas: StateFlow<Boolean> = prefs.aceptarCitas
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    init {
        // Si ya hay perfil guardado, se recupera el usuario y se salta el login
        viewModelScope.launch {
            val perfil = prefs.perfilActivo.first()
            val id = prefs.usuarioId.first()
            if (perfil.isNotEmpty() && id > 0) {
                _usuarioActual.value = repo.obtenerUsuario(id)
            }
            _sesionRevisada.value = true
        }
    }

    fun login(correo: String, clave: String, rolSeleccionado: Rol) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Ingresa tu correo y tu contraseña"
            return
        }
        viewModelScope.launch {
            val usuario = repo.login(correo.trim(), clave)
            when {
                usuario == null -> _error.value = "Correo o contraseña incorrectos"
                usuario.rol != rolSeleccionado.name -> {
                    val rolReal = Rol.valueOf(usuario.rol).etiqueta
                    _error.value = "Esta cuenta es de $rolReal. Elige la pestaña $rolReal"
                }
                else -> {
                    _error.value = null
                    prefs.guardarSesion(usuario.rol, usuario.id)
                    _usuarioActual.value = usuario
                }
            }
        }
    }

    fun limpiarError() {
        _error.value = null
    }

    fun cambiarModoOscuro(b: Boolean) { viewModelScope.launch { prefs.guardarModoOscuro(b) } }
    fun cambiarNotificaciones(b: Boolean) { viewModelScope.launch { prefs.guardarNotificaciones(b) } }
    fun cambiarAceptarCitas(b: Boolean) { viewModelScope.launch { prefs.guardarAceptarCitas(b) } }

    // Modo practica: guarda el otro perfil (su primer usuario) y lo deja como sesion activa
    fun cambiarPerfil() {
        val actual = _usuarioActual.value ?: return
        val otro = if (actual.rol == Rol.ADMIN.name) Rol.CLIENTE else Rol.ADMIN
        viewModelScope.launch {
            val usuario = repo.primerUsuarioConRol(otro) ?: return@launch
            prefs.guardarSesion(usuario.rol, usuario.id)
            _usuarioActual.value = usuario
        }
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
        _error.value = null
        viewModelScope.launch { prefs.cerrarSesion() }
    }
}
