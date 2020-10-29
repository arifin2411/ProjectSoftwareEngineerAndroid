package com.tokopedia.filter.view.data

import com.tokopedia.filter.view.data.response.ProductResponse
import com.tokopedia.filter.view.utils.JsonHelper

interface RemoteDataSource {

    val jsonHelper: JsonHelper

    fun getAllProduct(callback: LoadCoursesCallback)

    interface LoadCoursesCallback {
        fun onAllProductReceived(productResponses: ProductResponse)
    }
}