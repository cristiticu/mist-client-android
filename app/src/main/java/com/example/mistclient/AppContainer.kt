package com.example.mistclient

import android.content.Context
import android.util.Log
import com.example.mistclient.auth.data.AuthRepository
import com.example.mistclient.auth.data.RemoteAuthDataSource

class AppContainer(val context: Context) {
    init {
        Log.d("AppContainer", "init")
    }

    private val remoteAuthDataSource: RemoteAuthDataSource = RemoteAuthDataSource()

    val authRepository: AuthRepository by lazy {
        AuthRepository(remoteAuthDataSource)
    }

}