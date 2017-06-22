package com.kasparpeterson.minibux.api

import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.*
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import org.junit.Test

import org.junit.Before
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
class BuxWebSocketClientTest {

    val gson = Gson()
    val securityId = "mock123"
    val currentPrice = "146.36"

    lateinit var okHttpClient: OkHttpClient
    lateinit var webSocket: WebSocket
    lateinit var socketClient: BuxWebSocketClient

    @Before
    fun setUp() {
        setUpOkHttpClient()
        socketClient = BuxWebSocketClient(gson, okHttpClient)
    }

    private fun setUpOkHttpClient() {
        webSocket = mock()
        okHttpClient = mock {
            on { newWebSocket(any(), any()) } doReturn webSocket
        }
    }

    @Test
    fun startListening() {
        val message = "{\"subscribeTo\":[\"trading.product.$securityId\"],\"unsubscribeFrom\":[]}"
        startListening(securityId)
        verify(webSocket).send(message)
    }

    @Test
    fun startListening_twice_reusesPreviousWebSocket() {
        socketClient.startListening("id1", mock())
        socketClient.startListening("id2", mock())
        verify(okHttpClient).newWebSocket(any(), any())
    }

    @Test
    fun stopListening() {
        // startListening has to be called before stopListening can be called
        startListening()
        val message = "{\"subscribeTo\":[],\"unsubscribeFrom\":[\"trading.product.$securityId\"]}"
        socketClient.stopListening(securityId)
        verify(webSocket).send(message)
    }

    @Test
    fun onClosed_informsListeners() {
        val listener = mock<TradingQuoteListener>()
        socketClient.startListening(securityId, listener)
        socketClient.onClosed(null, 0, null)
        verify(listener).onTradingQuoteUnAvailable()
    }

    @Test
    fun onClosed_createsNewSocket() {
        startListening(securityId)
        socketClient.onClosed(null, 0, null)
        startListening(securityId)
        verify(okHttpClient, times(2)).newWebSocket(any(), any())
    }

    @Test
    fun onFailure_informsListeners() {
        val listener = mock<TradingQuoteListener>()
        socketClient.startListening(securityId, listener)
        socketClient.onFailure(null, null, null)
        verify(listener).onTradingQuoteUnAvailable()
    }

    @Test
    fun onFailure_createsNewSocket() {
        startListening(securityId)
        socketClient.onFailure(null, null, null)
        startListening(securityId)
        verify(okHttpClient, times(2)).newWebSocket(any(), any())
    }

    @Test
    fun onMessage_emptyString() {
        val listener = mock<TradingQuoteListener>()
        socketClient.startListening(securityId, listener)
        socketClient.onMessage(webSocket, "")
        verify(listener, never()).onTradingQuoteUpdate(any())
    }

    @Test
    fun onMessage_gibberish() {
        val listener = mock<TradingQuoteListener>()
        socketClient.startListening(securityId, listener)
        socketClient.onMessage(webSocket, "some gibberish 123")
        verify(listener, never()).onTradingQuoteUpdate(any())
    }

    @Test
    fun onMessage_wrongSecurityId() {
        val listener = mock<TradingQuoteListener>()
        socketClient.startListening(securityId, listener)
        socketClient.onMessage(webSocket, getMockText("wrongSecurityId"))
        verify(listener, never()).onTradingQuoteUpdate(any())
    }

    @Test
    fun onMessage_correctSecurityId() {
        val listener = mock<TradingQuoteListener>()
        socketClient.startListening(securityId, listener)
        socketClient.onMessage(webSocket, getMockText(securityId))
        verify(listener).onTradingQuoteUpdate(TradingQuote(securityId, BigDecimal(currentPrice)))
    }

    private fun startListening(securityId: String) {
        socketClient.startListening(securityId, mock())
    }

    fun getMockText(securityId: String): String {
        return "{\n" +
                "  \"t\": \"trading.quote\",\n" +
                "  \"id\": \"a796fe40-506c-11e7-a9d6-3edef9cd5681\",\n" +
                "  \"v\": 2,\n" +
                "  \"body\": {\n" +
                "    \"securityId\": \"" + securityId + "\",\n" +
                "    \"currentPrice\": \"" + currentPrice + "\"\n" +
                "  }\n" +
                "}"
    }
}