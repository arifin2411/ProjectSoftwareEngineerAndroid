package com.tokopedia.filter.view.data

import androidx.lifecycle.LiveData
import com.tokopedia.filter.view.ui.entity.Product

interface ProductDataSource {

    fun getProduct(): List<Product>
}