package com.tecsup.valorant.data.repository

import com.tecsup.valorant.data.local.ItemDao
import com.tecsup.valorant.data.mapper.aEntity
import com.tecsup.valorant.data.mapper.aModelo
import com.tecsup.valorant.data.remote.ApiService
import com.tecsup.valorant.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class ItemRepositoryImpl(
    private val api: ApiService,
    private val dao: ItemDao
) : ItemRepository {

    // La UI SIEMPRE lee de Room
    override val items: Flow<List<Item>> =
        dao.todos().map { lista -> lista.map { it.aModelo() } }

    override val favoritos: Flow<List<Item>> =
        dao.favoritos().map { lista -> lista.map { it.aModelo() } }

    // Retrofit solo ACTUALIZA Room
    override suspend fun refrescar(): Result<Int> = try {
        // Guardamos los favoritos antes, para no perderlos con el Upsert
        val idsFavoritos = dao.idsFavoritos().toSet()
        val nuevos = api.listar().data.map { it.aEntity(esFavorito = it.uuid in idsFavoritos) }
        dao.guardarTodos(nuevos)
        Result.success(nuevos.size)
    } catch (e: IOException) {          // sin internet
        Result.failure(e)
    } catch (e: HttpException) {        // 404, 500...
        Result.failure(e)
    }

    override suspend fun obtenerDetalle(id: String): Result<Item> = try {
        val esFavorito = dao.porId(id)?.esFavorito ?: false
        Result.success(api.detalle(id).data.aModelo(esFavorito))
    } catch (e: IOException) {
        // Sin internet: mostramos lo básico que está guardado en Room
        val guardado = dao.porId(id)
        if (guardado != null) Result.success(guardado.aModelo()) else Result.failure(e)

    } catch (e: HttpException) {
        Result.failure(e)
    }

    override suspend fun alternarFavorito(id: String) = dao.alternarFavorito(id)
    override suspend fun cantidadGuardada() = dao.contar()
    override suspend fun cantidadFavoritos() = dao.contarFavoritos()
    override suspend fun limpiarCache() = dao.borrarTodo()
}