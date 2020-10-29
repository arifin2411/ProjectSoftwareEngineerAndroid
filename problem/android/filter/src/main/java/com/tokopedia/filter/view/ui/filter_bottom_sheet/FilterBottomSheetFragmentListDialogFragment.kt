package com.tokopedia.filter.view.ui.filter_bottom_sheet

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tokopedia.filter.R
import com.tokopedia.filter.view.ui.ProductActivity
import com.tokopedia.filter.view.data.entity.*
import com.tokopedia.filter.view.ui.ProductViewModel
import com.tokopedia.filter.view.utils.Resource
import com.tokopedia.filter.view.utils.ResourceState
import com.tokopedia.filter.view.utils.StringUtils
import com.tokopedia.filter.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.fragment_setting_bottom_sheet_list_dialog_list_dialog.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class FilterBottomSheetFragmentListDialogFragment() :
        BottomSheetDialogFragment(),
        View.OnClickListener {

    private lateinit var viewModel: ProductViewModel
    private var products: List<Product> = emptyList()
    private val sortAdapter = SortAdapter()
    private var cityWithSizesSorted: Map<String, Int> = emptyMap()
    var selectedFilter: SelectedFilter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this,
                ViewModelFactory.getInstance(activity!!))[ProductViewModel::class.java]
        viewModel.getAllProduct().observe(this, Observer(::handleLoadData))
        return inflater.inflate(R.layout.fragment_setting_bottom_sheet_list_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val bottomSheet = dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.peekHeight = 0
                behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            dismiss()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    }
                })
            }
        }
        btn_filter.setOnClickListener(this)
    }

    private fun handleLoadData(result: Resource<List<Product>>) {
        when (result.state) {
            ResourceState.LOADING -> {

            }
            ResourceState.SUCCESS -> {
                result.data.let {
                    if (it != null) {
                        products = it
                        filterLocation()
                        filterPrice()
                    }
                }
            }
            ResourceState.ERROR -> {
                Toast.makeText(activity," ${result.throwable}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun filterLocation() {
        val cityWithSizes = products.groupBy { it.shop.city }.mapValues { it.value.size }
        cityWithSizesSorted = cityWithSizes.toList().sortedBy { it.second }.reversed().toMap()

        val filter = ArrayList<LocationFilter>()
        val shownLocation = 2
        filter.addAll(
            cityWithSizesSorted.keys.take(shownLocation).mapIndexed { index, s ->
                LocationFilter(index + 1, listOf(s), false)
            }
        )
        filter.add(LocationFilter(3, cityWithSizesSorted.keys.drop(shownLocation), false))

        val updatedFilter =
            filter.map {
                if (selectedFilter?.location != null &&
                    it.id == selectedFilter!!.location!!.filterId) {
                        it.copy(isSelected = true)
                } else {
                    it.copy(isSelected = false)
                }
            }

        sortAdapter.setProductLocation(updatedFilter)

        with(rv_location) {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = sortAdapter
        }
    }

    private fun filterPrice() {
        rs_price.valueFrom = products.minBy { it.priceInt }?.priceInt?.toFloat()?: 0.toFloat()
        rs_price.valueTo = products.maxBy { it.priceInt }?.priceInt?.toFloat() ?: 0.toFloat()

        rs_price.values = if (selectedFilter != null) {
                listOf(selectedFilter!!.price.min, selectedFilter!!.price.max)
            } else {
                listOf(rs_price.valueFrom, rs_price.valueTo)
            }

        tv_price_min.text = StringUtils.getRpCurrency(rs_price.values[0].toLong())
        tv_price_max.text = StringUtils.getRpCurrency(rs_price.values[1].toLong())

        rs_price.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance(IDR)
            format.format(value.toDouble())
        }
        rs_price.addOnChangeListener { rangeSlider, value, fromUser ->
            tv_price_min.text = StringUtils.getRpCurrency(rangeSlider.values[0].toLong())
            tv_price_max.text = StringUtils.getRpCurrency(rangeSlider.values[1].toLong())
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_filter -> {
                val productActivity: ProductActivity = activity as ProductActivity

                val dataSelection = sortAdapter.getProductLocation()
                val locationResult =
                    dataSelection.find { it.isSelected }?.cities?.let { itCities ->
                        dataSelection.find { it.isSelected }?.id?.let { itId ->
                            LocationResult(
                                    itId,
                                    itCities)
                        }
                    }
                val priceResult = PriceResult(rs_price.values[0], rs_price.values[1])

                productActivity.setFilter(SelectedFilter(locationResult, priceResult))
                dismiss()
            }
        }
    }

    companion object {
        const val IDR = "IDR"
    }
}
