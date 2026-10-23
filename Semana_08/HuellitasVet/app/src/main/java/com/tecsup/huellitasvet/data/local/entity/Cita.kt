package com.tecsup.huellitasvet.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tecsup.huellitasvet.model.EstadoCita

// Tabla citas: N citas de 1 mascota y N citas de 1 servicio
@Entity(
    tableName = "citas",
    foreignKeys = [
        ForeignKey(
            entity = Mascota::class,
            parentColumns = ["id"],
            childColumns = ["mascotaId"]
        ),
        ForeignKey(
            entity = Servicio::class,
            parentColumns = ["id"],
            childColumns = ["servicioId"]
        )
    ],
    indices = [
        Index("mascotaId"),
        Index("servicioId"),
        Index(value = ["fecha", "hora"])     // acelera la consulta de horarios ocupados
    ]
)
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mascotaId: Int,
    val servicioId: Int,
    val fecha: String,                  // 'aaaa-MM-dd'
    val hora: String,                   // 'HH:mm'
    val nota: String = "",
    val estado: String = EstadoCita.PENDIENTE.name,
    val precio: Double,                 // foto del precio del servicio al crear la cita
    val diagnostico: String? = null,
    val tratamiento: String? = null,
    val pesoRegistrado: Double? = null,
    val fechaCreacion: Long = System.currentTimeMillis()
)
