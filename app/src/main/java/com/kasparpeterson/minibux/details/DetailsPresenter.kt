package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.TradingQuote
import com.kasparpeterson.minibux.chooseproduct.Product

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsPresenter(val product: Product,
                       view: DetailsMVP.ViewOperations,
                       model: DetailsMVP.ModelOperations):
        DetailsMVP.PresenterViewOperations(view, model),
        DetailsMVP.PresenterModelOperations {

    override fun onStart() {
        super.onStart()
        onView { view -> view.showProduct(product) }
        model.fetchProduct(product.securityId)
        model.startListening(product.securityId)
    }

    override fun onProductFetched(product: Product) {
        onView { view ->  view.showProduct(product) }
    }

    override fun onProductFetchFailed() {
        onView { view -> view.showError() }
    }

    override fun onTradingQuoteUpdate(tradingQuote: TradingQuote) {
        onView { view -> view.updatePrice(tradingQuote.currentPrice) }
    }
}