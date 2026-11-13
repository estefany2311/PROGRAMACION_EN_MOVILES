package com.tecsup.valorant.data.remote

import com.tecsup.valorant.data.remote.dto.RespuestaDetalle
import com.tecsup.valorant.data.remote.dto.RespuestaLista
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v1/agents")
    suspend fun listar(
        @Query("isPlayableCharacter") jugable: Boolean = true,
        @Query("language") idioma: String = "es-MX"
    ): RespuestaLista

    @GET("v1/agents/{uuid}")
    suspend fun detalle(
        @Path("uuid") id: String,
        @Query("language") idioma: String = "es-MX"
    ): RespuestaDetalle
}