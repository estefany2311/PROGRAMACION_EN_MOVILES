package com.tecsup.valorant.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.valorant.AppContainer
import com.tecsup.valorant.data.repository.ItemRepository
import com.tecsup.valorant.notification.Notificador
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.mensajeDeError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Totales(val guardados: Int, val favoritos: Int)

class AdminViewModel(
    private val repo: ItemRepository,
    private val notificador: Notificador
) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<Totales>>(UiState.Cargando)
    val estado: StateFlow<UiState<Totales>> = _estado.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    init { cargarTotales() }

    fun cargarTotales() {
        viewModelScope.launch {
            _estado.value = UiState.Exito(Totales(repo.cantidadGuardada(), repo.cantidadFavoritos()))
        }
    }

    fun sincronizar() {
        viewModelScope.launch {
            _mensaje.value = "Sincronizando..."
            repo.refrescar()
                .onSuccess { n ->
                    _mensaje.value = "Se guardaron $n elementos"
                    notificador.mostrar("Datos actualizados", "Se guardaron $n elementos")
                }
                .onFailure { _mensaje.value = mensajeDeError(it) }
            cargarTotales()
        }
    }

    fun limpiarCache() {
        viewModelScope.launch {
            repo.limpiarCache()
            _mensaje.value = "Caché limpiada"
            cargarTotales()
        }
    }

    companion object {
        fun factory(c: AppContainer) = viewModelFactory {
            initializer { AdminViewModel(c.itemRepository, c.notificador) }
        }
    }
}
