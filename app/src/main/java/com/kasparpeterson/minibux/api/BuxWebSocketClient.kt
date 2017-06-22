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
open class BuxWebSocketClient(val gson: Gson, val client: OkHttpClient): WebSocketListener() {

    private val TRADING_QUOTE = "trading.quote"
    private val request = Request.Builder()
            .url("https://rtf.beta.getbux.com/subscriptions/me")
            .addHeader("Authorization", API_TOKEN)
            .addHeader("Accept-Language", "en-EN,en;q=0.8")
            .build()
    private var webSocket: WebSocket? = null
    private val listeners = HashMap<String, TradingQuoteListener>()

    private fun initialiseConnection() {
        webSocket = client.newWebSocket(request, this)
    }

    open fun startListening(securityId: String, listener: TradingQuoteListener) {
        if (webSocket == null) {
            initialiseConnection()
        }

        listeners.put(securityId, listener)
        val message = getSubscription(securityId)
        webSocket!!.send(message)
    }

    open fun stopListening(securityId: String) {
        listeners.remove(securityId)
        webSocket?.send(getUnSubscription(securityId))
    }

    override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
        this.webSocket = null
        informListenersAboutFailure()
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: okhttp3.Response?) {
        t?.printStackTrace()
        this.webSocket = null
        informListenersAboutFailure()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
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
        val listener = listeners[tradingQuote.securityId]
        listener?.onTradingQuoteUpdate(tradingQuote)
    }

    private fun getSubscription(securityId: String): String {
        return gson.toJson(SubscriptionMessage(
                listOf("trading.product." + securityId),
                listOf<String>()))
    }

    private fun getUnSubscription(securityId: String): String {
        return gson.toJson(SubscriptionMessage(
                listOf<String>(),
                listOf("trading.product." + securityId)))
    }

    private fun informListenersAboutFailure() {
        for (listener in listeners.values) listener.onTradingQuoteUnAvailable()
        listeners.clear()
    }
}

interface TradingQuoteListener {
    fun onTradingQuoteUpdate(tradingQuote: TradingQuote)
    fun onTradingQuoteUnAvailable()
}

data class Response(
        val t: String,
        val id: String,
        val v: Int,
        val body: JsonObject)

data class TradingQuote(
        val securityId: String,
        val currentPrice: BigDecimal)

data class SubscriptionMessage(
        val subscribeTo: List<String>,
        val unsubscribeFrom: List<String>)