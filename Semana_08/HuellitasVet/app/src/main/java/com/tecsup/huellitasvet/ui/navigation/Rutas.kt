package com.tecsup.huellitasvet.ui.navigation

// Todas las rutas de la app en un solo lugar
object Rutas {
    const val SPLASH = "splash"
    const val LOGIN = "login"

    // Grafo del dueño
    const val GRAFO_DUENO = "grafo_dueno"
    const val INICIO = "inicio"
    const val MASCOTAS = "mascotas"
    const val FORM_MASCOTA = "mascota/form?id={id}"
    const val FICHA_MASCOTA = "mascota/{id}"
    const val PEDIR_CITA = "pedir_cita?mascotaId={mascotaId}"
    const val MIS_CITAS = "mis_citas"
    const val PERFIL = "perfil"

    // Grafo del veterinario
    const val GRAFO_VET = "grafo_vet"
    const val PANEL = "panel"
    const val AGENDA = "agenda"
    const val ATENDER_CITA = "atender/{citaId}"
    const val FICHA_PACIENTE = "paciente/{id}"
    const val PACIENTES = "pacientes"
    const val SERVICIOS = "servicios"
    const val FORM_SERVICIO = "servicio/form?id={id}"
    const val AJUSTES = "ajustes"

    fun formMascota(id: Int = 0) = "mascota/form?id=$id"
    fun fichaMascota(id: Int) = "mascota/$id"
    fun pedirCita(mascotaId: Int = 0) = "pedir_cita?mascotaId=$mascotaId"
    fun atenderCita(citaId: Int) = "atender/$citaId"
    fun fichaPaciente(id: Int) = "paciente/$id"
    fun formServicio(id: Int = 0) = "servicio/form?id=$id"
}
