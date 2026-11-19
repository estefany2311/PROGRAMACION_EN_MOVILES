package com.chavez.centrotareas.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

/**
 * Ejecuta [bloque] y, si falla, lo reintenta hasta [maxReintentos] veces con espera creciente
 * (1 s, 2 s, 4 s con los valores por defecto). Si el último intento también falla, la
 * excepción sube a quien llamó. La CancellationException nunca se traga ni se reintenta.
 */
suspend fun <T> reintentar(
    maxReintentos: Int = 3,
    esperaInicialMs: Long = 1000,
    onReintento: (intento: Int, maximo: Int) -> Unit = { _, _ -> },
    bloque: suspend () -> T
): T {
    var espera = esperaInicialMs
    repeat(maxReintentos) { n ->
        try {
            return bloque()
        } catch (e: Exception) {
            if (e is CancellationException) throw e // cancelar o timeout: no se reintenta
            onReintento(n + 1, maxReintentos)
            delay(espera)
            espera *= 2
        }
    }
    return bloque() // último intento: si falla, la excepción llega al llamador
}
