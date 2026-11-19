package com.chavez.campusgps.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chavez.campusgps.data.LugaresRepository
import com.chavez.campusgps.data.UbicacionNoDisponibleException
import com.chavez.campusgps.data.UbicacionRepository
import com.chavez.campusgps.model.LugarConDistancia
import com.chavez.campusgps.model.Posicion
import com.chavez.campusgps.model.TipoError
import com.chavez.campusgps.util.distanciaMetros
import com.chavez.campusgps.model.UbicacionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    private val _lugares = MutableStateFlow(
        lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) }
    )
    val lugares: StateFlow<List<LugarConDistancia>> = _lugares.asStateFlow()

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    fun buscar() {
        if (!ubicacionRepo.playServicesDisponible()) {
            _estado.value = UbicacionUi.Error(
                "Este dispositivo no tiene Google Play Services",
                TipoError.SIN_PLAY_SERVICES
            )
            return
        }
        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    _estado.value = UbicacionUi.Lista(it)
                    actualizarDistancias(it)
                }
                .onFailure { _estado.value = errorDe(it) }
        }
    }

    // Calcula la distancia a cada lugar y publica la lista ordenada de menor a mayor
    private fun actualizarDistancias(pos: Posicion) {
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { LugarConDistancia(it, distanciaMetros(pos, it)) }
            .sortedBy { it.metros }
        Log.d("CampusGPS", "Lugares: " + _lugares.value.joinToString { "${it.lugar.nombre}=${it.metros}" })
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            "Sin permiso de ubicación no puedo mostrar dónde estás",
            TipoError.SIN_PERMISO
        )
    }

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
    }

    // El más cercano: el primero con distancia calculada (null si aún no hay ubicación)
    val masCercano: StateFlow<LugarConDistancia?> = lugares
        .map { lista -> lista.firstOrNull { it.metros != null } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private fun errorDe(e: Throwable): UbicacionUi.Error = when (e) {
        is SecurityException ->
            UbicacionUi.Error("Falta el permiso de ubicación", TipoError.SIN_PERMISO)
        is UbicacionNoDisponibleException ->
            UbicacionUi.Error("Activa el GPS e intenta de nuevo", TipoError.GPS_APAGADO)
        else ->
            UbicacionUi.Error(e.message ?: "No se pudo obtener la ubicación", TipoError.OTRO)
    }
}
