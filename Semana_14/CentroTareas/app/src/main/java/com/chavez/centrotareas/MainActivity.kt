package com.chavez.centrotareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chavez.centrotareas.data.TareasRepositoryLocal
import com.chavez.centrotareas.ui.PantallaTareas
import com.chavez.centrotareas.ui.theme.CentroTareasTheme
import com.chavez.centrotareas.viewmodel.TareasViewModel
import com.chavez.centrotareas.viewmodel.TareasViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CentroTareasTheme {
                val viewModel: TareasViewModel = viewModel(
                    factory = TareasViewModelFactory(TareasRepositoryLocal())
                )
                PantallaTareas(viewModel)
            }
        }
    }
}
