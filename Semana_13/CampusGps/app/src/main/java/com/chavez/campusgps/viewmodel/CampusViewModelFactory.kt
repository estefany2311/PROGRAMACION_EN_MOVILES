package com.chavez.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chavez.campusgps.data.FavoritosRepository
import com.chavez.campusgps.data.LugaresRepository
import com.chavez.campusgps.data.UbicacionRepository

class CampusViewModelFactory(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository,
    private val favoritosRepo: FavoritosRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapaViewModel::class.java)) {
            return MapaViewModel(ubicacionRepo, lugaresRepo, favoritosRepo) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
