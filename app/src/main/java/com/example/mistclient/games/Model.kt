package com.example.mistclient.games

import java.time.LocalDate

data class Game(val id: String,
                val title: String,
                val description: String,
                val price: Double,
                val addedAt: LocalDate,
                val positiveReviews: Int,
                val negativeReviews: Int,
                val imageSrc: String? = null
)
