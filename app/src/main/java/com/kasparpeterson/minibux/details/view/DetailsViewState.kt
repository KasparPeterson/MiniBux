package com.kasparpeterson.minibux.details.view

import com.kasparpeterson.minibux.api.models.Product

/**
 * Created by kaspar on 22/06/2017.
 */
data class DetailsViewState(
        val product: Product,
        val isProductError: Boolean = false,
        val isPriceError: Boolean = false) {

    fun getNewState(product: Product? = null,
                    isProductError: Boolean? = null,
                    isPriceError: Boolean? = null): DetailsViewState {
        return DetailsViewState(getNewProduct(product),
                getNewIsProductError(isProductError),
                getNewIsPriceError(isPriceError))
    }

    private fun getNewProduct(product: Product?): Product {
        if (product == null) return this.product
        return product
    }

    private fun getNewIsProductError(isProductError: Boolean?): Boolean {
        if (isProductError == null) return this.isProductError
        return isProductError
    }

    private fun getNewIsPriceError(isPriceError: Boolean?): Boolean {
        if (isPriceError == null) return this.isPriceError
        return isPriceError
    }

}