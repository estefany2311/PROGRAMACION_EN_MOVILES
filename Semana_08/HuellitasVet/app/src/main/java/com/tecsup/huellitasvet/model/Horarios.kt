package com.tecsup.huellitasvet.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

// Horarios de atencion de la clinica: de 09:00 a 17:30 cada 30 minutos (18 horarios)
object Horarios {
    val INICIO: LocalTime = LocalTime.of(9, 0)
    const val INTERVALO_MIN = 30L
    const val CANTIDAD = 18

    val todos: List<String> = (0 until CANTIDAD).map {
        INICIO.plusMinutes(it * INTERVALO_MIN).toString()   // "09:00", "09:30", ... "17:30"
    }

    val locale: Locale = Locale.forLanguageTag("es-PE")

    fun fechaHora(fecha: String, hora: String): LocalDateTime =
        LocalDateTime.of(LocalDate.parse(fecha), LocalTime.parse(hora))

    // "2026-10-21" -> "Mié 21 oct"
    fun fechaCorta(fecha: String): String {
        val d = LocalDate.parse(fecha)
        val dia = d.format(DateTimeFormatter.ofPattern("EEE", locale)).replace(".", "")
            .replaceFirstChar { it.uppercase() }
        val mes = d.format(DateTimeFormatter.ofPattern("MMM", locale)).replace(".", "")
        return "$dia ${d.dayOfMonth} $mes"
    }

    // "2026-10-21" -> "21 oct"
    fun diaMes(fecha: String): String {
        val d = LocalDate.parse(fecha)
        val mes = d.format(DateTimeFormatter.ofPattern("MMM", locale)).replace(".", "")
        return "${d.dayOfMonth} $mes"
    }

    // Abreviatura del dia de la semana: "Lun", "Mar", ...
    fun diaSemana(fecha: LocalDate): String =
        fecha.format(DateTimeFormatter.ofPattern("EEE", locale)).replace(".", "")
            .replaceFirstChar { it.uppercase() }
}
