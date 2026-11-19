package com.chavez.campusgps.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.favoritosDataStore: DataStore<Preferences> by preferencesDataStore(name = "favoritos")

// Guarda los ids favoritos en DataStore Preferences (sobreviven al cerrar la app)
class FavoritosRepositoryDataStore(context: Context) : FavoritosRepository {

    private val store = context.applicationContext.favoritosDataStore
    private val clave = stringSetPreferencesKey("ids_favoritos")

    override val favoritos: Flow<Set<Int>> = store.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { prefs -> prefs[clave].orEmpty().mapNotNull { it.toIntOrNull() }.toSet() }

    override suspend fun alternar(id: Int) {
        store.edit { prefs ->
            val actuales = prefs[clave].orEmpty()
            val texto = id.toString()
            prefs[clave] = if (texto in actuales) actuales - texto else actuales + texto
        }
    }
}
