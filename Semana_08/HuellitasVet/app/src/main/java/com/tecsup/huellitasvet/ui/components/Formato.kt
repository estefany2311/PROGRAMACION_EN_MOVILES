package com.tecsup.huellitasvet.ui.components

import java.util.Locale

// 45.0 -> "S/ 45.00"
fun soles(valor: Double): String = "S/ " + String.format(Locale.US, "%,.2f", valor)
