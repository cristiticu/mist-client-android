package com.example.mistclient.games.ui.list

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreGamesListScreen(
    onGameClick: (id: String) -> Unit,
    viewModel: StoreGamesListViewModel =
        viewModel<StoreGamesListViewModel>(factory = StoreGamesListViewModel.Factory)
) {
    Log.d("StoreGamesListScreen", "recompose")

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(uiState.snackbarHostState) },
        topBar = { TopAppBar(title = { Text(text = "Store") }) },

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
                        Button(onClick = { viewModel.getGames() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            uiState.games.isNotEmpty() -> {
                GamesList(
                    games = uiState.games,
                    onGameClick = onGameClick,
                    modifier = Modifier.padding(padding)
                )
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No games available.")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStoreGamesListScreen() {
    StoreGamesListScreen(onGameClick = {})
}
