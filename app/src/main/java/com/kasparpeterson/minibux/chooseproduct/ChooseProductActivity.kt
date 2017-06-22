package com.kasparpeterson.minibux.chooseproduct

import android.os.Bundle
import com.kasparpeterson.minibux.MiniBux
import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.minibux.chooseproduct.view.ChooseProductListener
import com.kasparpeterson.minibux.chooseproduct.view.ChooseProductView
import com.kasparpeterson.minibux.details.DetailsActivity
import com.kasparpeterson.simplemvp.MVPBaseActivity

/**
 * Created by kaspar on 15/06/2017.
 */
class ChooseProductActivity: MVPBaseActivity<ChooseProductMVP.PresenterViewOperations>(),
        ChooseProductMVP.ViewOperations, ChooseProductListener {

    val TAG = ChooseProductActivity::class.java.simpleName

    private lateinit var view: ChooseProductView

    override fun callSetupPresenter() {
        setupPresenter(this, TAG, ChooseProductMVP.PresenterViewOperations.TAG)
    }

    override fun initialisePresenter(): ChooseProductMVP.PresenterViewOperations {
        return ChooseProductPresenter(this,
                ChooseHttpModel(MiniBux.instance.productManager))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ChooseProductView(this, this)
        setContentView(view)
    }

    override fun onProductChosen(product: Product) {
        presenter.onProductChosen(product)
    }

    override fun onRetry() {
        presenter.onRetry()
    }

    override fun showProducts(products: List<Product>) {
        view.post { view.showProducts(products)}
    }

    override fun showError() {
        view.post { view.showError() }
    }

    override fun startDetailsActivity(product: Product) {
        startActivity(DetailsActivity.getIntent(this, product))
    }
}