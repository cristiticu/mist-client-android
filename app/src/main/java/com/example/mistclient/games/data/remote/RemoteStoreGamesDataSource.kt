package com.example.mistclient.games.data.remote

import android.util.Log
import com.example.mistclient.api.Api
import com.example.mistclient.api.Result
import com.example.mistclient.games.Game
import com.example.mistclient.games.toGame


class RemoteStoreGamesDataSource {
    private val routes = Api.retrofit.create(StoreGamesServiceRoutes::class.java)

    suspend fun fetchGames(limit: Int?, offset: Int?): Result<List<Game>> {
        try {
            return Result.Success(
                routes.fetchGames(
                    limit?.toString(),
                    offset?.toString()
                ).map { it.toGame() }
            )
        } catch (e: Exception) {
            Log.w("RemoteStoreGamesDataSource", "fetchGames failed", e)
            return Result.Error(e)
        }
    }

    suspend fun fetchGame(guid: String): Result<Game> {
        try {
            return Result.Success(routes.fetchGame(guid).toGame())
        } catch (e: Exception) {
            Log.w("RemoteStoreGamesDataSource", "fetchGame $guid failed", e)
            return Result.Error(e)
        }
    }
}