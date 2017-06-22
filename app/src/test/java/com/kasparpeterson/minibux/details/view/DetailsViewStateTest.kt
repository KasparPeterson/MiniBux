package com.kasparpeterson.minibux.details.view

import com.kasparpeterson.minibux.api.models.Price
import com.kasparpeterson.minibux.api.models.Product
import org.junit.Test
import org.junit.Assert.*

/**
 * Created by kaspar on 22/06/2017.
 */
class DetailsViewStateTest {

    val product = Product("mock1", "mock2", Price(), Price(), "mock3")

    @Test
    fun fromOldState_productNull() {
        val oldState = DetailsViewState(product)
        val newState = oldState.getNewState()
        assertEquals(DetailsViewState(product), newState)
    }

    @Test
    fun fromOldState_productNotNull() {
        val newProduct = Product("mock11", "mock12", Price(), Price(), "mock13")
        val oldState = DetailsViewState(product)
        val newState = oldState.getNewState(newProduct)
        assertEquals(DetailsViewState(newProduct), newState)
    }

    @Test
    fun fromOldState_isProductErrorNull() {
        val oldState = DetailsViewState(product, isProductError = true)
        val newState = oldState.getNewState()
        assertEquals(DetailsViewState(product, isProductError = true), newState)
    }

    @Test
    fun fromOldState_isProductErrorNotNull() {
        val oldState = DetailsViewState(product)
        val newState = oldState.getNewState(isProductError = true)
        assertEquals(DetailsViewState(product, isProductError = true), newState)
    }

    @Test
    fun fromOldState_isPriceErrorNull() {
        val oldState = DetailsViewState(product, isPriceError = true)
        val newState = oldState.getNewState()
        assertEquals(DetailsViewState(product, isPriceError = true), newState)
    }

    @Test
    fun fromOldState_isPriceErrorNotNull() {
        val oldState = DetailsViewState(product)
        val newState = oldState.getNewState(isPriceError = true)
        assertEquals(DetailsViewState(product, isPriceError = true), newState)
    }
}