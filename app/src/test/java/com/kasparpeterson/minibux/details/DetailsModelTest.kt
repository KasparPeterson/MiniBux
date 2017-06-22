package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.BuxWebSocketClient
import com.kasparpeterson.minibux.api.ProductManager
import com.kasparpeterson.minibux.api.TradingQuote
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

import org.junit.Before
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsModelTest {

    val productId = "productId"

    lateinit var socketClient: BuxWebSocketClient
    lateinit var productManager: ProductManager
    lateinit var presenter: DetailsMVP.PresenterModelOperations
    lateinit var model: DetailsModel

    @Before
    fun setUp() {
        socketClient = mock()
        productManager = mock()
        presenter = mock()
        model = DetailsModel(socketClient, productManager)
        model.presenter = presenter
    }

    @Test
    fun startListening() {
        model.startListening(productId)
        verify(socketClient).startListening(productId, model)
    }

    @Test
    fun fetchProduct() {
        model.fetchProduct(productId)
    }

    @Test
    fun onUpdate() {
        model.onTradingQuoteUpdate(TradingQuote(productId, BigDecimal.ZERO))
        verify(presenter).onTradingQuoteUpdate(TradingQuote(productId, BigDecimal.ZERO))
    }

    @Test
    fun onTradingQuoteUnAvailable() {
        model.onTradingQuoteUnAvailable()
        verify(presenter).onTradingQuoteUnAvailable()
    }

    @Test
    fun onDestroy() {
        model.onDestroy()
        verify(socketClient, never()).stopListening(productId)
    }

    @Test
    fun onDestroy_whenListening() {
        startListening()
        model.onDestroy()
        verify(socketClient).stopListening(productId)
    }

}