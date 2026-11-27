package com.chavez.murocampus.data

import com.chavez.murocampus.data.remote.PostApi
import com.chavez.murocampus.model.Comentario
import com.chavez.murocampus.model.NuevoPost
import com.chavez.murocampus.model.Post
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

// Lo único que conoce el ViewModel: funciones que nunca lanzan, siempre devuelven Result.
interface PostRepository {
    suspend fun listar(): Result<List<Post>>
    suspend fun detalle(id: Int): Result<Post>
    suspend fun comentarios(id: Int): Result<List<Comentario>>
    suspend fun porUsuario(userId: Int): Result<List<Post>>
    suspend fun crear(nuevo: NuevoPost): Result<Post>
    suspend fun borrar(id: Int): Result<Unit>
}

// Implementación que habla con el servicio REST a través de Retrofit.
class PostRepositoryRemote(private val api: PostApi) : PostRepository {

    override suspend fun listar(): Result<List<Post>> = llamar { api.listar() }

    override suspend fun detalle(id: Int): Result<Post> = llamar { api.detalle(id) }

    override suspend fun comentarios(id: Int): Result<List<Comentario>> =
        llamar { api.comentarios(id) }

    override suspend fun porUsuario(userId: Int): Result<List<Post>> =
        llamar { api.porUsuario(userId) }

    override suspend fun crear(nuevo: NuevoPost): Result<Post> = llamar { api.crear(nuevo) }

    // Con Response<Unit> Retrofit no lanza por códigos 4xx/5xx: se revisa a mano.
    // Si no es 2xx, se lanza HttpException dentro de llamar() y termina en Result.failure.
    override suspend fun borrar(id: Int): Result<Unit> = llamar {
        val respuesta = api.borrar(id)
        if (!respuesta.isSuccessful) throw HttpException(respuesta)
    }

    // Un solo try/catch para las cinco llamadas.
    private suspend fun <T> llamar(bloque: suspend () -> T): Result<T> =
        try {
            Result.success(bloque())
        } catch (e: CancellationException) {
            throw e // nunca se traga la cancelación
        } catch (e: Exception) {
            Result.failure(e)
        }
}
