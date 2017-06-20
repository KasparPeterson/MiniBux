package com.kasparpeterson.minibux.chooseproduct

import java.math.BigDecimal

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
        var amount: BigDecimal = BigDecimal.ZERO)