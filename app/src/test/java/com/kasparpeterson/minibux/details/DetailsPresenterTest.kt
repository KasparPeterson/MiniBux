package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.TradingQuote
import com.kasparpeterson.minibux.chooseproduct.Price
import com.kasparpeterson.minibux.chooseproduct.Product
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsPresenterTest {

    val securityId = "mockId"
    val product = Product("name", securityId, Price(), "category")

    lateinit var view: DetailsMVP.ViewOperations
    lateinit var model: DetailsMVP.ModelOperations
    lateinit var presenter: DetailsPresenter

    @Before
    fun setUp() {
        view = mock<DetailsMVP.ViewOperations>()
        model = mock<DetailsMVP.ModelOperations>()
        presenter = DetailsPresenter(product, view, model)
        presenter.onStart()
        presenter.onResume()
    }

    @Test
    fun initialState() {
        verify(view).showProduct(product)
        verify(model).fetchProduct(securityId)
        verify(model).startListening(securityId)
    }

    @Test
    fun onProductFetched() {
        presenter.onProductFetched(product)
        verify(view, times(2)).showProduct(product)
    }

    @Test
    fun onProductFetchFailed() {
        presenter.onProductFetchFailed()
        verify(view).showError()
    }

    @Test
    fun onTradingQuoteUpdate() {
        presenter.onTradingQuoteUpdate(TradingQuote(securityId, BigDecimal.ZERO))
        verify(view).updatePrice(BigDecimal.ZERO)
    }

}