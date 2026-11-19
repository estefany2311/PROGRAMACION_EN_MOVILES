package com.chavez.campusgps.data

import com.chavez.campusgps.model.Posicion

// Se lanza (dentro de un Result.failure) cuando no se puede determinar la ubicación, p. ej. GPS apagado
class UbicacionNoDisponibleException : Exception("Activa el GPS e intenta de nuevo")

interface UbicacionRepository {
    fun playServicesDisponible(): Boolean
    suspend fun obtenerPosicion(): Result<Posicion>
}
