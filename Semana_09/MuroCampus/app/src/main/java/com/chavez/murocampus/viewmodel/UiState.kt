package com.chavez.murocampus.viewmodel

// Los tres estados de cualquier pantalla que carga datos de la red.
sealed interface UiState<out T> {
    data object Cargando : UiState<Nothing>
    data class Exito<T>(val datos: T) : UiState<T>
    data class Error(val mensaje: String) : UiState<Nothing>
}
