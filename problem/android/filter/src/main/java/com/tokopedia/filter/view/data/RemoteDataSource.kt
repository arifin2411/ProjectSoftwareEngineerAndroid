package com.tokopedia.filter.view.data

import com.tokopedia.filter.view.data.response.ProductResponse
import com.tokopedia.filter.view.utils.JsonHelper

interface RemoteDataSource {

    val jsonHelper: JsonHelper

    fun getProduct(cities: List<String>?, priceMin: Int?, priceMax:Int?, page: Int, callback: LoadCallback)
    fun getAllProduct(callback: LoadCallback)

    interface LoadCallback {
        fun onAllProductReceived(productResponses: ProductResponse)
    }
}