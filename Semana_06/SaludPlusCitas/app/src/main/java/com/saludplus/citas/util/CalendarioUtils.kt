package com.saludplus.citas.util

import java.time.DayOfWeek
import java.time.LocalDate

private val DIAS = listOf(
    "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
)

private val DIAS_CORTOS = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

private val MESES = listOf(
    "enero", "febrero", "marzo", "abril", "mayo", "junio",
    "julio", "agosto", "setiembre", "octubre", "noviembre", "diciembre"
)

// Lunes a viernes
fun esDiaHabil(fecha: LocalDate): Boolean {
    return fecha.dayOfWeek != DayOfWeek.SATURDAY && fecha.dayOfWeek != DayOfWeek.SUNDAY
}

// Si la fecha cae en sábado o domingo, avanza hasta el lunes
private fun primerDiaHabilDesde(fecha: LocalDate): LocalDate {
    var dia = fecha
    while (!esDiaHabil(dia)) {
        dia = dia.plusDays(1)
    }
    return dia
}

// Devuelve 5 días hábiles consecutivos.
// semana = 0 -> los próximos 5 días hábiles a partir de hoy (nunca días pasados).
// semana = 1, 2, ... -> el mismo bloque desplazado 7 días por cada semana.
fun diasHabilesDeSemana(semana: Int, hoy: LocalDate = LocalDate.now()): List<LocalDate> {
    var dia = primerDiaHabilDesde(hoy.plusWeeks(semana.toLong()))
    val dias = mutableListOf<LocalDate>()
    while (dias.size < 5) {
        if (esDiaHabil(dia)) {
            dias.add(dia)
        }
        dia = dia.plusDays(1)
    }
    return dias
}

// "Octubre 2026"
fun tituloMes(fecha: LocalDate): String {
    val mes = MESES[fecha.monthValue - 1].replaceFirstChar { it.uppercase() }
    return "$mes ${fecha.year}"
}

// "Jue", "Vie", ...
fun nombreDiaCorto(fecha: LocalDate): String {
    return DIAS_CORTOS[fecha.dayOfWeek.value - 1]
}

// Recibe "2026-09-16" y devuelve "Martes 16 de setiembre 2026"
fun formatearFechaLarga(fechaIso: String): String {
    return try {
        val fecha = LocalDate.parse(fechaIso)
        val dia = DIAS[fecha.dayOfWeek.value - 1]
        val mes = MESES[fecha.monthValue - 1]
        "$dia ${fecha.dayOfMonth} de $mes ${fecha.year}"
    } catch (e: Exception) {
        fechaIso
    }
}