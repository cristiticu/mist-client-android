package com.example.mistclient.games.ui.list

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mistclient.MistClient
import com.example.mistclient.api.Api
import com.example.mistclient.api.Result
import com.example.mistclient.api.ubbIP
import com.example.mistclient.games.Game
import com.example.mistclient.games.data.StoreGamesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val gamesWebsocketNotificationsUrl = "ws://$ubbIP:8000/ws/notification/game"

@Stable
data class StoreGamesListUiState(
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    var games: List<Game> = emptyList(),
    val loading: Boolean = false,
    val error: Boolean? = null,
    val isWebsocketConnected: Boolean = false,
    val currentOffset: Int = 0,
    val currentLimit: Int = 5,
)

class StoreGamesListViewModel(private val storeGamesRepository: StoreGamesRepository) :
    ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MistClient)
                StoreGamesListViewModel(
                    app.applicationContext.storeGamesRepository,
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(StoreGamesListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d("StoreGamesViewModel", "init")
        connectWebsocket()
        observeGames()
        getGames()
    }

    private fun connectWebsocket() {
        storeGamesRepository.connectWebSocket(
            "$gamesWebsocketNotificationsUrl?token=${Api.tokenInterceptor.token}",
            onOpen = { onWebsocketOpen() },
            onClosed = { onWebsocketClose() }
        )
    }

    private fun onWebsocketOpen() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isWebsocketConnected = true)
            }
        }
    }

    private fun onWebsocketClose() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isWebsocketConnected = false)
            }
            
            val result = _uiState.value.snackbarHostState.showSnackbar(
                message =
                "Connection to the server was lost!",
                actionLabel = "reconnect",
                duration = SnackbarDuration.Indefinite

            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    Log.d("Snackbar", "Action Performed")
                    connectWebsocket()
                }

                else -> {
                    Log.d("Snackbar", "Snackbar dismissed")
                }
            }
        }
    }

    private fun observeGames() {
        viewModelScope.launch {
            storeGamesRepository.gamesPageFlow.collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.update { state ->
                            state.copy(games = it.data, loading = false, error = false)
                        }
                    }

                    is Result.Error -> {
                        _uiState.update { state ->
                            state.copy(loading = false, error = true)
                        }
                    }

                    Result.Loading -> {
                        _uiState.update { state ->
                            state.copy(loading = true, error = null)
                        }
                    }
                }
            }
        }
    }

    fun getGames() {
        viewModelScope.launch {
            storeGamesRepository.getGames(null, null)
        }
    }

    override fun onCleared() {
        storeGamesRepository.disconnectWebSocket()
        super.onCleared()
    }
}