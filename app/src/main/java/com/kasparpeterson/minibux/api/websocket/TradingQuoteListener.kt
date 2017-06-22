package com.kasparpeterson.minibux.api.websocket

import com.kasparpeterson.minibux.api.models.TradingQuote

/**
 * Created by kaspar on 22/06/2017.
 */
interface TradingQuoteListener {
    fun onTradingQuoteUpdate(tradingQuote: TradingQuote)
    fun onTradingQuoteUnAvailable()
}