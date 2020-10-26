package com.tokopedia.filter.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.filter.R
import com.tokopedia.filter.view.ui.entity.Product
import com.tokopedia.filter.view.ui.filter_bottom_sheet.FilterBottomSheetFragmentListDialogFragment
import com.tokopedia.filter.view.utils.Resource
import com.tokopedia.filter.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity(), View.OnClickListener {

    private var listProduct: List<Product> = emptyList()

    private val filterBottomSheet by lazy {
        FilterBottomSheetFragmentListDialogFragment(listProduct)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        fab_filter.setOnClickListener(this)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        val productAdapter = ProductAdapter()
        viewModel.getProduct().observe(this, Observer { product ->
            productAdapter.setProduct(product.data)
            productAdapter.notifyDataSetChanged()
            listProduct = product.data ?: emptyList()
        })

        with(product_list) {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = productAdapter
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fab_filter -> filterBottomSheet.show(supportFragmentManager, filterBottomSheet.tag)
        }
    }
}