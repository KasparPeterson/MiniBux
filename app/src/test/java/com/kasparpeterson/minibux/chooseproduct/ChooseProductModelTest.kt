package com.kasparpeterson.minibux.chooseproduct

import com.kasparpeterson.minibux.api.Listener
import com.kasparpeterson.minibux.api.ProductService
import com.nhaarman.mockito_kotlin.*
import org.junit.Test

import org.junit.Before

/**
 * Created by kaspar on 16/06/2017.
 */
class ChooseProductModelTest {

    lateinit var products: List<Product>
    lateinit var productService: ProductService
    lateinit var presenter: ChooseProductMVP.PresenterModelOperations
    lateinit var model: ChooseProductModel

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
                category = "Category")
        products = listOf(product1)
    }

    private fun createMocks() {
        productService = mock<ProductService>()
        presenter = mock<ChooseProductMVP.PresenterModelOperations>()
        model = ChooseProductModel(productService)
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

    private fun provideProductServiceAnswer(callback: (Listener<List<Product>>) -> Unit) {
        doAnswer { invocation ->
            callback.invoke(invocation.arguments[0] as Listener<List<Product>>)
            null
        }.`when`(productService).fetchProducts(any())
    }
}