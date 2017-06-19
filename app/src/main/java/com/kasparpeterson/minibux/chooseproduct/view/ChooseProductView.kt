package com.kasparpeterson.minibux.chooseproduct.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.widget.FrameLayout
import com.kasparpeterson.minibux.R
import com.kasparpeterson.minibux.chooseproduct.Product
import kotlinx.android.synthetic.main.view_choose_product.view.*

/**
 * Created by kaspar on 16/06/2017.
 */
interface ChooseProductListener {
    fun onProductChosen(product: Product)
}

@SuppressLint("ViewConstructor")
class ChooseProductView(context: Context, val listener: ChooseProductListener)
    : FrameLayout(context), ProductListener {

    val adapter: ProductsAdapter

    init {
        inflate(context, R.layout.view_choose_product, this)
        adapter = ProductsAdapter(this)
        choose_product_recycler_view.layoutManager = GridLayoutManager(context, 2)
        choose_product_recycler_view.adapter = adapter
    }

    override fun onProductChosen(product: Product) {
        listener.onProductChosen(product)
    }

    fun showProducts(products: List<Product>) {
        adapter.adapterItems = products.map(::ContentItem)
    }
}