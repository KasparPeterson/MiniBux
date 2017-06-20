package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.BuxWebSocketClient
import com.kasparpeterson.minibux.api.ProductService
import com.kasparpeterson.minibux.api.TradingQuote
import com.nhaarman.mockito_kotlin.mock
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
    lateinit var productService: ProductService
    lateinit var presenter: DetailsMVP.PresenterModelOperations
    lateinit var model: DetailsModel

    @Before
    fun setUp() {
        socketClient = mock<BuxWebSocketClient>()
        productService = mock<ProductService>()
        presenter = mock<DetailsMVP.PresenterModelOperations>()
        model = DetailsModel(socketClient, productService)
        model.presenter = presenter
    }

    @Test
    fun startListening() {

    }

    @Test
    fun fetchProduct() {
        model.fetchProduct(productId)
    }

    @Test
    fun onUpdate() {
        model.onUpdate(TradingQuote(productId, BigDecimal.ZERO))
        verify(presenter).onTradingQuoteUpdate(TradingQuote(productId, BigDecimal.ZERO))
    }

}