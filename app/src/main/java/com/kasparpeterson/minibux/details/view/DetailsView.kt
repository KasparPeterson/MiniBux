package com.kasparpeterson.minibux.details.view

import android.content.Context
import android.widget.FrameLayout
import com.kasparpeterson.minibux.R
import com.kasparpeterson.minibux.chooseproduct.Product
import kotlinx.android.synthetic.main.view_details.view.*
import java.math.BigDecimal

/**
 * Created by kaspar on 17/06/2017.
 */
class DetailsView(context: Context): FrameLayout(context) {

    init {
        inflate(context, R.layout.view_details, this)
    }

    fun showProduct(product: Product) {
        details_product_name_text_view.text = product.displayName
        details_product_description_text_view.text = product.description
    }

    fun updatePrice(price: BigDecimal) {
        details_product_price_text_view.text = price.toString()
    }
}