package com.kasparpeterson.minibux.chooseproduct

import com.kasparpeterson.minibux.api.rest.ProductManager
import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.api.rest.HttpListener

/**
 * Created by kaspar on 15/06/2017.
 */
class ChooseHttpModel(val productManager: ProductManager)
    : ChooseProductMVP.ModelOperations(), HttpListener<List<Product>> {

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