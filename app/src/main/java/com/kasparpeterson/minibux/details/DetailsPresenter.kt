package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.TradingQuote

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsPresenter(val securityId: String,
                       view: DetailsMVP.ViewOperations,
                       model: DetailsMVP.ModelOperations):
        DetailsMVP.PresenterViewOperations(view, model),
        DetailsMVP.PresenterModelOperations {

    override fun onStart() {
        super.onStart()
        model.startListening(securityId)
    }

    override fun onTradingQuoteUpdate(tradingQuote: TradingQuote) {
        onView { view -> view.updatePrice(tradingQuote.currentPrice) }
    }
}