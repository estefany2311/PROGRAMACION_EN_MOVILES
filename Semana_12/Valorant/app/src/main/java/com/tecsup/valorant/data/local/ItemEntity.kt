package com.tecsup.valorant.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// En Room guardamos solo lo básico de la LISTA.
// La descripción y las habilidades vienen del endpoint de DETALLE.
@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val rol: String,
    val iconoUrl: String?,
    val esFavorito: Boolean = false
)
