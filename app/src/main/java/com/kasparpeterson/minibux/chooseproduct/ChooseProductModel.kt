package com.kasparpeterson.minibux.chooseproduct

import com.kasparpeterson.minibux.api.Listener
import com.kasparpeterson.minibux.api.ProductManager

/**
 * Created by kaspar on 15/06/2017.
 */
class ChooseProductModel(val productManager: ProductManager): ChooseProductMVP.ModelOperations(),
        Listener<List<Product>> {

    override fun loadProducts() {
        productManager.fetchProducts(this)
    }

    override fun onResponse(response: List<Product>) {
        presenter.onProductsLoaded(response)
    }

    override fun onFailure() {
        presenter.onProductsLoadFailed()
    }
}