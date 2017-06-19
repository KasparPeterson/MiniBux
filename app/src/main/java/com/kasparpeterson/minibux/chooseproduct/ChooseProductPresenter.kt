package com.kasparpeterson.minibux.chooseproduct

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
        onView { view -> view.startDetailsActivity(product) }
    }

    override fun onProductsLoaded(products: List<Product>) {
        onView { view -> view.showProducts(products) }
    }

    override fun onProductsLoadFailed() {
        onView { view -> view.showError() }
    }
}