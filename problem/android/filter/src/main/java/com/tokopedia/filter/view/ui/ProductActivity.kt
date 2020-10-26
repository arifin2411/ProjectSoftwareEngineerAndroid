package com.tokopedia.filter.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.filter.R
import com.tokopedia.filter.view.data.entity.Product
import com.tokopedia.filter.view.data.entity.SelectedFilter
import com.tokopedia.filter.view.ui.filter_bottom_sheet.FilterBottomSheetFragmentListDialogFragment
import com.tokopedia.filter.view.utils.Resource
import com.tokopedia.filter.view.utils.ResourceState
import com.tokopedia.filter.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: ProductViewModel
    private val productAdapter: ProductAdapter = ProductAdapter()
    private var listProduct: List<Product> = emptyList()
    private var selectedFilter: SelectedFilter? = null

    private val filterBottomSheet by lazy {
        FilterBottomSheetFragmentListDialogFragment(listProduct)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        fab_filter.setOnClickListener(this)

        viewModel = ViewModelProvider(this,
                ViewModelFactory.getInstance(this))[ProductViewModel::class.java]
        viewModel.getProduct().observe(this, Observer(::handleLoadData))

        with(product_list) {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = productAdapter
        }
    }

    private fun handleLoadData(result: Resource<List<Product>>) {
        when (result.state) {
            ResourceState.LOADING -> {
                swipe_refresh_layout.isRefreshing = true
            }
            ResourceState.SUCCESS -> {
                swipe_refresh_layout.isRefreshing = false
                result.data.let {
                    productAdapter.setProduct(loadFiltered(it ?: emptyList()))
                    listProduct = it ?: emptyList()
                }
            }
            ResourceState.ERROR -> {
                Toast.makeText(this," ${result.throwable}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout() {
        swipe_refresh_layout?.apply {
            isEnabled = true

            setProgressViewOffset(
                false,
                resources.getDimensionPixelSize(R.dimen.dp_0),
                resources.getDimensionPixelSize(R.dimen.dp_32)
            )

            setOnRefreshListener {
                viewModel.getProduct()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fab_filter -> {
                filterBottomSheet.selectedFilter = selectedFilter
                filterBottomSheet.show(supportFragmentManager, filterBottomSheet.tag)
            }
        }
    }

    private fun loadFiltered(products: List<Product>): List<Product> {
        if (selectedFilter != null) {
            val filteredByCity =
                if (selectedFilter!!.location != null) {
                    listProduct.filter { selectedFilter!!.location!!.cities.contains(it.shop.city) }
                } else {
                    listProduct
                }

            return filteredByCity.filter {
                it.priceInt >= selectedFilter!!.price.min && it.priceInt <= selectedFilter!!.price.max
            }
        } else {
            return products
        }
    }

    fun setFilter(selectedFilter: SelectedFilter) {
        this.selectedFilter = selectedFilter
        viewModel.getProduct()
    }
}