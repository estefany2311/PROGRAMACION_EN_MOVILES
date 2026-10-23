package com.tecsup.huellitasvet.model

enum class Sexo(val etiqueta: String) {
    MACHO("Macho"),
    HEMBRA("Hembra");

    companion object {
        fun desde(valor: String): Sexo = entries.firstOrNull { it.name == valor } ?: MACHO
    }
}
