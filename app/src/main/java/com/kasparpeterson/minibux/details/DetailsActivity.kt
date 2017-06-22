package com.kasparpeterson.minibux.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kasparpeterson.minibux.MiniBux
import com.kasparpeterson.minibux.chooseproduct.Product
import com.kasparpeterson.minibux.details.view.DetailsListener
import com.kasparpeterson.minibux.details.view.DetailsView
import com.kasparpeterson.minibux.details.view.DetailsViewState
import com.kasparpeterson.simplemvp.MVPBaseActivity

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsActivity: MVPBaseActivity<DetailsMVP.PresenterViewOperations>(),
        DetailsMVP.ViewOperations, DetailsListener {

    companion object {
        private val ARG_PRODUCT = "arg_product"

        fun getIntent(context: Context, product: Product): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(ARG_PRODUCT, MiniBux.gson.toJson(product))
            return intent
        }
    }

    private lateinit var view: DetailsView

    override fun callSetupPresenter() {
        setupPresenter(this, DetailsActivity::class.java.simpleName,
                DetailsMVP.PresenterViewOperations.TAG)
    }

    override fun initialisePresenter(): DetailsMVP.PresenterViewOperations {
        val product = MiniBux.gson.fromJson(intent.getStringExtra(ARG_PRODUCT), Product::class.java)
        val model = DetailsModel(MiniBux.instance.buxWebSocketClient, MiniBux.instance.productManager)
        return DetailsPresenter(product, this, model)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = DetailsView(this, this)
        setContentView(view)
    }

    override fun onRetry() {
        presenter.onRetry()
    }

    override fun showState(state: DetailsViewState) {
        view.post { view.showState(state) }
    }
}