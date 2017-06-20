package com.kasparpeterson.minibux.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kasparpeterson.minibux.chooseproduct.Product
import okhttp3.*
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by kaspar on 15/06/2017.
 */
open class ProductService(val gson: Gson, val client: OkHttpClient) {

    private val baseUrl = "https://api.beta.getbux.com/core/16/products"
    private val baseRequestBuilder: Request.Builder = Request.Builder()
            .addHeader("Authorization", API_TOKEN)
            .addHeader("Accept-Language", "en-EN,en;q=0.8")
    private val productsRequest: Request = baseRequestBuilder
            .url(baseUrl)
            .build()

    private fun getProductRequest(productId: String): Request {
        return baseRequestBuilder.url(baseUrl + "/" + productId).build()
    }

    var products: List<Product>? = null

    open fun fetchProduct(productId: String, listener: Listener<Product>) {
        val callback = HttpCallback(listener, Product::class.java, gson)
        makeRequest(getProductRequest(productId), callback)
    }

    open fun fetchProducts(listener: Listener<List<Product>>) {
        if (products != null) {
            listener.onResponse(products!!)
        } else {
            fetchProductsRequest(listener)
        }
    }

    private fun fetchProductsRequest(listener: Listener<List<Product>>) {
        val type = object : TypeToken<List<Product>>() {}.type
        val callback = HttpCallback(listener, type, gson) {
            products = it
        }
        makeRequest(productsRequest, callback)
    }

    private fun <T> makeRequest(request: Request, callback: HttpCallback<T>) {
        client.newCall(request).enqueue(callback)
    }
}

open class HttpCallback<in T>(val listener: Listener<T>,
                              val type: Type,
                              val gson: Gson,
                              val successInterceptor: ((T) -> Unit)? = null) : Callback {

    override fun onFailure(call: Call?, e: IOException?) {
        onFailure()
    }

    override fun onResponse(call: Call?, response: Response?) {
        if (response != null && response.isSuccessful) {
            onSuccess(response.body())
        } else {
            onFailure()
        }
    }

    private fun onSuccess(responseBody: ResponseBody) {
        val response = gson.fromJson<T>(responseBody.string(), type)
        successInterceptor?.invoke(response)
        listener.onResponse(response)
    }

    private fun onFailure() {
        listener.onFailure()
    }
}

interface Listener<in T> {
    fun onResponse(response: T)
    fun onFailure()
}
