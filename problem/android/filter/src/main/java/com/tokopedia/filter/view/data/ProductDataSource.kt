package com.tokopedia.filter.view.data

import com.tokopedia.filter.view.data.entity.Product

interface ProductDataSource {

    fun getAllProduct(): List<Product>
    fun getProduct(cities: List<String>?, priceMin: Int?, priceMax:Int?,page: Int): List<Product>
}