package com.tecsup.valorant.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.valorant.AppContainer
import com.tecsup.valorant.data.repository.ItemRepository
import com.tecsup.valorant.domain.model.Item
import com.tecsup.valorant.notification.Notificador
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.mensajeDeError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ListaViewModel(
    private val repo: ItemRepository,
    private val notificador: Notificador
) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
    val estado: StateFlow<UiState<List<Item>>> = _estado.asStateFlow()

    private val _aviso = MutableStateFlow<String?>(null)
    val aviso: StateFlow<String?> = _aviso.asStateFlow()

    // Filtro por rol: null = "Todos"
    private val _rol = MutableStateFlow<String?>(null)
    val rol: StateFlow<String?> = _rol.asStateFlow()

    // Texto del buscador por nombre ("" = sin búsqueda)
    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private var refrescoTerminado = false

    init {
        // La lista que ve el usuario sale de Room, filtrada por rol y por nombre.
        // combine vuelve a calcular cada vez que cambian los datos, el rol O el texto.
        viewModelScope.launch {
            combine(repo.items, _rol, _busqueda) { lista, rol, texto ->
                Triple(lista, rol, texto)
            }.collect { (lista, rol, texto) ->
                if (lista.isNotEmpty() || refrescoTerminado) {
                    _estado.value = UiState.Exito(filtrarPorNombre(filtrarPorRol(lista, rol), texto))
                }
            }
        }
        refrescar()
    }

    fun refrescar() {
        viewModelScope.launch {
            if (_estado.value is UiState.Error) _estado.value = UiState.Cargando
            val resultado = repo.refrescar()
            refrescoTerminado = true
            resultado
                .onSuccess { n ->
                    if (n == 0 && _estado.value is UiState.Cargando) _estado.value = UiState.Exito(emptyList())
                    _aviso.value = null
                    notificador.mostrar("Datos actualizados", "Se guardaron $n elementos")
                }
                .onFailure { e ->
                    if (repo.cantidadGuardada() == 0) _estado.value = UiState.Error(mensajeDeError(e))
                    else _aviso.value = "Sin conexión: mostrando datos guardados"
                }
        }
    }

    fun elegirRol(rol: String?) {
        _rol.value = rol
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
    }

    fun alternarFavorito(id: String) {
        viewModelScope.launch { repo.alternarFavorito(id) }
    }

    companion object {
        // Los 4 roles de Valorant, tal como los devuelve la API en español
        val ROLES = listOf("Duelista", "Iniciador", "Controlador", "Centinela")

        // Función pura: fácil de probar en las pruebas unitarias
        fun filtrarPorRol(lista: List<Item>, rol: String?): List<Item> =
            if (rol == null) lista else lista.filter { it.rol.equals(rol, ignoreCase = true) }

        // Busca por nombre sin importar mayúsculas ni espacios extra
        fun filtrarPorNombre(lista: List<Item>, texto: String): List<Item> {
            val t = texto.trim()
            return if (t.isEmpty()) lista else lista.filter { it.nombre.contains(t, ignoreCase = true) }
        }

        fun factory(c: AppContainer) = viewModelFactory {
            initializer { ListaViewModel(c.itemRepository, c.notificador) }
        }
    }
}
