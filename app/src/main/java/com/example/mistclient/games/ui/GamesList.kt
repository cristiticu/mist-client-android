package com.example.mistclient.games.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.mistclient.games.Game
import java.time.LocalDate

@Composable
fun GamesList(games: List<Game>, onGameClick: (id: String) -> Unit) {
    Log.d("GamesList", "Recompose")
    LazyColumn {
        items(items = games, key = { it.id }) {
            Row(Modifier.clickable { onGameClick(it.id) }) {
                AsyncImage(
                    model = it.imageSrc,
                    contentDescription = "Game Image"
                )
                Text(text = AnnotatedString(it.title))
            }

        }
    }
}

@Preview
@Composable
fun GamesListPreview() {
    val game1 = Game(
        "1",
        "Title",
        "Description",
        49.99,
        LocalDate.now(),
        100,
        50,
        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/211420/header.jpg"
    )
    val game2 = Game(
        "2",
        "Title 2",
        "Description 2",
        49.99,
        LocalDate.now(),
        100,
        50,
        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/211420/header.jpg"
    )

    GamesList(games = listOf(game1, game2), onGameClick = {})
}