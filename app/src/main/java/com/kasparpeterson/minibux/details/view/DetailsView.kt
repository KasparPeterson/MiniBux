package com.kasparpeterson.minibux.details.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.kasparpeterson.minibux.R
import com.kasparpeterson.minibux.api.models.Price
import com.kasparpeterson.minibux.api.models.Product
import kotlinx.android.synthetic.main.view_details.view.*

/**
 * Created by kaspar on 17/06/2017.
 */
@SuppressLint("ViewConstructor")
class DetailsView(context: Context, val listener: DetailsListener): FrameLayout(context) {

    init {
        inflate(context, R.layout.view_details, this)
        details_retry_button.setOnClickListener { listener.onRetry() }
        details_price_retry_button.setOnClickListener { listener.onRetry() }
    }

    fun showState(state: DetailsViewState) {
        showProduct(state.product)
        updatePrice(state.product.currentPrice)
        handleProductError(state.isProductError)
        handlePriceError(state.isPriceError)
    }

    private fun showProduct(product: Product) {
        details_product_name_text_view.text = product.displayName
        details_product_description_text_view.text = product.description

    }

    private fun updatePrice(price: Price) {
        val formattedPrice = price.getFormattedPresentation()
        details_product_price_text_view.text = formattedPrice
    }

    private fun handleProductError(isError: Boolean) {
        if (isError) {
            details_error_container.visibility = VISIBLE
        } else {
            details_error_container.visibility = View.GONE
        }
    }

    private fun handlePriceError(isError: Boolean) {
        if (isError) {
            details_product_price_error_text_view.visibility = VISIBLE
            details_price_retry_button.visibility = VISIBLE
        } else {
            details_product_price_error_text_view.visibility = GONE
            details_price_retry_button.visibility = GONE
        }
    }
}