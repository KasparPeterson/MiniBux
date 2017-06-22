package com.kasparpeterson.minibux.api.rest

import com.kasparpeterson.minibux.api.API_TOKEN
import com.kasparpeterson.minibux.api.models.Product

/**
 * Created by kaspar on 15/06/2017.
 */
open class ProductManager(val service: ProductService) {

    var products: List<Product>? = null

    open fun fetchProduct(productId: String, listener: HttpListener<Product>) {
        val call = service.getProduct(API_TOKEN, productId)
        call.enqueue(HttpCallback(listener))
    }

    open fun fetchProducts(listener: HttpListener<List<Product>>) {
        if (products != null) {
            listener.onResponse(products!!)
        } else {
            fetchProductsRequest(listener)
        }
    }

    private fun fetchProductsRequest(listener: HttpListener<List<Product>>) {
        val call = service.getProducts(API_TOKEN)
        call.enqueue(HttpCallback(HttpCallbackInterceptor(listener) {
            products = it
        }))
    }
}
