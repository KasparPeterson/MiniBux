package com.kasparpeterson.minibux.chooseproduct

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by kaspar on 16/06/2017.
 */
class ChooseProductPresenterTest {

    val product = Product("MockName", "mockid", Price(), "category")
    val products = listOf(product)
    lateinit var view: ChooseProductMVP.ViewOperations
    lateinit var model: ChooseProductMVP.ModelOperations
    lateinit var presenter: ChooseProductPresenter

    @Before
    fun setUp() {
        view = mock<ChooseProductMVP.ViewOperations>()
        model = mock<ChooseProductMVP.ModelOperations>()
        presenter = ChooseProductPresenter(view, model)
        presenter.onStart()
        presenter.onResume()
    }

    @Test
    fun onStart() {
        verify(model).loadProducts()
    }

    @Test
    fun onProductChosen() {
        presenter.onProductChosen(product)
        verify(view).startDetailsActivity(product)
    }

    @Test
    fun onProductsLoaded() {
        presenter.onProductsLoaded(products)
        verify(view).showProducts(products)
    }

    @Test
    fun onProductsLoadFailed() {
        presenter.onProductsLoadFailed()
        verify(view).showError()
    }

}