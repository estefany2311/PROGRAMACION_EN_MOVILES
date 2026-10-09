package com.saludplus.citas.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.saludplus.citas.data.model.Cita
import com.saludplus.citas.data.model.Especialidad
import com.saludplus.citas.data.model.Medico
import com.saludplus.citas.data.model.Usuario

// Un solo object compartido por toda la app: los datos viven en memoria y se pierden al cerrar la app.
object Repositorio {

    // ---------- COLECCIONES  ----------
    val usuarios = mutableStateListOf<Usuario>()
    var usuarioActual by mutableStateOf<Usuario?>(null)
    var localSeleccionado by mutableStateOf("")

    val especialidades = listOf(
        Especialidad(1, "Medicina General", "Atención integral", Icons.Default.MedicalServices),
        Especialidad(2, "Pediatría", "Niños y adolescentes", Icons.Default.ChildCare),
        Especialidad(3, "Ginecología", "Salud de la mujer", Icons.Default.Female),
        Especialidad(4, "Cardiología", "Corazón y vasos sanguíneos", Icons.Default.Favorite),
        Especialidad(5, "Dermatología", "Piel, cabello y uñas", Icons.Default.Spa),
        Especialidad(6, "Traumatología", "Huesos y articulaciones", Icons.Default.Accessibility),
        Especialidad(7, "Oftalmología", "Salud visual", Icons.Default.Visibility)
    )

    val medicos = listOf(
        Medico(1, "Dra. Ana Torres", 3, 4.9, 120, "12345", "Disponible hoy"),
        Medico(2, "Dra. Claudia Rojas", 3, 4.8, 95, "12346", "Disponible hoy"),
        Medico(3, "Dr. Luis Ramírez", 3, 4.7, 88, "12347", "Disponible hoy"),
        Medico(4, "Dra. Mariana Soto", 3, 4.6, 76, "12348", "Disponible esta semana"),
        Medico(5, "Dr. Carlos Mendoza", 1, 4.5, 60, "22001", "Disponible hoy"),
        Medico(6, "Dra. Lucía Paredes", 1, 4.8, 110, "22002", "Disponible esta semana"),
        Medico(7, "Dr. Jorge Salas", 2, 4.7, 90, "23001", "Disponible hoy"),
        Medico(8, "Dra. Elena Campos", 2, 4.9, 130, "23002", "Disponible esta semana"),
        Medico(9, "Dr. Ricardo Vega", 4, 4.8, 101, "24001", "Disponible hoy"),
        Medico(10, "Dra. Patricia Núñez", 4, 4.6, 70, "24002", "Disponible esta semana"),
        Medico(11, "Dra. Rosa Díaz", 5, 4.8, 85, "25001", "Disponible hoy"),
        Medico(12, "Dr. Marco León", 5, 4.5, 52, "25002", "Disponible esta semana"),
        Medico(13, "Dr. Andrés Quispe", 6, 4.7, 64, "26001", "Disponible hoy"),
        Medico(14, "Dra. Sofía Herrera", 7, 4.9, 99, "27001", "Disponible esta semana")
    )

    // Horarios que atiende cada médico en un día
    val horariosBase = listOf(
        "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00"
    )

    val citas = mutableStateListOf<Cita>()
    private var siguienteIdCita = 1   // úsalo para asignar el id de cada cita nueva

    //  FUNCIONES

    // Registro. Si ya existe un usuario con ese teléfono (any) devuelve false;
    //  si no, agrégalo a la lista (add) y devuelve true.
    fun registrarUsuario(usuario: Usuario): Boolean {
        if (usuarios.any { it.telefono == usuario.telefono }) {
            return false
        }
        return usuarios.add(usuario)
    }

