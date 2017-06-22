package com.kasparpeterson.minibux.chooseproduct

import com.kasparpeterson.minibux.api.rest.ProductManager
import com.kasparpeterson.minibux.api.models.Price
import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.api.rest.HttpListener
import com.nhaarman.mockito_kotlin.*
import org.junit.Test
import org.junit.Before

/**
 * Created by kaspar on 16/06/2017.
 */
class ChooseProductModelTest {

    lateinit var products: List<Product>
    lateinit var productManager: ProductManager
    lateinit var presenter: ChooseProductMVP.PresenterModelOperations
    lateinit var model: ChooseHttpModel

    @Before
    fun setUp() {
        createProducts()
        createMocks()
    }

    private fun createProducts() {
        val product1 = Product(
                displayName = "Product1",
                securityId = "ID",
                currentPrice = Price(),
                closingPrice = Price(),
                category = "Category")
        products = listOf(product1)
    }

    private fun createMocks() {
        productManager = mock()
        presenter = mock()
        model = ChooseHttpModel(productManager)
        model.presenter = presenter
    }

    @Test
    fun loadProducts_success() {
        provideProductServiceSuccess()
        model.loadProducts()
        verify(presenter).onProductsLoaded(products)
    }

    @Test
    fun loadProducts_failure() {
        provideProductServiceFailure()
        model.loadProducts()
        verify(presenter).onProductsLoadFailed()
    }

    private fun provideProductServiceSuccess() {
        provideProductServiceAnswer { it.onResponse(products) }
    }

    private fun provideProductServiceFailure() {
        provideProductServiceAnswer { it.onFailure() }
    }

    @Suppress("UNCHECKED_CAST")
    private fun provideProductServiceAnswer(callback: (HttpListener<List<Product>>) -> Unit) {
        doAnswer { invocation ->
            callback.invoke(invocation.arguments[0] as HttpListener<List<Product>>)
            null
        }.`when`(productManager).fetchProducts(any())
    }
}