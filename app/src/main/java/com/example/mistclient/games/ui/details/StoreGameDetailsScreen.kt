package com.example.mistclient.games.ui.details

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreGameDetailsScreen(
    guid: String,
    viewModel: StoreGameDetailsViewModel =
        viewModel<StoreGameDetailsViewModel>(factory = StoreGameDetailsViewModel.Factory)
) {
    Log.d("StoreGameDetailsScreen", "recompose")

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(guid) {
        viewModel.getGame(guid)
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Details") }) },

        ) { padding ->

        when {
            uiState.loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error == true -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Something went wrong. Please try again.")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.getGame(guid) }) {
                            Text("Retry")
                        }
                    }
                }
            }

            uiState.game != null -> {
                GameDetails(game = uiState.game!!, modifier = Modifier.padding(padding))
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No game details available.")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStoreGameDetailsScreen() {
    StoreGameDetailsScreen("1")
}