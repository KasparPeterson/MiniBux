package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.BuxWebClient
import com.kasparpeterson.minibux.api.TradingQuote
import com.kasparpeterson.minibux.api.TradingQuoteListener

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsModel(val webClient: BuxWebClient): DetailsMVP.ModelOperations(),
        TradingQuoteListener {

    override fun startListening(securityId: String) {
        webClient.startListening(securityId, this)
    }

    override fun onUpdate(tradingQuote: TradingQuote) {
        presenter.onTradingQuoteUpdate(tradingQuote)
    }
}