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

    val baseUrl = "https://api.beta.getbux.com/core/16/products"
    val baseRequestBuilder: Request.Builder = Request.Builder()
            .addHeader("Authorization", API_TOKEN)
            .addHeader("Accept-Language", "en-EN,en;q=0.8")
    val productsRequest: Request = baseRequestBuilder
            .url(baseUrl)
            .build()

    fun getProductRequest(productId: String): Request {
        return baseRequestBuilder.url(baseUrl + "/" + productId).build()
    }

    var products: List<Product>? = null

    open fun fetchProducts(listener: Listener<List<Product>>) {
        if (products != null) {
            listener.onResponse(products!!)
        } else {
            makeRequest(productsRequest, listener, object : TypeToken<List<Product>>() {}.type)
        }
    }

    open fun fetchProduct(productId: String, listener: Listener<Product>) {
        makeRequest(getProductRequest(productId), listener, Product::class.java)
    }

    fun <T> makeRequest(request: Request, listener: Listener<T>, type: Type) {
        val callback = HttpCallback(listener, type)
        client.newCall(request).enqueue(callback)
    }

    inner class HttpCallback<T>(val listener: Listener<T>, val type: Type) : Callback {

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
            listener.onResponse(response)
        }

        private fun onFailure() {
            listener.onFailure()
        }
    }
}

interface Listener<T> {
    fun onResponse(response: T)
    fun onFailure()
}
