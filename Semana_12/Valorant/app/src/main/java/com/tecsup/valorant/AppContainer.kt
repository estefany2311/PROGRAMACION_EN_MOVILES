package com.tecsup.valorant

import android.app.Application
import android.content.Context
import com.tecsup.valorant.data.auth.AuthRepository
import com.tecsup.valorant.data.auth.FirebaseAuthRepository
import com.tecsup.valorant.data.local.AppDatabase
import com.tecsup.valorant.data.remote.RetrofitClient
import com.tecsup.valorant.data.repository.ItemRepository
import com.tecsup.valorant.data.repository.ItemRepositoryImpl
import com.tecsup.valorant.notification.Notificador
import com.tecsup.valorant.notification.NotificadorAndroid

// Crea el contenedor UNA sola vez para toda la app
class ValorantApp : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

// Crea la API, la base de datos y los repositorios
class AppContainer(context: Context) {
    private val db = AppDatabase.obtener(context)

    val itemRepository: ItemRepository = ItemRepositoryImpl(RetrofitClient.api, db.itemDao())

    val authRepository: AuthRepository = FirebaseAuthRepository()

    // TEMPORAL: en el paso 15 se reemplaza por NotificadorAndroid
    val notificador: Notificador = NotificadorAndroid(context.applicationContext)
}