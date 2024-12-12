package com.example.mistclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.mistclient.theme.MistClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Log.d("MainActivity", "SetContent")
            MistClient {
                MistClientNavHost()
            }
        }
    }
}

@Composable
fun MistClient(content: @Composable () -> Unit) {
    MistClientTheme {
        content()
    }
}
