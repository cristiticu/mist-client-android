package com.example.mistclient

import android.app.Application
import android.util.Log

class MistClient : Application() {
    lateinit var applicationContext: ApplicationContext

    override fun onCreate() {
        super.onCreate()
        Log.d("MistClientApplication", "init")
        applicationContext = ApplicationContext(this)
    }
}