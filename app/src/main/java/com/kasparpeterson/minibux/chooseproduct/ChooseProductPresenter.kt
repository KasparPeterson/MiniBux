package com.kasparpeterson.minibux.chooseproduct

import com.kasparpeterson.minibux.api.models.Product

/**
 * Created by kaspar on 15/06/2017.
 */
class ChooseProductPresenter(view: ChooseProductMVP.ViewOperations,
                             model: ChooseProductMVP.ModelOperations)
    : ChooseProductMVP.PresenterViewOperations(view, model),
        ChooseProductMVP.PresenterModelOperations {

    override fun onStart() {
        super.onStart()
        model.loadProducts()
    }

    override fun onProductChosen(product: Product) {
        onView { it.startDetailsActivity(product) }
    }

    override fun onRetry() {
        model.loadProducts()
    }

    override fun onProductsLoaded(products: List<Product>) {
        onView { it.showProducts(products) }
    }

    override fun onProductsLoadFailed() {
        onView { it.showError() }
    }
}