package com.tecsup.huellitasvet.data.local

import com.tecsup.huellitasvet.data.local.entity.Servicio

// Datos de prueba ficticios (Ley 29733). Se cargan solo la primera vez
object DatosIniciales {

    suspend fun sembrar(db: VetDatabase) {
        // TODO (Paso 3): usar usuarioDao().contar() == 0 y sembrar usuarios, mascotas y citas
        if (db.servicioDao().contar() > 0) return
        db.servicioDao().insertarTodos(servicios())
    }

    private fun servicios() = listOf(
        Servicio(1, "Consulta general", "Control de rutina y revisión completa", 30, 60.0, true, "heart"),
        Servicio(2, "Vacunación", "Aplicación de vacunas según calendario", 30, 45.0, true, "syringe"),
        Servicio(3, "Baño y corte", "Higiene, baño medicado y corte de pelo", 60, 50.0, true, "scissors"),
        Servicio(4, "Desparasitación", "Tratamiento antiparasitario interno y externo", 15, 25.0, false, "heart")
    )
}
