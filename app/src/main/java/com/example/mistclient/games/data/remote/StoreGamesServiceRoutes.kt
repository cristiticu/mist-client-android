package com.example.mistclient.games.data.remote

import com.example.mistclient.games.GameApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreGamesServiceRoutes {
    @GET("/game")
    suspend fun fetchGames(
        @Query("limit") limit: String?,
        @Query("offset") offset: String?
    ): List<GameApiResponse>

    @GET("/game/{guid}")
    suspend fun fetchGame(@Path("guid") guid: String): GameApiResponse
}