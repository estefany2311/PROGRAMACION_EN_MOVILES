package com.tecsup.huellitasvet.data.repository

import com.tecsup.huellitasvet.data.local.entity.Servicio
import kotlinx.coroutines.flow.Flow

// El contrato: los ViewModels solo conocen esta interface.
// Mas adelante tendra otra implementacion (servicio web) sin cambiar ViewModels ni pantallas.
interface VetRepository {
    // TODO (Paso 4): Usuarios -> login(correo, clave): Usuario?

    // Servicios (ejemplo resuelto)
    fun observarServicios(): Flow<List<Servicio>>
    fun observarServiciosActivos(): Flow<List<Servicio>>
    suspend fun guardarServicio(s: Servicio)
    suspend fun eliminarServicio(s: Servicio)
    suspend fun cambiarActivo(id: Int, activo: Boolean)
    suspend fun servicioTieneCitas(id: Int): Boolean

    // TODO (Paso 4): Mascotas, Citas y Panel cuando existan las entidades Usuario, Mascota y Cita
}
