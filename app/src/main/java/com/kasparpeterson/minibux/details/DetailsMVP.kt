package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.TradingQuote
import com.kasparpeterson.simplemvp.MVPBaseModel
import com.kasparpeterson.simplemvp.MVPBasePresenter
import com.kasparpeterson.simplemvp.MVPBasePresenterModelOperations
import com.kasparpeterson.simplemvp.MVPBaseViewOperations
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
interface DetailsMVP {

    // Presenter -> View
    interface ViewOperations : MVPBaseViewOperations {
        fun updatePrice(price: BigDecimal)
    }

    // View -> Presenter
    abstract class PresenterViewOperations(view: ViewOperations, model: ModelOperations)
        : MVPBasePresenter<ViewOperations, ModelOperations>(view, model) {

        companion object {
            val TAG = PresenterViewOperations::class.java.simpleName
        }
    }

    // Model -> Presenter
    interface PresenterModelOperations : MVPBasePresenterModelOperations {
        fun onTradingQuoteUpdate(tradingQuote: TradingQuote)
    }

    // Presenter -> Model
    abstract class ModelOperations : MVPBaseModel<PresenterModelOperations>() {
        abstract fun startListening(securityId: String)
    }

}