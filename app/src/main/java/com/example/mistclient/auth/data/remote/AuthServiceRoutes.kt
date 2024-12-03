package com.example.mistclient.auth.data.remote

import com.example.mistclient.auth.AuthToken
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthServiceRoutes {
    @Multipart
    @POST("/user/auth")
    suspend fun login(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ): AuthToken
}