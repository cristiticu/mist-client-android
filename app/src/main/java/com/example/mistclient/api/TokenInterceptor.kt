package com.example.mistclient.api

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        if (url.encodedPath.endsWith("/user/auth")) {
            return chain.proceed(request)
        }

        if (token == null) {
            return chain.proceed(request)
        }

        val authenticatedRequest = request
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .url(url)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}