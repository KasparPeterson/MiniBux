package com.kasparpeterson.minibux.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kasparpeterson.minibux.MiniBux
import com.kasparpeterson.minibux.chooseproduct.Product
import com.kasparpeterson.minibux.details.view.DetailsView
import com.kasparpeterson.simplemvp.MVPBaseActivity
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsActivity: MVPBaseActivity<DetailsMVP.PresenterViewOperations>(),
        DetailsMVP.ViewOperations {

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
        val model = DetailsModel(MiniBux.instance.buxWebClient, MiniBux.instance.productService)
        return DetailsPresenter(product, this, model)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = DetailsView(this)
        setContentView(view)
    }

    override fun showProduct(product: Product) {
        view.post { view.showProduct(product) }
    }

    override fun updatePrice(price: BigDecimal) {
        view.updatePrice(price)
    }

    override fun showError() {

    }
}