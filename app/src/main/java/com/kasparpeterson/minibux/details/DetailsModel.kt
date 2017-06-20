package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.*
import com.kasparpeterson.minibux.chooseproduct.Product

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsModel(val webSocketClient: BuxWebSocketClient, val productService: ProductService)
    : DetailsMVP.ModelOperations(), TradingQuoteListener, Listener<Product> {

    private var securityId: String? = null

    override fun fetchProduct(securityId: String) {
        productService.fetchProduct(securityId, this)
    }

    override fun startListening(securityId: String) {
        this.securityId = securityId
        webSocketClient.startListening(securityId, this)
    }

    override fun onUpdate(tradingQuote: TradingQuote) {
        presenter.onTradingQuoteUpdate(tradingQuote)
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