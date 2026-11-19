package com.chavez.campusgps.data

import kotlinx.coroutines.flow.Flow

interface FavoritosRepository {
    // Ids de los lugares favoritos; emite de nuevo cada vez que cambian
    val favoritos: Flow<Set<Int>>

    // Si el lugar es favorito lo quita; si no, lo agrega
    suspend fun alternar(id: Int)
}
