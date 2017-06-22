package com.kasparpeterson.minibux.api

import com.kasparpeterson.minibux.chooseproduct.Product

/**
 * Created by kaspar on 15/06/2017.
 */

open class ProductManager(val service: ProductService) {

    var products: List<Product>? = null

    open fun fetchProduct(productId: String, listener: Listener<Product>) {
        val call = service.getProduct(API_TOKEN, productId)
        call.enqueue(HttpCallback(listener))
    }

    open fun fetchProducts(listener: Listener<List<Product>>) {
        if (products != null) {
            listener.onResponse(products!!)
        } else {
            fetchProductsRequest(listener)
        }
    }

    private fun fetchProductsRequest(listener: Listener<List<Product>>) {
        val call = service.getProducts(API_TOKEN)
        call.enqueue(HttpCallback(HttpCallbackInterceptor(listener) {
            products = it
        }))
    }
}

private class HttpCallbackInterceptor<in T>(val listener: Listener<T>,
                                            val interceptor: (T) -> Unit) : Listener<T> {

    override fun onResponse(response: T) {
        interceptor.invoke(response)
        listener.onResponse(response)
    }

    override fun onFailure() {
        listener.onFailure()
    }
}

interface Listener<in T> {
    fun onResponse(response: T)
    fun onFailure()
}
