package com.tokopedia.filter.view.data

import com.tokopedia.filter.view.data.response.ProductResponse
import com.tokopedia.filter.view.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource(helper)
                }
    }

    fun getAllProduct(callback: LoadCoursesCallback) {
         callback.onAllProductReceived(jsonHelper.loadDataDummy())
    }

    interface LoadCoursesCallback {
        fun onAllProductReceived(productResponses: ProductResponse)
    }
}