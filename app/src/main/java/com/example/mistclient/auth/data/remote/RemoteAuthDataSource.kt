package com.example.mistclient.auth.data.remote

import android.util.Log
import com.example.mistclient.api.Api
import com.example.mistclient.auth.AuthToken
import com.example.mistclient.auth.UserCredentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun createPartFromString(stringData: String): RequestBody {
    return stringData.toRequestBody("text/plain".toMediaTypeOrNull());
}


class RemoteAuthDataSource {
    private val routes = Api.retrofit.create(AuthServiceRoutes::class.java)

    suspend fun login(credentials: UserCredentials): Result<AuthToken> {
        try {
            val username = createPartFromString(credentials.username)
            val password = createPartFromString((credentials.password))

            Log.d("RemoteAuthDataSource", "login")

            return Result.success(routes.login(username, password))
        } catch (e: Exception) {
            Log.w("RemoteAuthDataSource", "login failed", e)
            return Result.failure(e)
        }
    }
}