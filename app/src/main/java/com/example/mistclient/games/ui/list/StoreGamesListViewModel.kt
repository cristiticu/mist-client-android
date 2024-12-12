package com.example.mistclient.games.ui.list

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mistclient.MistClient
import com.example.mistclient.api.Result
import com.example.mistclient.games.Game
import com.example.mistclient.games.data.StoreGamesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
data class StoreGamesListUiState(
    val games: List<Game> = listOf(),
    val loading: Boolean = false,
    val error: Boolean? = null,
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
        getGames()
    }

    fun getGames() {
        viewModelScope.launch {
            storeGamesRepository.getGames(null, null)
                .collect {
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
}