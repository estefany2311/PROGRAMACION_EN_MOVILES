package com.chavez.campusgps.util

import kotlin.math.roundToInt

private const val METROS_POR_MINUTO = 80f

// Minutos caminando a 80 m por minuto, mínimo 1. Sin distancia calculada -> null
fun minutosAPie(metros: Float?): Int? =
    metros?.let { (it / METROS_POR_MINUTO).roundToInt().coerceAtLeast(1) }

fun formatearMinutosAPie(minutos: Int): String = "$minutos min a pie"
