package com.chavez.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chavez.murocampus.data.PostRepositoryRemote
import com.chavez.murocampus.data.remote.RetrofitClient
import com.chavez.murocampus.ui.PantallaDetalle
import com.chavez.murocampus.ui.PantallaLista
import com.chavez.murocampus.ui.theme.MuroCampusTheme
import com.chavez.murocampus.viewmodel.PostsViewModel
import com.chavez.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {

    // Aquí se arma la cadena: RetrofitClient -> PostRepositoryRemote -> PostsViewModel.
    // by viewModels conserva el ViewModel al rotar la pantalla.
    private val vm: PostsViewModel by viewModels {
        PostsViewModelFactory(PostRepositoryRemote(RetrofitClient.api))
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MuroCampusTheme {
                // Navegación simple con estado: null = lista; un id = detalle de esa publicación.
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }

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
                    val contenido = Modifier.padding(innerPadding)
                    val seleccionado = idSeleccionado
                    if (seleccionado == null) {
                        PantallaLista(
                            vm = vm,
                            onAbrir = { id ->
                                idSeleccionado = id
                                vm.abrir(id)
                            },
                            modifier = contenido
                        )
                    } else {
                        // Atrás del sistema: vuelve a la lista (que sigue en memoria, sin recargar).
                        BackHandler { idSeleccionado = null }
                        PantallaDetalle(vm = vm, id = seleccionado, modifier = contenido)
                    }
                }
            }
        }
    }
}
