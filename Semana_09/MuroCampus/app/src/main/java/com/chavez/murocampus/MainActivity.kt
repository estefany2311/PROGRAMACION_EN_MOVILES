package com.chavez.murocampus

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.chavez.murocampus.data.remote.RetrofitClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import com.chavez.murocampus.ui.theme.MuroCampusTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // PRUEBA TEMPORAL (Paso 4): se borra antes del commit
        lifecycleScope.launch {
            // TODO 3: listar y mostrar cuántas llegaron
            try {
                val posts = RetrofitClient.api.listar()
                Log.d("MuroCampus", "Llegaron ${posts.size} publicaciones")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("MuroCampus", "Fallo la prueba de red", e)
            }
        }

        setContent {
            MuroCampusTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("MuroCampus") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { innerPadding ->
                    // Contenido vacío por ahora: las pantallas llegan en los pasos 6 a 9.
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}
