package com.example.mistclient.games.data

import android.util.Log
import com.example.mistclient.api.Result
import com.example.mistclient.games.Game
import com.example.mistclient.games.data.remote.RemoteStoreGamesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class StoreGamesRepository(private val remoteStoreGamesDataSource: RemoteStoreGamesDataSource) {
    private var cachedGamesPage = mutableListOf<Game>()
    private var cachedGames = mutableSetOf<Game>()

    init {
        Log.d("StoreGamesRepository", "init")
    }

    fun getGames(limit: Int?, offset: Int?): Flow<Result<List<Game>>> = flow {
        Log.d("StoreGamesRepository", "getAllGames emit loading")

        emit(Result.Loading)

        if (cachedGamesPage.size > 0) {
            Log.d("StoreGamesRepository", "getAllGames emit cached items")
            emit(Result.Success(cachedGamesPage.toList()))
        } else {
            when (val result = remoteStoreGamesDataSource.fetchGames(limit, offset)) {
                is Result.Success -> {
                    Log.d("StoreGamesRepository", "getAllGames emit fetched items")
                    cachedGamesPage.addAll(result.data)
                    emit(Result.Success(cachedGamesPage.toList()))
                }

                is Result.Error -> {
                    Log.w("StoreGamesRepository", "getAllGames emit error", result.exception)
                    emit(Result.Error())
                }

                is Result.Loading -> {
                    emit(Result.Loading)
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getGame(guid: String): Flow<Result<Game>> = flow {
        Log.d("StoreGamesRepository", "getGame emit loading")

        emit(Result.Loading)

        val cachedGame = cachedGames.find { it.id == guid }

        if (cachedGame != null) {
            Log.d("StoreGamesRepository", "getGame emit cached game")
            emit(Result.Success(cachedGame))
        } else {
            when (val result = remoteStoreGamesDataSource.fetchGame(guid)) {
                is Result.Success -> {
                    Log.d("StoreGamesRepository", "getGame emit fetched game")
                    cachedGames.add(result.data)

                    emit(Result.Success(result.data))
                }

                is Result.Error -> {
                    Log.w("StoreGamesRepository", "getGame emit error", result.exception)
                    emit(Result.Error())
                }

                is Result.Loading -> {
                    emit(Result.Loading)
                }
            }
        }
    }.flowOn(Dispatchers.IO)


}