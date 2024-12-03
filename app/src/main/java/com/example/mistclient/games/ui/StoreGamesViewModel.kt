package com.example.mistclient.games.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mistclient.games.Game
import java.time.LocalDate

data class StoreGamesUiState(val games: List<Game>)

class StoreGamesViewModel : ViewModel() {
    init {
        Log.d("GamesViewModel", "init")
    }

    val uiState = mutableStateOf(
        StoreGamesUiState(
            games = listOf(
                Game(
                    "1",
                    "Title",
                    "Description",
                    49.99,
                    LocalDate.now(),
                    100,
                    50,
                    "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/211420/header.jpg"
                ),
                Game(
                    "2",
                    "Title 2",
                    "Description 2",
                    49.99,
                    LocalDate.now(),
                    100,
                    50,
                    "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/211420/header.jpg"
                )
            )
        )
    )

}