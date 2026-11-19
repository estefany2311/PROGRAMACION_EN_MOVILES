package com.chavez.campusgps.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chavez.campusgps.data.FavoritosRepository
import com.chavez.campusgps.data.LugaresRepository
import com.chavez.campusgps.data.UbicacionNoDisponibleException
import com.chavez.campusgps.data.UbicacionRepository
import com.chavez.campusgps.model.LugarConDistancia
import com.chavez.campusgps.model.Posicion
import com.chavez.campusgps.model.TipoError
import com.chavez.campusgps.util.dentroDelRadio
import com.chavez.campusgps.util.distanciaMetros
import com.chavez.campusgps.util.minutosAPie
import com.chavez.campusgps.model.UbicacionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository,
    private val favoritosRepo: FavoritosRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    // Lista completa (con o sin distancia); la que ve la pantalla es `lugares`, ya filtrada
    private val _lugares = MutableStateFlow(
        lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) }
    )

    // Categorías disponibles, sacadas de los datos (Estudio, Comida, Eventos, Servicios)
    val categorias: List<String> = lugaresRepo.obtenerLugares().map { it.categoria }.distinct()

    // null = "Todos"
    private val _categoria = MutableStateFlow<String?>(null)
    val categoria: StateFlow<String?> = _categoria.asStateFlow()

    // Radio de cercanía en metros; null = "Sin radio"
    private val _radio = MutableStateFlow<Int?>(null)
    val radio: StateFlow<Int?> = _radio.asStateFlow()

    // Ids favoritos (vienen de DataStore y se actualizan solos)
    private val favoritos: StateFlow<Set<Int>> = favoritosRepo.favoritos
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    // true = chip "Mis lugares" activo (solo favoritos)
    private val _soloFavoritos = MutableStateFlow(false)
    val soloFavoritos: StateFlow<Boolean> = _soloFavoritos.asStateFlow()

    // Lista filtrada por categoría y favoritos, marcada con "dentro del radio", minutos a pie
    // y favorito: la usan el mapa, la lista y el "más cercano"
    val lugares: StateFlow<List<LugarConDistancia>> =
        combine(_lugares, _categoria, _radio, _soloFavoritos, favoritos) { lista, cat, radio, soloFav, favs ->
            lista
                .filter { cat == null || it.lugar.categoria == cat }
                .filter { !soloFav || it.lugar.id in favs }
                .map {
                    it.copy(
                        dentroDelRadio = dentroDelRadio(it.metros, radio),
                        minutosAPie = minutosAPie(it.metros),
                        esFavorito = it.lugar.id in favs
                    )
                }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _lugares.value)

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

    fun seleccionarRadio(metros: Int?) {
        _radio.value = metros
    }

    // Marca o quita un lugar como favorito (se guarda en DataStore)
    fun alternarFavorito(id: Int) {
        viewModelScope.launch { favoritosRepo.alternar(id) }
    }

    // Activa o desactiva el chip "Mis lugares"
    fun alternarMisLugares() {
        _soloFavoritos.value = !_soloFavoritos.value
        // si el lugar seleccionado no es favorito, queda fuera de la lista y se deselecciona
        if (_soloFavoritos.value && _seleccionado.value !in favoritos.value) {
            _seleccionado.value = null
        }
    }

    // Tocar la categoría ya activa la quita (vuelve a "Todos"); null también es "Todos"
    fun filtrarPorCategoria(cat: String?) {
        _categoria.value = if (cat == _categoria.value) null else cat
        // si el lugar seleccionado quedó fuera del filtro, se deselecciona
        val actual = _categoria.value
        val sel = _lugares.value.firstOrNull { it.lugar.id == _seleccionado.value }
        if (actual != null && sel != null && sel.lugar.categoria != actual) {
            _seleccionado.value = null
        }
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
