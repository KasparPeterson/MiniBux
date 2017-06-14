package com.kasparpeterson.minibux.details

import android.os.Bundle
import com.kasparpeterson.minibux.MiniBux
import com.kasparpeterson.simplemvp.MVPBaseActivity
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsActivity: MVPBaseActivity<DetailsMVP.PresenterViewOperations>(),
        DetailsMVP.ViewOperations {

    override fun callSetupPresenter() {
        setupPresenter(this, DetailsActivity::class.java.simpleName,
                DetailsMVP.PresenterViewOperations.TAG)
    }

    override fun initialisePresenter(): DetailsMVP.PresenterViewOperations {
        return DetailsPresenter("TODO:MOCKID", this,
                DetailsModel(MiniBux.staticInstance!!.client!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun updatePrice(price: BigDecimal) {

    }
}