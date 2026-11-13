package com.tecsup.valorant.domain.model

data class Habilidad(
    val nombre: String,
    val descripcion: String,
    val iconoUrl: String?
)

data class Item(
    val id: String,                 // uuid del agente
    val nombre: String,
    val rol: String,
    val iconoUrl: String?,
    val descripcion: String = "",
    val retratoUrl: String? = null,
    val habilidades: List<Habilidad> = emptyList(),
    val esFavorito: Boolean = false
)