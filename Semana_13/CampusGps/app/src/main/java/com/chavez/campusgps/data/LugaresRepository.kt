package com.chavez.campusgps.data

import com.chavez.campusgps.model.Lugar

interface LugaresRepository {
    fun obtenerLugares(): List<Lugar>
}
