package com.example.mistclient.games

import java.time.LocalDate

data class Game(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val addedAt: LocalDate,
    val positiveReviews: Int,
    val negativeReviews: Int,
    val imageSrc: String? = null
)

data class GameApiResponse(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val added_at: String,
    val positive_reviews: Int,
    val negative_reviews: Int,
    val image_src: String? = null
)

fun GameApiResponse.toGame(): Game {
    return Game(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        addedAt = LocalDate.parse(this.added_at), // Adjust the date parsing format if needed
        positiveReviews = this.positive_reviews,
        negativeReviews = this.negative_reviews,
        imageSrc = this.image_src
    )
}

