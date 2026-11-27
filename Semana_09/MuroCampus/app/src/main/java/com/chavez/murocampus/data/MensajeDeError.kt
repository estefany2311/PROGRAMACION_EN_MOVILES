package com.chavez.murocampus.data

import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

// Traduce la excepción de un Result.failure a un texto para la pantalla.
// Vive en data/ para que viewmodel/ no conozca HttpException.
fun mensajeDe(e: Throwable): String = when (e) {
    is IOException -> "Sin conexión a internet.\nRevisa tu red e inténtalo otra vez."
    is HttpException -> "Error del servidor (${e.code()})"
    is SerializationException -> "Respuesta inesperada"
    else -> "Error desconocido"
}
