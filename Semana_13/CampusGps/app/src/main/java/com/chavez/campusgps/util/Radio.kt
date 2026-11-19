package com.chavez.campusgps.util

// ¿El lugar está dentro del radio elegido? Sin radio (null) o sin distancia calculada -> false
fun dentroDelRadio(metros: Float?, radioMetros: Int?): Boolean =
    metros != null && radioMetros != null && metros <= radioMetros
