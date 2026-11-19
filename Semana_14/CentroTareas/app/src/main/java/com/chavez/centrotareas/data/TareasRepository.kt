package com.chavez.centrotareas.data

import kotlinx.coroutines.flow.Flow

interface TareasRepository {
    // Tarea simulada: emite el progreso de 0f a 1f (100 vueltas de 40 ms). mej1F2
    fun tareaConProgreso(): Flow<Float>
    suspend fun contarPrimos(hasta: Int): Int
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>
}
