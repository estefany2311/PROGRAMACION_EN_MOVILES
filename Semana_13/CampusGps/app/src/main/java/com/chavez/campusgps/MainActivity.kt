package com.chavez.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chavez.campusgps.data.FavoritosRepositoryDataStore
import com.chavez.campusgps.data.LugaresRepositoryLocal
import com.chavez.campusgps.data.UbicacionRepositoryGoogle
import com.chavez.campusgps.ui.PantallaMapa
import com.chavez.campusgps.ui.theme.CampusGPSTheme
import com.chavez.campusgps.viewmodel.CampusViewModelFactory
import com.chavez.campusgps.viewmodel.MapaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val factory = CampusViewModelFactory(
            ubicacionRepo = UbicacionRepositoryGoogle(applicationContext),
            lugaresRepo = LugaresRepositoryLocal(),
            favoritosRepo = FavoritosRepositoryDataStore(applicationContext)
        )

        setContent {
            CampusGPSTheme {
                val vm: MapaViewModel = viewModel(factory = factory)
                PantallaMapa(vm)
            }
        }
    }
}
