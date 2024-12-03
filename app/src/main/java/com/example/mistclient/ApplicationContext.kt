package com.example.mistclient

import android.content.Context
import android.util.Log
import com.example.mistclient.auth.data.AuthRepository
import com.example.mistclient.auth.data.remote.RemoteAuthDataSource

class ApplicationContext(val context: Context) {
    init {
        Log.d("ApplicationContext", "init")
    }

    private val remoteAuthDataSource = RemoteAuthDataSource()

    val authRepository by lazy {
        AuthRepository(remoteAuthDataSource)
    }

}