package com.chavez.clinicasalud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.chavez.clinicasalud.navigation.AppNavigation
import com.chavez.clinicasalud.navigation.Screen
import com.chavez.clinicasalud.ui.theme.ClinicaSaludTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClinicaSaludTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            // Header del menú lateral (Avatar JP + Nombre + Rol)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFD0BCFF)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "KC",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF381E72),
                                        fontSize = 18.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = "Karla Chavez",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "Paciente",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(8.dp))

                            // Opciones del menú
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                                label = { Text("Inicio") },
                                selected = false,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                    }
                                }
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                                label = { Text("Mis citas") },
                                selected = true, // Seleccionado según la captura
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navController.navigate(Screen.MyAppointments.route)
                                }
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Default.ListAlt, contentDescription = null) },
                                label = { Text("Historial médico") },
                                selected = false,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Default.Person, contentDescription = null) },
                                label = { Text("Perfil") },
                                selected = false,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                    }
                ) {
                    AppNavigation(
                        navController = navController,
                        onOpenDrawer = {
                            scope.launch { drawerState.open() }
                        }
                    )
                }
            }
        }
    }
}