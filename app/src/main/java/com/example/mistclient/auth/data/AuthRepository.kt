package com.example.mistclient.auth.data

import android.util.Log
import com.example.mistclient.api.Api
import com.example.mistclient.auth.AuthToken
import com.example.mistclient.auth.UserCredentials
import com.example.mistclient.auth.data.remote.RemoteAuthDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val remoteAuthDataSource: RemoteAuthDataSource) {
    init {
        Log.d("AuthRepository", "init")
    }

    fun logout() {
        Api.tokenInterceptor.token = null
    }

    suspend fun login(username: String, password: String): Result<AuthToken> {
        return withContext(Dispatchers.IO) {
            val credentials = UserCredentials(username, password)
            val result = remoteAuthDataSource.login(credentials)
            if (result.isSuccess) {
                Api.tokenInterceptor.token = result.getOrNull()?.accessToken
            }
            return@withContext result
        }
    }
}