package com.tokopedia.filter.view.data

import com.tokopedia.filter.view.data.entity.Product

interface ProductDataSource {

    fun getProduct(): List<Product>
}