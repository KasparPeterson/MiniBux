package com.kasparpeterson.minibux.api.models

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

/**
 * Created by kaspar on 22/06/2017.
 */
class ProductTest {

    @Test
    fun getChangePresentation_positive() {
        val product = Product("mock", "mock",
                Price("EUR", 2, BigDecimal("150.00")),
                Price("EUR", 2, BigDecimal("100.00")),
                "mock")
        assertEquals("50.00%", product.getChangePresentation())
    }

    @Test
    fun getChangePresentation_negative() {
        val product = Product("mock", "mock",
                Price("EUR", 2, BigDecimal("100.00")),
                Price("EUR", 2, BigDecimal("200.00")),
                "mock")
        assertEquals("-50.00%", product.getChangePresentation())
    }

    @Test
    fun getChangePresentation_zero() {
        val product = Product("mock", "mock",
                Price("EUR", 2, BigDecimal("100.00")),
                Price("EUR", 2, BigDecimal("100.00")),
                "mock")
        assertEquals("0.00%", product.getChangePresentation())
    }

    @Test
    fun getChangePresentation_infiniteDecimalPoints() {
        val product = Product("mock", "mock",
                Price("EUR", 2, BigDecimal("130.00")),
                Price("EUR", 2, BigDecimal("100.00")),
                "mock")
        assertEquals("30.00%", product.getChangePresentation())
    }

    @Test
    fun getChangePresentation_infiniteDecimalPoints2() {
        val product = Product("mock", "mock",
                Price("EUR", 2, BigDecimal("123.2312")),
                Price("EUR", 2, BigDecimal("197.1234")),
                "mock")
        assertEquals("-37.49%", product.getChangePresentation())
    }

    @Test
    fun getChangePresentation_positive_huge() {
        val product = Product("mock", "mock",
                Price("EUR", 2, BigDecimal("15000.00")),
                Price("EUR", 2, BigDecimal("100.00")),
                "mock")
        assertEquals("14,900.00%", product.getChangePresentation())
    }
}