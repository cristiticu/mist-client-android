package com.example.mistclient.auth.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mistclient.MistClient
import com.example.mistclient.auth.data.AuthRepository
import kotlinx.coroutines.launch

data class LoginUiState(
    val isAuthenticating: Boolean = false,
    val authenticationError: Throwable? = null,
    val authenticationCompleted: Boolean = false,
    val token: String = ""
)

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    var uiState: LoginUiState by mutableStateOf(LoginUiState())

    init {
        Log.d("LoginViewModel", "init")
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            Log.v("LoginViewModel", "login...");
            uiState = uiState.copy(isAuthenticating = true, authenticationError = null)
            val result = authRepository.login(username, password)
            if (result.isSuccess) {
                uiState = uiState.copy(isAuthenticating = false, authenticationCompleted = true)
            } else {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = result.exceptionOrNull()
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MistClient)
                LoginViewModel(
                    app.container.authRepository,
                )
            }
        }
    }
}