package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.models.Price
import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.api.models.TradingQuote
import com.kasparpeterson.minibux.details.view.DetailsViewState
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsPresenterTest {

    val securityId = "mockId"

    lateinit var view: DetailsMVP.ViewOperations
    lateinit var model: DetailsMVP.ModelOperations
    lateinit var presenter: DetailsPresenter

    @Before
    fun setUp() {
        view = mock()
        model = mock()
        presenter = DetailsPresenter(getProduct(), view, model)
        presenter.onStart()
        presenter.onResume()
    }

    @Test
    fun initialState() {
        verify(view).showState(DetailsViewState(getProduct()))
        verify(model).fetchProduct(securityId)
        verify(model).startListening(securityId)
    }

    @Test
    fun onRetry() {
        presenter.onRetry()
        verify(model, times(2)).fetchProduct(securityId)
        verify(model, times(2)).startListening(securityId)
    }

    @Test
    fun onProductFetched() {
        presenter.onProductFetched(getProduct())
        verify(view, times(2)).showState(DetailsViewState(getProduct()))
    }

    @Test
    fun onProductFetched_removesProductError() {
        presenter.onProductFetchFailed()
        presenter.onProductFetched(getProduct())
        verify(view, times(2)).showState(DetailsViewState(getProduct()))
    }

    @Test
    fun onProductFetchFailed() {
        presenter.onProductFetchFailed()
        verify(view).showState(DetailsViewState(getProduct(), isProductError = true))
    }

    @Test
    fun onProductFetchFailed_productErrorNotShownWhenDescriptionExists() {
        presenter.onProductFetched(getProduct(description = "Mock description"))
        presenter.onProductFetchFailed()
        verify(view, never()).showState(DetailsViewState(getProduct(), isProductError = true))
    }

    @Test
    fun onProductFetchFailed_productErrorShownWhenDescriptionIsNull() {
        presenter.onProductFetched(getProduct(description = null))
        presenter.onProductFetchFailed()
        verify(view).showState(DetailsViewState(getProduct(), isProductError = true))
    }

    @Test
    fun onTradingQuoteUpdate() {
        presenter.onTradingQuoteUpdate(TradingQuote(securityId, BigDecimal.TEN))
        verify(view, times(2)).showState(DetailsViewState(getProduct(BigDecimal.TEN)))
    }

    @Test
    fun onTradingQuoteUpdate_removesPriceError() {
        presenter.onTradingQuoteUnAvailable()
        presenter.onTradingQuoteUpdate(TradingQuote(securityId, BigDecimal.TEN))
        verify(view, times(2)).showState(DetailsViewState(getProduct(BigDecimal.TEN)))
    }

    @Test
    fun onTradingQuoteUnAvailable() {
        presenter.onTradingQuoteUnAvailable()
        verify(view).showState(DetailsViewState(getProduct(), isPriceError = true))
    }

    private fun getProduct(price: BigDecimal = BigDecimal.ONE, description: String? = null): Product {
        return Product("name", securityId, Price("EUR", 2, price), Price(), "category", description)
    }
}