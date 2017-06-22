package com.kasparpeterson.minibux.api.models

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by kaspar on 15/06/2017.
 */
data class Product(
        val displayName: String,
        val securityId: String,
        val currentPrice: Price,
        val closingPrice: Price,
        val category: String,
        val description: String? = null) {

    fun getChangePresentation(): String {
        return formatPercentage(getChangePercentage()) + "%"
    }

    private fun getChangePercentage(): BigDecimal {
        val hundred = BigDecimal("100")
        val percentage = currentPrice.amount
                .multiply(hundred)
                .divide(closingPrice.amount, RoundingMode.HALF_UP)
        return percentage.subtract(hundred)
    }

    private fun formatPercentage(percentage: BigDecimal): String {
        val numberFormat = DecimalFormat("#,##0.00")
        return numberFormat.format(percentage)
    }
}

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