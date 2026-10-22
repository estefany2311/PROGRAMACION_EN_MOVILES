package com.tecsup.huellitasvet.data.repository

import com.tecsup.huellitasvet.data.local.VetDatabase
import com.tecsup.huellitasvet.data.local.entity.Servicio
import kotlinx.coroutines.flow.Flow

// Implementacion del contrato con Room
class VetRepositoryRoom(private val db: VetDatabase) : VetRepository {

    private val servicioDao = db.servicioDao()

    // Servicios (ejemplo resuelto)
    override fun observarServicios(): Flow<List<Servicio>> = servicioDao.observarTodos()

    override fun observarServiciosActivos(): Flow<List<Servicio>> = servicioDao.observarActivos()

    override suspend fun guardarServicio(s: Servicio) {
        servicioDao.insertar(s)
    }

    override suspend fun eliminarServicio(s: Servicio) = servicioDao.eliminar(s)

    override suspend fun cambiarActivo(id: Int, activo: Boolean) = servicioDao.cambiarActivo(id, activo)

    override suspend fun servicioTieneCitas(id: Int): Boolean = TODO("Paso 4: usar el CitaDao")
}
