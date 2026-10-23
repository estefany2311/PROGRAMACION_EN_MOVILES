package com.tecsup.huellitasvet.model

// Perfil del usuario. En la base se guarda Rol.name ("CLIENTE" o "ADMIN")
enum class Rol(val etiqueta: String) {
    CLIENTE("Dueño"),
    ADMIN("Veterinario")
}
