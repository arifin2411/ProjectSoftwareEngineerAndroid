package com.tokopedia.filter.view.ui.filter_bottom_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.filter.R
import com.tokopedia.filter.view.ui.entity.Filter
import kotlinx.android.synthetic.main.sort_item.view.*
import java.util.ArrayList

class SortAdapter : RecyclerView.Adapter<SortAdapter.SortViewHolder>() {

    private var listProductLocation = ArrayList<Filter>()
    private var recyclerView: RecyclerView? = null

    fun setRecyclerView(recyclerView: RecyclerView?) {
        this.recyclerView = recyclerView
    }

    fun setProductLocation(products: List<Filter>?) {
        if (products == null) return
        this.listProductLocation.clear()
        this.listProductLocation.addAll(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortAdapter.SortViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.sort_item, parent, false)
        return SortViewHolder(view)
    }

    override fun getItemCount(): Int = listProductLocation.size

    override fun onBindViewHolder(holder: SortAdapter.SortViewHolder, position: Int) {
        val product = listProductLocation[position]
        holder.bind(product)
    }

    inner class SortViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(filter: Filter) {
            with(itemView) {
                tv_sort_title.text = filter.name
                setOnClickListener {
                    itemView.cv_sort_title?.setBackgroundResource(R.drawable.bg_choice_chips)
                }
            }
        }
    }
}