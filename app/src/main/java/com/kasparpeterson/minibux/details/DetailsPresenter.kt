package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.TradingQuote
import com.kasparpeterson.minibux.chooseproduct.Product
import com.kasparpeterson.minibux.details.view.DetailsViewState

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsPresenter(var product: Product,
                       view: DetailsMVP.ViewOperations,
                       model: DetailsMVP.ModelOperations)
    : DetailsMVP.PresenterViewOperations(view, model),
        DetailsMVP.PresenterModelOperations {

    override fun onStart() {
        super.onStart()
        onView { it.showState(DetailsViewState(product))}
        model.fetchProduct(product.securityId)
        model.startListening(product.securityId)
    }

    override fun onRetry() {
        model.fetchProduct(product.securityId)
    }

    override fun onProductFetched(product: Product) {
        this.product = product
        onView { it.showState(DetailsViewState(product)) }
    }

    override fun onProductFetchFailed() {
        onView { it.showState(DetailsViewState(product, isProductError = true)) }
    }

    override fun onTradingQuoteUpdate(tradingQuote: TradingQuote) {
        product.currentPrice.amount = tradingQuote.currentPrice
        onView { it.showState(DetailsViewState(product)) }
    }
}