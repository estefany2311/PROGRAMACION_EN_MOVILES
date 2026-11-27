package com.chavez.murocampus.data.remote

import com.chavez.murocampus.model.Comentario
import com.chavez.murocampus.model.NuevoPost
import com.chavez.murocampus.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// Describe las llamadas al servicio. Las rutas no empiezan con "/" para no reemplazar
// el camino de la URL base.
interface PostApi {

    // GET posts
    @GET("posts")
    suspend fun listar(): List<Post>

    // GET posts/{id}
    @GET("posts/{id}")
    suspend fun detalle(@Path("id") id: Int): Post

    // GET posts/{id}/comments
    @GET("posts/{id}/comments")
    suspend fun comentarios(@Path("id") id: Int): List<Comentario>

    // GET posts?userId=N
    @GET("posts")
    suspend fun porUsuario(@Query("userId") userId: Int): List<Post>

    // POST posts con el JSON de NuevoPost en el cuerpo
    @POST("posts")
    suspend fun crear(@Body nuevo: NuevoPost): Post

    // DELETE posts/{id}: la respuesta viene vacía; Response<Unit> permite revisar el código.
    @DELETE("posts/{id}")
    suspend fun borrar(@Path("id") id: Int): Response<Unit>
}
