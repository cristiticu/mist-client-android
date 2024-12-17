package com.example.mistclient.games.data.remote

import android.util.Log
import com.example.mistclient.api.Api
import com.example.mistclient.games.Game
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

data class GamesWebsocketMessage(
    val type: String,
    val data: Game
)

fun parseGameMessageJson(json: String): GamesWebsocketMessage {
    return Gson().fromJson(json, GamesWebsocketMessage::class.java)
}

class GamesWebSocketClient() {
    private lateinit var webSocket: WebSocket
    private val okHttpClient: OkHttpClient = Api.client

    fun connect(url: String, listener: WebSocketListener) {
        val request = Request.Builder().url(url).build()
        webSocket = okHttpClient.newWebSocket(request, listener)
    }

    fun disconnect() {
        if (::webSocket.isInitialized) {
            webSocket.close(1000, "Disconnecting")
        }
    }
}

class GamesWebSocketListener(
    private val onOpen: () -> Unit,
    private val onClosed: () -> Unit,
    private val onNewGame: (Game) -> Unit
) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("GamesWebSocketListener", "onOpen")
        super.onOpen(webSocket, response)
        onOpen()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("GamesWebSocketListener", "onClosed")
        super.onClosed(webSocket, code, reason)
        onClosed()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("GamesWebSocketListener", "got new message $text")
        val message = parseGameMessageJson(text) // Deserialize JSON to Game
        Log.d("GamesWebSocketListener", "parser message $message")
        onNewGame(
            message.data
        )
    }
}

