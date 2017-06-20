package com.kasparpeterson.minibux.details.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.kasparpeterson.minibux.R
import com.kasparpeterson.minibux.chooseproduct.Price
import com.kasparpeterson.minibux.chooseproduct.Product
import kotlinx.android.synthetic.main.view_details.view.*
import java.math.BigDecimal

/**
 * Created by kaspar on 17/06/2017.
 */
interface DetailsListener {
    fun onRetry()
}

data class DetailsViewState(
        val product: Product,
        val isProductError: Boolean = false,
        val isPriceError: Boolean = false)

@SuppressLint("ViewConstructor")
class DetailsView(context: Context, val listener: DetailsListener): FrameLayout(context) {

    init {
        inflate(context, R.layout.view_details, this)
        details_retry_button.setOnClickListener { listener.onRetry() }
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
        val formattedPrice = price.amount.toString()
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
        } else {
            details_product_price_error_text_view.visibility = GONE
        }
    }
}