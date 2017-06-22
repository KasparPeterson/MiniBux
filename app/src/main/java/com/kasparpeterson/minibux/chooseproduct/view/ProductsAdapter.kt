package com.kasparpeterson.minibux.chooseproduct.view

import android.view.View
import android.widget.TextView
import com.kasparpeterson.minibux.R
import com.kasparpeterson.minibux.api.models.Price
import com.kasparpeterson.minibux.api.models.Product
import com.kasparpeterson.simplerecyclerview.SimpleAdapterItem
import com.kasparpeterson.simplerecyclerview.SimpleRecyclerViewAdapter

/**
 * Created by kaspar on 16/06/2017.
 */
interface ProductListener {
    fun onProductChosen(product: Product)
}

class ProductsAdapter(listener: ProductListener)
    : SimpleRecyclerViewAdapter<ProductListener>(listener) {

    override fun setAdapterItems(adapterItems: MutableList<SimpleAdapterItem>) {
        super.setAdapterItems(adapterItems)
    }
}

class ContentItem(val product: Product)
    : SimpleAdapterItem(0, R.layout.list_item_choose_product_content, ContentViewHolder::class.java)

class ContentViewHolder(val view: View)
    : SimpleRecyclerViewAdapter.SimpleViewHolder<ContentItem, ProductListener>(view) {

    val productTextView = view.findViewById(R.id.list_item_product_text_view) as TextView
    val priceTextView = view.findViewById(R.id.list_item_product_price_value_text_view) as TextView
    val categoryTextView = view.findViewById(R.id.list_item_product_category_value_text_view) as TextView

    override fun onBind(item: ContentItem, listener: ProductListener) {
        showProduct(item.product)
        view.setOnClickListener { listener.onProductChosen(item.product) }
    }

    private fun showProduct(product: Product) {
        productTextView.text = product.displayName
        priceTextView.text = formatPrice(product.currentPrice)
        categoryTextView.text = product.category
    }

    private fun formatPrice(price: Price): String {
        return price.amount.toString() + price.currency
    }
}
