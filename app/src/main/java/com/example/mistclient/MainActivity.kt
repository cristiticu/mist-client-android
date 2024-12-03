package com.example.mistclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Surface(Modifier.padding(8.dp, 64.dp)) {
            content()
        }
    }
}
