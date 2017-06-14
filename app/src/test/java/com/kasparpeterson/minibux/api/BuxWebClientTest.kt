package com.kasparpeterson.minibux.api

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import okhttp3.WebSocket
import org.junit.Test

import org.junit.Before
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
class BuxWebClientTest {

    val securityId = "mock123"
    val currentPrice = "146.36"

    lateinit var webSocket: WebSocket
    lateinit var client: BuxWebClient

    @Before
    fun setUp() {
        webSocket = mock<WebSocket>()
        client = BuxWebClient()
    }

    @Test
    fun onMessage_emptyString() {
        val listener = mock<TradingQuoteListener>()
        client.startListening(securityId, listener)
        client.onMessage(webSocket, "")
        verify(listener, never()).onUpdate(any())
    }

    @Test
    fun onMessage_gibberish() {
        val listener = mock<TradingQuoteListener>()
        client.startListening(securityId, listener)
        client.onMessage(webSocket, "some gibberish 123")
        verify(listener, never()).onUpdate(any())
    }

    @Test
    fun onMessage_wrongSecurityId() {
        val listener = mock<TradingQuoteListener>()
        client.startListening(securityId, listener)
        client.onMessage(webSocket, getMockText("wrongSecurityId"))
        verify(listener, never()).onUpdate(any())
    }

    @Test
    fun onMessage_correctSecurityId() {
        val listener = mock<TradingQuoteListener>()
        client.startListening(securityId, listener)
        client.onMessage(webSocket, getMockText(securityId))
        verify(listener).onUpdate(TradingQuote(securityId, BigDecimal(currentPrice)))
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