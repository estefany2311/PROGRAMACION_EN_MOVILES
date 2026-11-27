package com.chavez.murocampus.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// Cliente único de Retrofit para toda la app.
object RetrofitClient {

    // Ignora campos del JSON que no estén en los modelos.
    private val json = Json { ignoreUnknownKeys = true }

    private val cliente = OkHttpClient.Builder()
        // TODO 1: registra peticiones y respuestas completas en Logcat (solo para desarrollo)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    // by lazy: se crea una sola vez, la primera vez que se usa.
    val api: PostApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // termina con /
            .client(cliente)
            // TODO 2: conversor JSON <-> data class con kotlinx.serialization
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PostApi::class.java)
    }
}
