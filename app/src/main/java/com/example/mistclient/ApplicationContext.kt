package com.example.mistclient

import android.content.Context
import android.util.Log
import com.example.mistclient.auth.data.AuthRepository
import com.example.mistclient.auth.data.remote.RemoteAuthDataSource
import com.example.mistclient.games.data.StoreGamesRepository
import com.example.mistclient.games.data.remote.RemoteStoreGamesDataSource

class ApplicationContext(val context: Context) {
    init {
        Log.d("ApplicationContext", "init")
    }

    private val remoteAuthDataSource = RemoteAuthDataSource()
    private val remoteStoreGamesDataSource = RemoteStoreGamesDataSource()

    val authRepository by lazy {
        AuthRepository(remoteAuthDataSource)
    }

    val storeGamesRepository by lazy {
        StoreGamesRepository(remoteStoreGamesDataSource)
    }

}