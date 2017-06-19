package com.kasparpeterson.minibux.chooseproduct

import com.kasparpeterson.simplemvp.MVPBaseModel
import com.kasparpeterson.simplemvp.MVPBasePresenter
import com.kasparpeterson.simplemvp.MVPBasePresenterModelOperations
import com.kasparpeterson.simplemvp.MVPBaseViewOperations

/**
 * Created by kaspar on 15/06/2017.
 */
interface ChooseProductMVP {

    // Presenter -> View
    interface ViewOperations : MVPBaseViewOperations {
        fun showProducts(products: List<Product>)
        fun showError()
        fun startDetailsActivity(product: Product)
    }

    // View -> Presenter
    abstract class PresenterViewOperations(view: ViewOperations, model: ModelOperations)
        : MVPBasePresenter<ViewOperations, ModelOperations>(view, model) {

        companion object {
            val TAG = PresenterViewOperations::class.java.simpleName
        }

        abstract fun onProductChosen(product: Product)
    }

    // Model -> Presenter
    interface PresenterModelOperations : MVPBasePresenterModelOperations {
        fun onProductsLoaded(products: List<Product>)
        fun onProductsLoadFailed()
    }

    // Presenter -> Model
    abstract class ModelOperations : MVPBaseModel<PresenterModelOperations>() {
        abstract fun loadProducts()
    }

}