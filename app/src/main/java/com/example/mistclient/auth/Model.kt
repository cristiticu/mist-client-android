package com.example.mistclient.auth

data class AuthTokenApiResponse(val access_token: String, val token_type: String)

data class AuthToken(val accessToken: String, val tokenType: String)

data class UserCredentials(val username: String, val password: String)

fun AuthTokenApiResponse.toAuthToken(): AuthToken {
    return AuthToken(accessToken = this.access_token, tokenType = this.token_type)
}