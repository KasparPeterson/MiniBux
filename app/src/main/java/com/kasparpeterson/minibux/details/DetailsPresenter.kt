package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.api.models.TradingQuote
import com.kasparpeterson.minibux.details.view.DetailsViewState

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsPresenter(var product: Product,
                       view: DetailsMVP.ViewOperations,
                       model: DetailsMVP.ModelOperations)
    : DetailsMVP.PresenterViewOperations(view, model),
        DetailsMVP.PresenterModelOperations {

    private var currentState = DetailsViewState(product)

    override fun onStart() {
        super.onStart()
        onView { it.showState(currentState)}
        model.fetchProduct(product.securityId)
        model.startListening(product.securityId)
    }

    override fun onRetry() {
        model.fetchProduct(product.securityId)
        model.startListening(product.securityId)
    }

    override fun onProductFetched(product: Product) {
        this.product = product
        showView(getNewState(isProductError = false))
    }

    override fun onProductFetchFailed() {
        showView(getNewState(isProductError = isDescriptionEmpty()))
    }

    private fun isDescriptionEmpty(): Boolean {
        return product.description == null || product.description!!.isEmpty()
    }

    override fun onTradingQuoteUpdate(tradingQuote: TradingQuote) {
        product.currentPrice.amount = tradingQuote.currentPrice
        showView(getNewState(isPriceError = false))
    }

    override fun onTradingQuoteUnAvailable() {
        showView(getNewState(isPriceError = true))
    }

    private fun getNewState(isProductError: Boolean? = null,
                            isPriceError: Boolean? = null): DetailsViewState {
        return currentState.getNewState(product,
                isProductError = isProductError,
                isPriceError = isPriceError)
    }

    private fun showView(state: DetailsViewState) {
        currentState = state
        onView { it.showState(state) }
    }
}