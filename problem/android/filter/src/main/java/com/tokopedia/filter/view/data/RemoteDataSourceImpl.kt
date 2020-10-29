package com.tokopedia.filter.view.data
import com.tokopedia.filter.view.utils.JsonHelper

class RemoteDataSourceImpl private constructor(override val jsonHelper: JsonHelper) : RemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSourceImpl(helper)
            }
    }

    override fun getAllProduct(callback: RemoteDataSource.LoadCallback) {
        callback.onAllProductReceived(jsonHelper.loadAllDataDummy())
    }

    override fun getProduct(cities: List<String>?, priceMin: Int?, priceMax:Int?, page: Int, callback: RemoteDataSource.LoadCallback) {
        callback.onAllProductReceived(jsonHelper.loadDataDummy(cities, priceMin, priceMax, page))
    }

}