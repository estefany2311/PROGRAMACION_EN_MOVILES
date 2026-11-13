package com.tecsup.valorant.data.mapper

import com.tecsup.valorant.data.local.ItemEntity
import com.tecsup.valorant.data.remote.dto.ItemDto
import com.tecsup.valorant.domain.model.Habilidad
import com.tecsup.valorant.domain.model.Item

// Dto (API) -> Entity (Room)
fun ItemDto.aEntity(esFavorito: Boolean) = ItemEntity(
    id = uuid,
    nombre = displayName,
    rol = role?.displayName ?: "Sin rol",
    iconoUrl = displayIcon,
    esFavorito = esFavorito
)

// Entity (Room) -> Item (UI)
fun ItemEntity.aModelo() = Item(
    id = id,
    nombre = nombre,
    rol = rol,
    iconoUrl = iconoUrl,
    esFavorito = esFavorito
)

// Dto (detalle) -> Item (UI) con
fun ItemDto.aModelo(esFavorito: Boolean) = Item(
    id = uuid,
    nombre = displayName,
    rol = role?.displayName ?: "Sin rol",
    iconoUrl = displayIcon,
    descripcion = description.orEmpty(),
    retratoUrl = fullPortrait,
    habilidades = abilities.orEmpty().map {
        Habilidad(it.displayName, it.description.orEmpty(), it.displayIcon)
    },
    esFavorito = esFavorito
)