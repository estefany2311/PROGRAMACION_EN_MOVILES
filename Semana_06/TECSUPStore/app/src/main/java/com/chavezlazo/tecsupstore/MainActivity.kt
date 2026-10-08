package com.chavezlazo.tecsupstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chavezlazo.tecsupstore.screens.AppNavigation
import com.chavezlazo.tecsupstore.ui.theme.TecsupStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TecsupStoreTheme {
                AppNavigation() // llamamos a la funcion
            }
        }
    }
}