package com.tecsup.valorant.data.auth

import com.tecsup.valorant.domain.model.Sesion

interface AuthRepository {
    suspend fun iniciarSesion(correo: String, clave: String): Result<Sesion>
    suspend fun sesionActual(): Sesion?   // null si no hay sesión
    fun cerrarSesion()
}