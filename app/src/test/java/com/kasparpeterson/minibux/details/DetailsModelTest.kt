package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.BuxWebClient
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

    val productId = "productId";

    lateinit var client: BuxWebClient
    lateinit var productService: ProductService
    lateinit var presenter: DetailsMVP.PresenterModelOperations
    lateinit var model: DetailsModel

    @Before
    fun setUp() {
        client = mock<BuxWebClient>()
        productService = mock<ProductService>()
        presenter = mock<DetailsMVP.PresenterModelOperations>()
        model = DetailsModel(client, productService)
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