package com.kasparpeterson.minibux.api

import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.api.rest.ProductManager
import com.kasparpeterson.minibux.api.rest.ProductService
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

/**
 * Created by kaspar on 21/06/2017.
 */
class ProductManagerTest {

    val productId = "productId"

    lateinit var productCall: Call<Product>
    lateinit var productsCall: Call<List<Product>>
    lateinit var service: ProductService
    lateinit var manager: ProductManager

    @Before
    fun setUp() {
        productCall = mock()
        productsCall = mock()
        service = mock {
            on { getProduct(any(), any()) } doReturn productCall
            on { getProducts(any()) } doReturn productsCall
        }
        manager = ProductManager(service)
    }

    @Test
    fun fetchProduct() {
        manager.fetchProduct(productId, mock())
        verify(productCall).enqueue(any())
    }

    @Test
    fun fetchProducts() {
        manager.fetchProducts(mock())
        verify(productsCall).enqueue(any())
    }
}