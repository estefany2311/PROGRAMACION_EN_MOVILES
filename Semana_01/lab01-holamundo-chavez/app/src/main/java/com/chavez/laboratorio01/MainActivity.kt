package com.chavez.laboratorio01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chavez.laboratorio01.ui.theme.Laboratorio01Theme
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio01Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier=modifier) {
        Text(text = "Hello, soy $name!", fontSize =24.sp)
        Text(text="Curso: Programacion en Moviles")

        Button(onClick = {}){
            Text(text = "Presióname")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Laboratorio01Theme {
        Greeting("Karla")
    }
}