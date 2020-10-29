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
import com.tokopedia.filter.view.utils.EndlessOnScrollListener
import com.tokopedia.filter.view.utils.Resource
import com.tokopedia.filter.view.utils.ResourceState
import com.tokopedia.filter.view.utils.ext.hide
import com.tokopedia.filter.view.utils.ext.show
import com.tokopedia.filter.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.product_load_more.*

class ProductActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: ProductViewModel
    private val productAdapter: ProductAdapter = ProductAdapter()
    private var selectedFilter: SelectedFilter? = null
    private var PAGE: Int = 1

    private val filterBottomSheet by lazy {
        FilterBottomSheetFragmentListDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        fab_filter.setOnClickListener(this)

        viewModel = ViewModelProvider(this,
                ViewModelFactory.getInstance(this))[ProductViewModel::class.java]
        viewModel.getProduct(
            selectedFilter?.location?.cities,
            selectedFilter?.price?.min?.toInt(),
            selectedFilter?.price?.max?.toInt(),
            PAGE
        ).observe(this, Observer(::handleLoadData))
        PAGE += 1

        with(product_list) {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = productAdapter
        }

        product_list.addOnScrollListener(scrollData(PAGE)!!)
    }

    private fun scrollData(page: Int?): EndlessOnScrollListener? {
        return object : EndlessOnScrollListener() {
            override fun onLoadMore() {
                viewModel.getProduct(
                    selectedFilter?.location?.cities,
                    selectedFilter?.price?.min?.toInt(),
                    selectedFilter?.price?.max?.toInt(),
                    PAGE ?: 1)
                PAGE += 1
            }
        }
    }

    private fun handleLoadData(result: Resource<List<Product>>) {
        when (result.state) {
            ResourceState.LOADING -> {
                swipe_refresh_layout.isRefreshing = true
                load_more_indicator.show()
            }
            ResourceState.SUCCESS -> {
                swipe_refresh_layout.isRefreshing = false
                load_more_indicator.hide()
                result.data.let {
                    productAdapter.appendProduct(it ?: emptyList())
                }
            }
            ResourceState.ERROR -> {
                swipe_refresh_layout.isRefreshing = false
                load_more_indicator.hide()
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
                refreshData()
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

    fun setFilter(selectedFilter: SelectedFilter) {
        this.selectedFilter = selectedFilter
        productAdapter.clearData()
        viewModel.getProduct(
            selectedFilter.location?.cities,
            selectedFilter.price.min.toInt(),
            selectedFilter.price.max.toInt(),
            1
        )
        PAGE = 2

        product_list.clearOnScrollListeners()
        product_list.addOnScrollListener(scrollData(PAGE)!!)
    }

    private fun refreshData() {
        productAdapter.clearData()
        viewModel.getProduct(
            selectedFilter?.location?.cities,
            selectedFilter?.price?.min?.toInt(),
            selectedFilter?.price?.max?.toInt(),
            1
        )
        PAGE = 2
        product_list.clearOnScrollListeners()
        product_list.addOnScrollListener(scrollData(PAGE)!!)
    }
}