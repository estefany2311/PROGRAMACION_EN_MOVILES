package com.chavez.avisoscampus

import android.app.Application
import com.chavez.avisoscampus.notificacion.Notificador

class AvisosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanal(this)
    }
}
