package com.kasparpeterson.minibux.chooseproduct

import com.kasparpeterson.minibux.api.Listener
import com.kasparpeterson.minibux.api.ProductService

/**
 * Created by kaspar on 15/06/2017.
 */
class ChooseProductModel(val productService: ProductService): ChooseProductMVP.ModelOperations(),
        Listener<List<Product>> {

    override fun loadProducts() {
        productService.fetchProducts(this)
    }

    override fun onResponse(response: List<Product>) {
        presenter.onProductsLoaded(response)
    }

    override fun onFailure() {
        presenter.onProductsLoadFailed()
    }
}