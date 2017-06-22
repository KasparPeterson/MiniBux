package com.kasparpeterson.minibux.api.models

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

/**
 * Created by kaspar on 15/06/2017.
 */
data class Product(
        val displayName: String,
        val securityId: String,
        val currentPrice: Price,
        val category: String,
        val description: String = "")

data class Price(
        val currency: String = "",
        val decimals: Int = 0,
        var amount: BigDecimal = BigDecimal.ZERO) {

    fun getFormattedPresentation(): String {
        val numberFormat = NumberFormat.getNumberInstance()
        val currencyFormat = Currency.getInstance(currency)
        return currencyFormat.symbol + " " + numberFormat.format(amount)
    }

}