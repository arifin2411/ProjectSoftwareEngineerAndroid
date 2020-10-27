package com.tokopedia.filter.view.ui.filter_bottom_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.filter.R
import com.tokopedia.filter.view.data.entity.LocationFilter
import kotlinx.android.synthetic.main.sort_item.view.*

class SortAdapter : RecyclerView.Adapter<SortAdapter.SortViewHolder>() {

    private var listProductLocation = ArrayList<LocationFilter>()

    fun setProductLocation(products: List<LocationFilter>?) {
        if (products == null) return
        this.listProductLocation.clear()
        this.listProductLocation.addAll(products)
        notifyDataSetChanged()
    }

    fun getProductLocation(): ArrayList<LocationFilter> {
        return listProductLocation
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
        fun bind(filter: LocationFilter) {
            with(itemView) {
                tv_sort_title.text = filter.getName()
                if (filter.isSelected) {
                    itemView.cv_sort_title?.setBackgroundResource(R.drawable.bg_choice_chips_green)
                } else {
                    itemView.cv_sort_title?.setBackgroundResource(R.drawable.bg_choise_chips_grey)
                }
                setOnClickListener {
                    listProductLocation = listProductLocation.map {
                        val selected = if (filter.id == it.id) !it.isSelected else false
                        it.copy(isSelected = selected)
                    }.toCollection(ArrayList())
                    notifyDataSetChanged()
                }
            }
        }
    }
}