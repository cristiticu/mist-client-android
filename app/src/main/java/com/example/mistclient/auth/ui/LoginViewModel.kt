package com.example.mistclient.auth.ui

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mistclient.MistClient
import com.example.mistclient.api.Result
import com.example.mistclient.auth.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
data class LoginUiState(
    val isAuthenticating: Boolean = false,
    val authenticationError: String? = null,
    val authenticationCompleted: Boolean = false,
    val token: String = ""
)

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MistClient)
                LoginViewModel(
                    app.applicationContext.authRepository,
                )
            }
        }
    }

    init {
        Log.d("LoginViewModel", "init")
    }

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            Log.v("LoginViewModel", "login...");

            _uiState.update { it.copy(isAuthenticating = true, authenticationError = null) }

            when (val result = authRepository.login(username, password)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isAuthenticating = false,
                            authenticationCompleted = true
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isAuthenticating = false,
                            authenticationError = result.exception?.message
                        )
                    }
                }

                Result.Loading -> {}
            }

        }
    }
}