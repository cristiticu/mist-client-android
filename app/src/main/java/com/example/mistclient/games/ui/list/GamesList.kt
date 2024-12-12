package com.example.mistclient.games.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.mistclient.games.Game
import java.time.LocalDate

@Composable
fun GamesList(games: List<Game>, onGameClick: (id: String) -> Unit, modifier: Modifier? = null) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = (modifier ?: Modifier).fillMaxSize()
    ) {
        items(items = games, key = { it.id }) { game ->
            GameCard(game = game, onGameClick = onGameClick)
        }
    }
}

@Composable
fun GameCard(game: Game, onGameClick: (id: String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onGameClick(game.id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = game.imageSrc,
                contentDescription = "Game Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = game.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
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