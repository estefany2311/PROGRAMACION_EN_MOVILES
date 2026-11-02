package com.tecsup.huellitasvet.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Tabla usuarios: dueños (CLIENTE), veterinarios (ADMIN) y administradores (ADMINISTRADOR)
@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["correo"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val clave: String,
    val rol: String,              // Rol.name: CLIENTE | ADMIN | ADMINISTRADOR
    val apellido: String = "",    // nuevo en la version 2
    val codigo: String? = null    // nuevo en la version 2: solo el administrador tiene codigo
)
