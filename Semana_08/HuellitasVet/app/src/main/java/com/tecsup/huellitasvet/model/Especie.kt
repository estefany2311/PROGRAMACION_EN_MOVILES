package com.tecsup.huellitasvet.model

enum class Especie(val etiqueta: String, val plural: String) {
    PERRO("Perro", "Perros"),
    GATO("Gato", "Gatos"),
    OTRO("Otro", "Otros");

    companion object {
        fun desde(valor: String): Especie = entries.firstOrNull { it.name == valor } ?: OTRO
    }
}
