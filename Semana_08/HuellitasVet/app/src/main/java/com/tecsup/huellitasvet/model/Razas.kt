package com.tecsup.huellitasvet.model

// Razas que el dueño puede elegir segun la especie de la mascota
object Razas {
    fun de(especie: Especie): List<String> = when (especie) {
        Especie.PERRO -> listOf("Labrador", "Bulldog", "Pastor alemán", "Poodle", "Shih tzu", "Schnauzer", "Mestizo")
        Especie.GATO -> listOf("Siamesa", "Persa", "Angora", "Bengalí", "Mestizo")
        Especie.OTRO -> listOf("Conejo", "Hámster", "Ave", "Otra")
    }
}
