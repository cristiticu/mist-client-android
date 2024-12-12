package com.example.mistclient.games.ui.details

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
data class StoreGameDetailsUiState(
    val game: Game? = null,
    val loading: Boolean = false,
    val error: Boolean? = null,
)

class StoreGameDetailsViewModel(private val storeGamesRepository: StoreGamesRepository) :
    ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MistClient)
                StoreGameDetailsViewModel(
                    app.applicationContext.storeGamesRepository,
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(StoreGameDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d("StoreGameDetailsViewModel", "init")
    }

    fun getGame(guid: String) {
        viewModelScope.launch {
            storeGamesRepository.getGame((guid)).collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.update { state ->
                            state.copy(game = it.data, loading = false, error = false)
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