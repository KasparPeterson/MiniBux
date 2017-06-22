package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.api.models.TradingQuote
import com.kasparpeterson.minibux.details.view.DetailsViewState
import com.kasparpeterson.simplemvp.MVPBaseModel
import com.kasparpeterson.simplemvp.MVPBasePresenter
import com.kasparpeterson.simplemvp.MVPBasePresenterModelOperations
import com.kasparpeterson.simplemvp.MVPBaseViewOperations

/**
 * Created by kaspar on 13/06/2017.
 */
interface DetailsMVP {

    // Presenter -> View
    interface ViewOperations : MVPBaseViewOperations {
        fun showState(state: DetailsViewState)
    }

    // View -> Presenter
    abstract class PresenterViewOperations(view: ViewOperations, model: ModelOperations)
        : MVPBasePresenter<ViewOperations, ModelOperations>(view, model) {

        companion object {
            val TAG = PresenterViewOperations::class.java.simpleName
        }

        abstract fun onRetry()
    }

    // Model -> Presenter
    interface PresenterModelOperations : MVPBasePresenterModelOperations {
        fun onProductFetched(product: Product)
        fun onProductFetchFailed()
        fun onTradingQuoteUpdate(tradingQuote: TradingQuote)
        fun onTradingQuoteUnAvailable()
    }

    // Presenter -> Model
    abstract class ModelOperations : MVPBaseModel<PresenterModelOperations>() {
        abstract fun fetchProduct(securityId: String)
        abstract fun startListening(securityId: String)
    }
}