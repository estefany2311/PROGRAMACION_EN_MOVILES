package com.chavez.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chavez.centrotareas.data.TareasRepository
import com.chavez.centrotareas.model.CargaUi
import com.chavez.centrotareas.model.PrimosUi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {

    // ----- Tarea con progreso
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    // Paso 3: progreso de 0f a 1f (solo lectura hacia afuera)
    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    // Paso 4: Job de la tarea y bandera de "corriendo"
    private var trabajo: Job? = null
    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    // Paso 2 + 3 + 4: segundo plano, progreso y cancelación
    fun tareaLarga() {
        if (_corriendo.value) return // doble inicio: no hace nada
        _corriendo.value = true
        trabajo = viewModelScope.launch {
            try {
                _progreso.value = 0f
                _mensaje.value = "Trabajando..."
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                // F2M1 El trabajo vive en el Repository (Flow + flowOn); aquí solo se recoge
                repo.tareaConProgreso().collect { _progreso.value = it }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                _corriendo.value = false // termine bien o cancelada
            }
        }
    }
//CMIT5
    fun cancelar() {
        if (!_corriendo.value) return
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }


    // ----- Contar primos (paso 5)
    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()
    private var trabajoPrimos: Job? = null

    fun contar(hasta: Int) {
        if (hasta < 2 || _primos.value == PrimosUi.Calculando) return
        _primos.value = PrimosUi.Calculando
        trabajoPrimos = viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val cantidad = repo.contarPrimos(hasta)
                _primos.value = PrimosUi.Listo(cantidad, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e // siempre relanzar la cancelación
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    // ----- Dos cargas (pasos 6 y 7)
    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()

    // Paso 7: interruptor para simular que los pedidos fallan
    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    // Una por una: primero el perfil (2 s) y después los pedidos (3 s) = ~5 s
    fun cargarUnaPorUna() {
        if (_carga.value == CargaUi.Cargando) return
        _carga.value = CargaUi.Cargando
        viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val perfil = repo.cargarPerfil()
                val pedidos = repo.cargarPedidos(_simularError.value)
                _carga.value = CargaUi.Exito(
                    modo = "Una por una",
                    perfil = perfil,
                    pedidos = pedidos.size,
                    milisegundos = System.currentTimeMillis() - inicio
                )
            } catch (e: Exception) {
                if (e is CancellationException) throw e // nunca tragar la cancelación
                _carga.value = CargaUi.Error("No se pudo cargar: " + e.message)
            }
        }
    }

    // A la vez: las dos llamadas en paralelo con async = ~3 s (el máximo de las dos)
    fun cargarALaVez() {
        if (_carga.value == CargaUi.Cargando) return
        _carga.value = CargaUi.Cargando
        viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = coroutineScope {
                    val a = async { repo.cargarPerfil() }
                    val b = async { repo.cargarPedidos(_simularError.value) }
                    Pair(a.await(), b.await())
                }
                _carga.value = CargaUi.Exito(
                    modo = "A la vez",
                    perfil = perfil,
                    pedidos = pedidos.size,
                    milisegundos = System.currentTimeMillis() - inicio
                )
            } catch (e: Exception) {
                if (e is CancellationException) throw e // nunca tragar la cancelación
                _carga.value = CargaUi.Error("No se pudo cargar: " + e.message)
            }
        }
    }

}
