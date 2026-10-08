package com.chavezlazo.tecsupstore.screens

import androidx.compose.foundation.layout.Column
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentRoute by remember { mutableStateOf("inicio") }

    // Estado elevado: ids de los productos marcados como favoritos
    val favoritos = remember { mutableStateListOf<Int>() }
    val snackbarHostState = remember { SnackbarHostState() }

    // Agrega o quita un favorito y muestra un mensaje
    val alternarFavorito: (Int) -> Unit = { id ->
        val agregado = id !in favoritos
        if (agregado) favoritos.add(id) else favoritos.remove(id)
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                if (agregado) "Agregado a favoritos" else "Quitado de favoritos"
            )
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { nuevaRuta ->
                    currentRoute = nuevaRuta
                    scope.launch { drawerState.close() }
                },
                cantidadFavoritos = favoritos.size
            )
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "TECSUP Store",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Más vendidos",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú de navegación"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                when (currentRoute) {
                    "inicio" -> HomeScreen(
                        favoritos = favoritos,
                        onToggleFavorito = alternarFavorito
                    )
                    else -> HomeScreen(
                        favoritos = favoritos,
                        onToggleFavorito = alternarFavorito
                    )
                }
            }
        }
    }
}