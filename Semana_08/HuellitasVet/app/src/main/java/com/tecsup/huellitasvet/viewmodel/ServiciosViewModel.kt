package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.huellitasvet.data.local.entity.Servicio
import com.tecsup.huellitasvet.data.repository.VetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Lo que se escribe en el formulario de servicio
data class FormServicio(
    val id: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val duracionMin: Int = 30,
    val precio: String = "",
    val icono: String = "heart",
    val activo: Boolean = true
)

data class ErroresServicio(val nombre: String? = null, val duracion: String? = null, val precio: String? = null)

// Ejemplo resuelto: Flow del Repository -> StateFlow con stateIn
class ServiciosViewModel(private val repo: VetRepository) : ViewModel() {

    val servicios: StateFlow<List<Servicio>> = repo.observarServicios()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _form = MutableStateFlow(FormServicio())
    val form: StateFlow<FormServicio> = _form.asStateFlow()

    private val _errores = MutableStateFlow(ErroresServicio())
    val errores: StateFlow<ErroresServicio> = _errores.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private val _terminado = MutableStateFlow(false)
    val terminado: StateFlow<Boolean> = _terminado.asStateFlow()

    fun alternar(s: Servicio) {
        viewModelScope.launch { repo.cambiarActivo(s.id, !s.activo) }
    }

    // Al editar se llena el formulario con el servicio actual
    fun cargar(id: Int) {
        if (id == 0 || _form.value.id == id) return
        viewModelScope.launch {
            repo.observarServicios().first().firstOrNull { it.id == id }?.let { s ->
                _form.value = FormServicio(s.id, s.nombre, s.descripcion, s.duracionMin, s.precio.toString(), s.icono, s.activo)
            }
        }
    }

    fun cambiarNombre(v: String) { _form.update { it.copy(nombre = v) }; _errores.update { it.copy(nombre = null) } }
    fun cambiarDescripcion(v: String) { _form.update { it.copy(descripcion = v) } }
    fun cambiarDuracion(v: Int) { _form.update { it.copy(duracionMin = v) }; _errores.update { it.copy(duracion = null) } }
    fun cambiarPrecio(v: String) { _form.update { it.copy(precio = v) }; _errores.update { it.copy(precio = null) } }
    fun cambiarIcono(v: String) { _form.update { it.copy(icono = v) } }
    fun cambiarActivo(v: Boolean) { _form.update { it.copy(activo = v) } }

    // Nuevo o editado. Nombre obligatorio, duracion 15/30/45/60 y precio > 0
    fun guardar() {
        val f = _form.value
        val precio = f.precio.replace(',', '.').toDoubleOrNull()
        val errores = ErroresServicio(
            nombre = if (f.nombre.isBlank()) "El nombre es obligatorio" else null,
            duracion = if (f.duracionMin !in DURACIONES) "Elige 15, 30, 45 o 60 minutos" else null,
            precio = if (precio == null || precio <= 0.0) "El precio debe ser mayor que 0" else null
        )
        _errores.value = errores
        if (errores.nombre != null || errores.duracion != null || errores.precio != null) return
        viewModelScope.launch {
            repo.guardarServicio(
                Servicio(f.id, f.nombre.trim(), f.descripcion.trim(), f.duracionMin, precio!!, f.activo, f.icono)
            )
            _terminado.value = true
        }
    }

    // Regla: un servicio con citas no se elimina (solo se desactiva)
    fun eliminar(s: Servicio) {
        viewModelScope.launch {
            if (repo.servicioTieneCitas(s.id)) {
                _mensaje.value = "No se puede eliminar ${s.nombre} porque tiene citas. Puedes desactivarlo"
            } else {
                repo.eliminarServicio(s)
                _terminado.value = true
            }
        }
    }

    // Elimina el servicio que esta en el formulario
    fun eliminarActual() {
        val f = _form.value
        if (f.id == 0) return
        val s = servicios.value.firstOrNull { it.id == f.id }
            ?: Servicio(f.id, f.nombre, f.descripcion, f.duracionMin, f.precio.toDoubleOrNull() ?: 0.0, f.activo, f.icono)
        eliminar(s)
    }

    fun limpiarMensaje() { _mensaje.value = null }

    companion object {
        val DURACIONES = listOf(15, 30, 45, 60)
    }
}
