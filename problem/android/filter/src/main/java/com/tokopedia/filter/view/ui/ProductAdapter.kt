package com.tokopedia.filter.view.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tokopedia.filter.R
import com.tokopedia.filter.view.ui.entity.Product
import com.tokopedia.filter.view.utils.StringUtils
import kotlinx.android.synthetic.main.product_item.view.*
import java.util.*

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var listProduct = ArrayList<Product>()

    fun setProduct(products: List<Product>?) {
        if (products == null) return
        this.listProduct.clear()
        this.listProduct.addAll(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val view =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.product_item, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val product = listProduct[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = listProduct.size

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            with(itemView) {
                tv_product_name.text = product.name
                tv_price.text = StringUtils.getRpCurrency(product.priceInt.toLong())
                tv_city.text = product.shop.city
                Glide.with(itemView.context)
                    .load(product.imageUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                    .into(iv_picture)

            }
        }
    }
}