package com.example.mistclient

import android.app.Application
import android.util.Log

class MistClient : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        Log.d("MistClientApplication", "init")
        container = AppContainer(this)
    }
}