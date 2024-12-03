package com.example.mistclient.games.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.mistclient.games.Game
import java.time.LocalDate

@Composable
fun GameDetails(game: Game) {
    Log.d("Game Details", "recompose")
    Column {
        AsyncImage(
            model = game.imageSrc,
            contentDescription = "Game Image"
        )
        Text(game.title)
        Text(game.description)
        Text("Price: ${game.price.toString()}")
        Text("Added at: ${game.addedAt.toString()}")
        Text("Positive reviews: ${game.positiveReviews}")
        Text("Negative reviews: ${game.negativeReviews}")
    }
}

@Preview
@Composable
fun GameDetailsPreview() {
    GameDetails(Game(
        "1",
        "Title",
        "Description",
        49.99,
        LocalDate.now(),
        100,
        50,
        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/211420/header.jpg")
    )
}