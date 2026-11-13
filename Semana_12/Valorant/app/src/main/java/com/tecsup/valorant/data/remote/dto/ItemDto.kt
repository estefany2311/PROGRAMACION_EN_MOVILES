package com.tecsup.valorant.data.remote.dto

// La API envuelve todo en { "status": 200, "data": ... }
data class RespuestaLista(val status: Int, val data: List<ItemDto>)
data class RespuestaDetalle(val status: Int, val data: ItemDto)

// Los nombres coinciden EXACTAMENTE con el JSON de valorant-api.com
data class ItemDto(
    val uuid: String,
    val displayName: String,
    val description: String?,
    val displayIcon: String?,
    val fullPortrait: String?,
    val role: RolDto?,
    val abilities: List<HabilidadDto>?
)

data class RolDto(val displayName: String)

data class HabilidadDto(
    val slot: String?,
    val displayName: String,
    val description: String?,
    val displayIcon: String?
)