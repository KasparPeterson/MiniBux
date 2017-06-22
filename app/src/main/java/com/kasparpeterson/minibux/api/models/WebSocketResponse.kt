package com.kasparpeterson.minibux.api.models

import com.google.gson.JsonObject
import java.math.BigDecimal

/**
 * Created by kaspar on 22/06/2017.
 */
data class Response(
        val t: String,
        val id: String,
        val v: Int,
        val body: JsonObject)

data class TradingQuote(
        val securityId: String,
        val currentPrice: BigDecimal)

data class SubscriptionMessage(
        val subscribeTo: List<String>,
        val unsubscribeFrom: List<String>)