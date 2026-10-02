package com.chavezlazo.tecsupstore.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.chavezlazo.tecsupstore.components.AppDrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    // Estado del Drawer (abierto o cerrado)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentRoute by remember { mutableStateOf("inicio") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { nuevaRuta ->
                    currentRoute = nuevaRuta
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TECSUP Store") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú de navegación"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            // Contenido de la pantalla seleccionada
            Surface(modifier = Modifier.padding(innerPadding)) {
                when (currentRoute) {
                    "inicio" -> HomeScreen()
                    else -> HomeScreen() // HomeScreen
                }
            }
        }
    }
}