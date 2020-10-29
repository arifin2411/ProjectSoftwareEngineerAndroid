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

    override fun getAllProduct(callback: RemoteDataSource.LoadCoursesCallback) {
        callback.onAllProductReceived(jsonHelper.loadDataDummy())
    }

}