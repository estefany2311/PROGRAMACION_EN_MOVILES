package com.chavez.avisoscampus.data

import com.chavez.avisoscampus.model.Aviso
import com.chavez.avisoscampus.model.Origen
import com.chavez.avisoscampus.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface AvisosRepository {
    val avisos: StateFlow<List<Aviso>>
    fun registrar(
        titulo: String,
        texto: String,
        origen: Origen,
        prioridad: Prioridad = Prioridad.NORMAL,
        detalle: String? = null
    ): Aviso
}

class AvisosRepositoryMemoria : AvisosRepository {
    private val _avisos = MutableStateFlow<List<Aviso>>(emptyList())
    override val avisos: StateFlow<List<Aviso>> = _avisos.asStateFlow()
    private var siguiente = 1

    override fun registrar(
        titulo: String,
        texto: String,
        origen: Origen,
        prioridad: Prioridad,
        detalle: String?
    ): Aviso {
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val aviso = Aviso(siguiente++, titulo, texto, origen, hora, prioridad, detalle)
        _avisos.value = listOf(aviso) + _avisos.value // lo más reciente arriba
        return aviso
    }
}
