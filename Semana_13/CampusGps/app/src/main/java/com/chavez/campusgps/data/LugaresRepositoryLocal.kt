package com.chavez.campusgps.data

import com.chavez.campusgps.model.Lugar

class LugaresRepositoryLocal : LugaresRepository {

    override fun obtenerLugares(): List<Lugar> = listOf(
        Lugar(1, "Tecsup", "Estudio", -12.0448814, -76.9514571),
        Lugar(2, "Mall Aventura Santa Anita", "Tiendas", -12.0566156, -76.9709436),
        Lugar(3, "Farmacia Mas Salud", "Servicios", -12.0454076, -76.9584374),
        Lugar(4, "Mercado Productores", "Tiendas", -12.0420392, -76.9465293),
        Lugar(5, "Polideportivo", "Campus", -12.04348028532463, -76.95384541729312),
        Lugar(6, "Pabellón 10", "Campus", -12.043727, -76.952826),
        Lugar(7, "Pabellón 16", "Campus", -12.043398, -76.9527886),
        Lugar(8, "Canchas Deportivas", "Campus", -12.0425876, -76.9524681)
    )
}