    // Login. Busca en usuarios (find) uno con ese teléfono y esa contraseña.
    // Si existe, guárdalo en usuarioActual y devuelve true; si no, false.
    fun iniciarSesion(telefono: String, contrasena: String): Boolean {
        val telefonoLimpio = telefono.trim()
        val encontrado = usuarios.find {
            it.telefono == telefonoLimpio && it.contrasena == contrasena
        }

        if (encontrado != null) {
            usuarioActual = encontrado
            return true
        }
        return false
    }

    // Deja usuarioActual en null.
    fun cerrarSesion() {
        usuarioActual = null
    }

    // Especialidades cuyo nombre contenga el texto (filter + contains, sin importar mayúsculas).
    //         Si el texto está vacío, devuelve todas.
    fun buscarEspecialidades(texto: String): List<Especialidad> {
        val textoLimpio = texto.trim()
        if (textoLimpio.isBlank()) return especialidades
        return especialidades.filter { it.nombre.contains(textoLimpio, ignoreCase = true) }
    }

    // Las 3 primeras especialidades (take).
    fun especialidadesDestacadas(): List<Especialidad> {
        return especialidades.take(3)
    }

    // Busca por id (find). Devuelve null si no existe.
    fun obtenerEspecialidad(id: Int): Especialidad? {
        return especialidades.find { it.id == id }
    }

    // Busca por id (find).
    fun obtenerMedico(id: Int): Medico? {
        return medicos.find { it.id == id }
    }

    // Busca por id (find).
    fun obtenerCita(id: Int): Cita? {
        return citas.find { it.id == id }
    }

    // Médicos de esa especialidad, del mejor al peor calificado (filter + sortedByDescending).
    fun medicosPorEspecialidad(especialidadId: Int): List<Medico> {
        return medicos
            .filter { it.especialidadId == especialidadId }
            .sortedByDescending { it.calificacion }
    }

    // Igual que la anterior pero filtrando también por texto en el nombre del médico.
    fun buscarMedicos(especialidadId: Int, texto: String): List<Medico> {
        return medicos
            .filter { it.especialidadId == especialidadId && it.nombre.contains(texto, ignoreCase = true) }
            .sortedByDescending { it.calificacion }
    }

    // Horarios libres de ese médico en esa fecha:
    // obtén las horas ya reservadas en citas (filter + map) y quítalas de horariosBase (filter).
    fun horariosDisponibles(medicoId: Int, fecha: String): List<String> {
        val horasReservadas = citas
            .filter { it.medicoId == medicoId && it.fecha == fecha }
            .map { it.hora }

        return horariosBase.filter { it !in horasReservadas }
    }

    //  Crea y guarda una cita para usuarioActual.
    //  Si ese médico ya tiene una cita en esa fecha y hora (any) o no hay sesión, devuelve null.
    //  Si no, crea la Cita con siguienteIdCita, agrégala (add), incrementa el contador y devuélvela.
    fun agendarCita(medicoId: Int, fecha: String, hora: String, motivo: String): Cita? {
        val usuario = usuarioActual ?: return null

        val estaOcupado = citas.any {
            it.medicoId == medicoId && it.fecha == fecha && it.hora == hora
        }
        if (estaOcupado) return null

        val nuevaCita = Cita(
            id = siguienteIdCita,
            telefonoUsuario = usuario.telefono,
            medicoId = medicoId,
            fecha = fecha,
            hora = hora,
            motivo = motivo
        )

        citas.add(nuevaCita)
        siguienteIdCita++
        return nuevaCita
    }

    // Citas del usuarioActual, ordenadas por fecha y luego por hora (filter + sortedWith).
    fun citasDelUsuario(): List<Cita> {
        val usuario = usuarioActual ?: return emptyList()

        return citas
            .filter { it.telefonoUsuario == usuario.telefono }
            .sortedWith(compareBy({ it.fecha }, { it.hora }))
    }

    //  (reto extra): Elimina la cita con ese id (removeIf). Devuelve true si se eliminó.
    fun cancelarCita(citaId: Int): Boolean {
        return citas.removeIf { it.id == citaId }
    }
}