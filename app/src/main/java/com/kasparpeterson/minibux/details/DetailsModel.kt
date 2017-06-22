package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.api.models.TradingQuote
import com.kasparpeterson.minibux.api.rest.HttpListener
import com.kasparpeterson.minibux.api.rest.ProductManager
import com.kasparpeterson.minibux.api.websocket.BuxWebSocketClient
import com.kasparpeterson.minibux.api.websocket.TradingQuoteListener

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsModel(val webSocketClient: BuxWebSocketClient, val productManager: ProductManager)
    : DetailsMVP.ModelOperations(), TradingQuoteListener, HttpListener<Product> {

    private var securityId: String? = null

    override fun fetchProduct(securityId: String) {
        productManager.fetchProduct(securityId, this)
    }

    override fun startListening(securityId: String) {
        this.securityId = securityId
        webSocketClient.startListening(securityId, this)
    }

    override fun onTradingQuoteUpdate(tradingQuote: TradingQuote) {
        presenter.onTradingQuoteUpdate(tradingQuote)
    }

    override fun onTradingQuoteUnAvailable() {
        presenter.onTradingQuoteUnAvailable()
    }

    override fun onResponse(response: Product) {
        presenter.onProductFetched(response)
    }

    override fun onFailure() {
        presenter.onProductFetchFailed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (securityId != null) {
            webSocketClient.stopListening(securityId!!)
        }
    }
}