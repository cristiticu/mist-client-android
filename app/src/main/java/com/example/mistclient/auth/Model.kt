package com.example.mistclient.auth

data class AuthToken(val accessToken: String, val tokenType: String)

data class UserCredentials(val username: String, val password: String)