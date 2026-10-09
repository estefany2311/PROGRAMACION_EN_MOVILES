package com.saludplus.citas.navigation

object Rutas {
    // Rutas sin parámetros
    const val SPLASH = "splash"
    const val REGISTRO = "registro"
    const val LOGIN = "login"
    const val TERMINOS = "terminos"
    const val HOME = "home"
    const val ESPECIALIDADES = "especialidades"
    const val MIS_CITAS = "misCitas"
    const val PERFIL = "perfil"
    const val RESULTADOS = "resultados"
    const val NOTIFICACIONES = "notificaciones"

    // Rutas con parámetros (patrón que se registra en el NavHost)
    const val MEDICOS = "medicos/{especialidadId}"
    const val FECHA_HORA = "fechaHora/{medicoId}"
    const val CONFIRMAR_CITA = "confirmarCita/{medicoId}/{fecha}/{hora}"
    const val CITA_EXITOSA = "citaExitosa/{citaId}"
    const val DETALLE_CITA = "detalleCita/{citaId}"

    // Funciones para armar la ruta real al navegar
    fun medicos(especialidadId: Int) = "medicos/$especialidadId"
    fun fechaHora(medicoId: Int) = "fechaHora/$medicoId"
    fun confirmarCita(medicoId: Int, fecha: String, hora: String) = "confirmarCita/$medicoId/$fecha/$hora"
    fun citaExitosa(citaId: Int) = "citaExitosa/$citaId"
    fun detalleCita(citaId: Int) = "detalleCita/$citaId"
}
