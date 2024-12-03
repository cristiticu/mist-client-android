package com.example.mistclient.auth.data

import com.example.mistclient.auth.AuthToken
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RemoteRoutes {
    @Multipart
    @POST("/user/auth")
    suspend fun login(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ): AuthToken
}