package com.chavez.campusgps.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.chavez.campusgps.model.Lugar

private const val PAQUETE_GOOGLE_MAPS = "com.google.android.apps.maps"

// Abre la ruta a pie hacia el lugar. Se arma en la View porque necesita un Context.
// 1) Google Maps (navegación a pie)  2) cualquier app/navegador con una URL  3) aviso, sin cerrar la app
fun abrirRutaAPie(contexto: Context, lugar: Lugar) {
    val destino = "${lugar.latitud},${lugar.longitud}"

    val intentMaps = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$destino&mode=w"))
        .setPackage(PAQUETE_GOOGLE_MAPS)

    val intentWeb = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$destino&travelmode=walking")
    )

    try {
        contexto.startActivity(intentMaps)
    } catch (e: ActivityNotFoundException) {
        try {
            contexto.startActivity(intentWeb)
        } catch (e2: ActivityNotFoundException) {
            Toast.makeText(contexto, "No hay una app para abrir la ruta", Toast.LENGTH_SHORT).show()
        }
    }
}
