package com.example.mistclient.games.data

import android.util.Log
import com.example.mistclient.api.Result
import com.example.mistclient.games.Game
import com.example.mistclient.games.data.remote.GamesWebSocketClient
import com.example.mistclient.games.data.remote.GamesWebSocketListener
import com.example.mistclient.games.data.remote.RemoteStoreGamesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class StoreGamesRepository(
    private val remoteStoreGamesDataSource: RemoteStoreGamesDataSource,
    private val webSocketClient: GamesWebSocketClient
) {
    private var cachedGamesPage = mutableListOf<Game>()
    private var cachedGames = mutableSetOf<Game>()

    private val _gamesPageFlow =
        MutableSharedFlow<Result<List<Game>>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val gamesPageFlow: SharedFlow<Result<List<Game>>> = _gamesPageFlow.asSharedFlow()

    init {
        Log.d("StoreGamesRepository", "init")
    }

    fun connectWebSocket(webSocketUrl: String, onOpen: () -> Unit, onClosed: () -> Unit) {
        Log.d("StoreGamesRepository", "connect websocket")
        webSocketClient.connect(
            webSocketUrl,
            GamesWebSocketListener(onOpen = onOpen, onClosed = onClosed) { newGame ->
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("StoreGamesRepository", "adding new game $newGame")
                    cachedGamesPage.add(newGame)
                    _gamesPageFlow.emit(Result.Success(cachedGamesPage.toList()))
                }
            })
    }

    fun disconnectWebSocket() {
        Log.d("StoreGamesRepository", "disconnect websocket")
        webSocketClient.disconnect()
    }

    fun getGames(limit: Int?, offset: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            _gamesPageFlow.emit(Result.Loading)
            if (cachedGamesPage.size > 0) {
                Log.d("StoreGamesRepository", "getAllGames emit cached items")
                _gamesPageFlow.emit(Result.Success(cachedGamesPage.toList()))
            } else {
                when (val result = remoteStoreGamesDataSource.fetchGames(limit, offset)) {
                    is Result.Success -> {
                        Log.d("StoreGamesRepository", "getAllGames emit fetched items")
                        cachedGamesPage.clear()
                        cachedGamesPage.addAll(result.data)
                        _gamesPageFlow.emit(Result.Success(cachedGamesPage.toList()))
                    }

                    is Result.Error -> {
                        Log.w("StoreGamesRepository", "getAllGames emit error", result.exception)
                        _gamesPageFlow.emit(Result.Error())
                    }

                    is Result.Loading -> {
                        _gamesPageFlow.emit(Result.Loading)
                    }
                }
            }
        }
    }

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