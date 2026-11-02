package com.tecsup.huellitasvet.model

// Perfil del usuario. En la base se guarda Rol.name ("CLIENTE", "ADMIN" o "ADMINISTRADOR")
// Ojo: ADMIN es el Veterinario; ADMINISTRADOR es el nuevo perfil de administrador
enum class Rol(val etiqueta: String) {
    CLIENTE("Dueño"),
    ADMIN("Veterinario"),
    ADMINISTRADOR("Administrador")
}
