package com.kasparpeterson.minibux.api

import com.kasparpeterson.minibux.api.models.Price
import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.api.rest.HttpCallback
import com.kasparpeterson.minibux.api.rest.HttpListener
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Created by kaspar on 21/06/2017.
 */
class HttpCallbackTest {

    lateinit var listener: HttpListener<Product>
    lateinit var callback: HttpCallback<Product>

    @Before
    fun setUp() {
        listener = mock()
        callback = HttpCallback(listener)
    }

    @Test
    fun onResponse() {
        val product = Product("", "", Price(), "")
        val response = Response.success(product)
        callback.onResponse(null, response)
        verify(listener).onResponse(product)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun onResponse_notSuccessful() {
        val response = Response.success(null) as Response<Product>
        callback.onResponse(null, response)
        verify(listener).onFailure()
    }

    @Test
    fun onFailure() {
        callback.onFailure(null, null)
        verify(listener).onFailure()
    }

}