package com.tecsup.valorant

import com.tecsup.valorant.data.repository.ItemRepository
import com.tecsup.valorant.domain.model.Item
import com.tecsup.valorant.notification.Notificador
import com.tecsup.valorant.ui.UiState
import com.tecsup.valorant.ui.lista.ListaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

// Repositorio falso: no usa internet ni Room, guarda todo en memoria
class RepositorioFalso(
    guardados: List<Item> = emptyList(),
    private val resultado: Result<List<Item>>
) : ItemRepository {
    private val datos = MutableStateFlow(guardados)

    override val items: Flow<List<Item>> = datos
    override val favoritos: Flow<List<Item>> = datos.map { lista -> lista.filter { it.esFavorito } }

    override suspend fun refrescar(): Result<Int> =
        resultado.map { nuevos ->
            datos.value = nuevos
            nuevos.size
        }

    override suspend fun obtenerDetalle(id: String): Result<Item> =
        Result.success(datos.value.first { it.id == id })

    override suspend fun alternarFavorito(id: String) {}
    override suspend fun cantidadGuardada(): Int = datos.value.size
    override suspend fun cantidadFavoritos(): Int = datos.value.count { it.esFavorito }
    override suspend fun limpiarCache() {
        datos.value = emptyList()
    }
}

// Notificador falso: en vez de mostrar la notificación, guarda el texto
class NotificadorFalso : Notificador {
    val mensajes = mutableListOf<String>()
    override fun mostrar(titulo: String, texto: String) {
        mensajes.add(texto)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ListaViewModelTest {

    private val agentes = listOf(
        Item("1", "Jett", "Duelista", null),
        Item("2", "Sage", "Centinela", null),
        Item("3", "Omen", "Controlador", null)
    )

    // El ViewModel usa viewModelScope (hilo Main); en las pruebas lo reemplazamos
    @Before
    fun antes() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun despues() {
        Dispatchers.resetMain()
    }

    @Test
    fun conInternet_muestraLaListaYNotifica() = runTest {
        val notificador = NotificadorFalso()
        val vm = ListaViewModel(RepositorioFalso(resultado = Result.success(agentes)), notificador)

        val estado = vm.estado.value
        assertTrue(estado is UiState.Exito)
        assertEquals(3, (estado as UiState.Exito).datos.size)
        assertEquals(listOf("Se guardaron 3 elementos"), notificador.mensajes)
    }

    @Test
    fun sinInternetYSinDatos_muestraError() = runTest {
        val vm = ListaViewModel(
            RepositorioFalso(resultado = Result.failure(IOException())),
            NotificadorFalso()
        )

        assertEquals(UiState.Error("No hay conexión a internet"), vm.estado.value)
    }

    @Test
    fun sinInternetConDatosGuardados_muestraDatosYAviso() = runTest {
        val vm = ListaViewModel(
            RepositorioFalso(guardados = agentes.take(2), resultado = Result.failure(IOException())),
            NotificadorFalso()
        )

        val estado = vm.estado.value
        assertTrue(estado is UiState.Exito)
        assertEquals(2, (estado as UiState.Exito).datos.size)
        assertEquals("Sin conexión: mostrando datos guardados", vm.aviso.value)
    }
}