package com.kasparpeterson.minibux.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
open class BuxWebClient: WebSocketListener() {

    companion object {
        private val TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6IjJlMDcxNjllLTNlMTMtNGFjOS04M2JkLTFiN2I5ZTYwZGM2OSIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInNjcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwibmJmIjoxNDczOTQyMzk0LCJleHAiOjE1MDU0NzgzOTQsImlhdCI6MTQ3Mzk0MjM5NCwianRpIjoiMzgyNTVlNmMtMTU0MC00OGJkLWIxMjUtZWVkMmYyZjRjZDdlIiwiY2lkIjoiODQ3MzYyMjkzNCJ9.1r55eMkL7s2kBMio9KqHqS7NYHdBMT7LAJOFc2U08Lg"
        private val TRADING_QUOTE = "trading.quote"
    }

    private val gson = Gson()
    private val webSocket: WebSocket
    private val listeners = HashMap<String, TradingQuoteListener>()

    init {
        val client = OkHttpClient()
        webSocket = client.newWebSocket(getRequest(), this)
        client.dispatcher().executorService().shutdown()
    }

    fun startListening(securityId: String, listener: TradingQuoteListener) {
        println("startListening!!")
        listeners.put(securityId, listener)
        webSocket.send(SUBSCRIBE_MESSAGE)
    }

    fun stopListening(securityId: String) {
        listeners.remove(securityId)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("Receiving : " + text)
        try {
            tryParsing(text)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun tryParsing(text: String) {
        val response = gson.fromJson(text, Response::class.java)
        if (response.t == TRADING_QUOTE) {
            notifyTradingQuoteListener(response.body)
        }
    }

    private fun notifyTradingQuoteListener(tradingQuoteBody: JsonObject) {
        val tradingQuote = gson.fromJson(tradingQuoteBody, TradingQuote::class.java)
        listeners[tradingQuote.securityId]?.onUpdate(tradingQuote)
    }

    private fun getRequest(): Request {
        return Request.Builder()
                .url("https://rtf.beta.getbux.com/subscriptions/me")
                .addHeader("Authorization", TOKEN)
                .addHeader("Accept-Language", "nl-NL,en;q=0.8")
                .build()
    }

    private val SUBSCRIBE_MESSAGE = "{\n" +
            "        \"subscribeTo\": [\n" +
            "           \"trading.product.sb26513\"\n" +
            "        ],\n" +
            "        \"unsubscribeFrom\": [\n" +
            "] }"

}

interface TradingQuoteListener {
    fun onUpdate(tradingQuote: TradingQuote)
}

data class Response(val t: String,
                    val id: String,
                    val v: Int,
                    val body: JsonObject)

data class TradingQuote(val securityId: String,
                        val currentPrice: BigDecimal)