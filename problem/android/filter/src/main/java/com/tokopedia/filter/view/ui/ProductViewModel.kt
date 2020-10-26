package com.tokopedia.filter.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tokopedia.filter.view.data.ProductRepository
import com.tokopedia.filter.view.ui.entity.Product
import com.tokopedia.filter.view.utils.Resource
import com.tokopedia.filter.view.utils.setError
import com.tokopedia.filter.view.utils.setLoading
import com.tokopedia.filter.view.utils.setSuccess

class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    val productLiveData = MutableLiveData<Resource<List<Product>>>()

    fun getProduct(): LiveData<Resource<List<Product>>> {
        productLiveData.setLoading()

        try {
            val products = productRepository.getProduct()
            productLiveData.setSuccess(products)
        } catch (ex: Exception) {
            productLiveData.setError(ex)
        }

        return productLiveData
    }
}