package com.example.mistclient.games.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StoreGamesListScreen(onGameClick: (id: String) -> Unit) {
    Log.d("StoreGamesListScreen", "recompose")

    val storeGamesViewModel = viewModel<StoreGamesViewModel>()
    val storeGamesUiState by remember { storeGamesViewModel.uiState }

    GamesList(storeGamesUiState.games, onGameClick)
}

@Preview
@Composable
fun PreviewStoreGamesListScreen() {
    StoreGamesListScreen(onGameClick = {})
}
