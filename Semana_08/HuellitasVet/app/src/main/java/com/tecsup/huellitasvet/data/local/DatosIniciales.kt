package com.tecsup.huellitasvet.data.local

import androidx.room.withTransaction
import com.tecsup.huellitasvet.data.local.entity.Cita
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.entity.Servicio
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.Rol
import com.tecsup.huellitasvet.model.Sexo
import java.time.LocalDate

// Datos de prueba ficticios (Ley 29733). Se cargan solo la primera vez
object DatosIniciales {

    suspend fun sembrar(db: VetDatabase) {
        if (db.usuarioDao().contar() > 0) return
        db.withTransaction {
            db.servicioDao().insertarTodos(servicios())
            db.usuarioDao().insertarTodos(usuarios())
            db.mascotaDao().insertarTodas(mascotas())
            db.citaDao().insertarTodas(citas())
        }
    }

    private fun servicios() = listOf(
        Servicio(1, "Consulta general", "Control de rutina y revisión completa", 30, 60.0, true, "heart"),
        Servicio(2, "Vacunación", "Aplicación de vacunas según calendario", 30, 45.0, true, "syringe"),
        Servicio(3, "Baño y corte", "Higiene, baño medicado y corte de pelo", 60, 50.0, true, "scissors"),
        Servicio(4, "Desparasitación", "Tratamiento antiparasitario interno y externo", 15, 25.0, false, "heart")
    )

    private fun usuarios() = listOf(
        Usuario(1, "Karla Chavez Lazo", "karla@correo.test", "1234", Rol.CLIENTE.name),
        Usuario(2, "Luis Ramírez", "luis@correo.test", "1234", Rol.CLIENTE.name),
        Usuario(3, "Dra. Valeria Rojas", "vet@huellitas.test", "admin", Rol.ADMIN.name)
    )

    private fun mascotas(): List<Mascota> {
        val anio = LocalDate.now().year
        return listOf(
            Mascota(1, 1, "Luna", Especie.GATO.name, "Siamesa", anio - 3, 4.3, Sexo.HEMBRA.name),
            Mascota(2, 1, "Max", Especie.PERRO.name, "Labrador", anio - 4, 24.5, Sexo.MACHO.name),
            Mascota(3, 2, "Rocky", Especie.PERRO.name, "Bulldog", anio - 6, 12.0, Sexo.MACHO.name),
            Mascota(4, 2, "Michi", Especie.GATO.name, "Mestizo", anio - 2, 3.8, Sexo.HEMBRA.name)
        )
    }

    // Fechas relativas a hoy para que siempre haya citas pasadas, de hoy y futuras
    private fun dia(n: Long) = LocalDate.now().plusDays(n).toString()

    private val precios = mapOf(1 to 60.0, 2 to 45.0, 3 to 50.0, 4 to 25.0)

    private fun cita(
        mascotaId: Int, servicioId: Int, dias: Long, hora: String, estado: EstadoCita,
        nota: String = "", diagnostico: String? = null, tratamiento: String? = null, peso: Double? = null
    ) = Cita(
        mascotaId = mascotaId,
        servicioId = servicioId,
        fecha = dia(dias),
        hora = hora,
        nota = nota,
        estado = estado.name,
        precio = precios.getValue(servicioId),
        diagnostico = diagnostico,
        tratamiento = tratamiento,
        pesoRegistrado = peso
    )

    private fun citas() = listOf(
        // Pasadas ATENDIDAS (historial clinico)
        cita(1, 1, -60, "10:00", EstadoCita.ATENDIDA, "Control anual",
            "Control de rutina. Sin hallazgos.", "Vitaminas por 15 días", 4.0),
        cita(1, 2, -25, "09:30", EstadoCita.ATENDIDA, "Vacuna anual",
            "Vacuna triple felina aplicada. Buen estado general.", "Refuerzo en 12 meses", 4.2),
        cita(1, 3, -8, "11:00", EstadoCita.ATENDIDA, "",
            "Higiene general. Pelaje y piel sanos.", "Cepillado semanal", 4.3),
        cita(2, 1, -15, "15:00", EstadoCita.ATENDIDA, "Se rasca la oreja",
            "Otitis leve en el oído derecho.", "Gotas óticas cada 12 h por 7 días", 24.5),
        cita(3, 2, -5, "10:30", EstadoCita.ATENDIDA, "",
            "Vacuna antirrábica aplicada.", "Observar 24 horas", 12.0),
        cita(4, 1, -3, "16:00", EstadoCita.CANCELADA, "No pudo asistir"),
        // Hoy
        cita(2, 3, 0, "09:00", EstadoCita.ATENDIDA, "",
            "Baño y corte completo. Sin pulgas.", "Ninguno", 24.5),
        cita(1, 2, 0, "10:30", EstadoCita.CONFIRMADA, "Refuerzo de vacuna"),
        cita(3, 1, 0, "11:00", EstadoCita.PENDIENTE, "Revisión de la piel"),
        cita(4, 1, 0, "12:30", EstadoCita.PENDIENTE, "Control de peso"),
        // Proximos dias
        cita(3, 3, 1, "15:00", EstadoCita.PENDIENTE),
        cita(2, 1, 2, "16:00", EstadoCita.PENDIENTE, "Control de la otitis"),
        cita(2, 3, 3, "09:30", EstadoCita.PENDIENTE),
        cita(1, 1, 5, "11:30", EstadoCita.CONFIRMADA, "Chequeo general")
    )
}
