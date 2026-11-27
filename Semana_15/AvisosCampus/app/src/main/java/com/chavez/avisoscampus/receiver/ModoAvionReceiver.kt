package com.chavez.avisoscampus.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

// onReceive debe ser corto: solo lee el estado y lo pasa a quien lo registró.
class ModoAvionReceiver(private val alCambiar: (Boolean) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            alCambiar(intent.getBooleanExtra("state", false))
        }
    }
}
