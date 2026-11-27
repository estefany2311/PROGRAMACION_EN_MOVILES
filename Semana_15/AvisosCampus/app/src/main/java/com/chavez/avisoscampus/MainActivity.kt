package com.chavez.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chavez.avisoscampus.ui.PantallaAvisos
import com.chavez.avisoscampus.ui.theme.AvisosCampusTheme
import com.chavez.avisoscampus.viewmodel.AvisosViewModel
import com.chavez.avisoscampus.viewmodel.AvisosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvisosCampusTheme {
                val vm: AvisosViewModel = viewModel(factory = AvisosViewModelFactory())
                PantallaAvisos(vm)
            }
        }
    }
}
