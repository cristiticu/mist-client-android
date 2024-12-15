package com.example.mistclient.api

import com.google.gson.Gson

data class WebsocketMessage(
    val type: String,
    val data: Any
)

fun parseMessageJson(json: String): WebsocketMessage {
    return Gson().fromJson(json, WebsocketMessage::class.java)
}