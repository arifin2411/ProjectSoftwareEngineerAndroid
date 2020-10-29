package com.tokopedia.filter.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tokopedia.filter.view.data.ProductDataSource
import com.tokopedia.filter.view.data.entity.Product
import com.tokopedia.filter.view.utils.Resource
import com.tokopedia.filter.view.utils.setError
import com.tokopedia.filter.view.utils.setLoading
import com.tokopedia.filter.view.utils.setSuccess

class ProductViewModel(
    private val productRepository: ProductDataSource,
    val productLiveData: MutableLiveData<Resource<List<Product>>> = MutableLiveData<Resource<List<Product>>>(),
    val allProductLiveData: MutableLiveData<Resource<List<Product>>> = MutableLiveData<Resource<List<Product>>>()
) : ViewModel() {

    fun getProduct(cities: List<String>?, priceMin: Int?, priceMax:Int?, page: Int): LiveData<Resource<List<Product>>> {
        productLiveData.setLoading()

        try {
            val products = productRepository.getProduct(cities, priceMin, priceMax, page)
            productLiveData.setSuccess(products)
        } catch (ex: Exception) {
            productLiveData.setError(ex)
        }

        return productLiveData
    }

    fun getAllProduct(): LiveData<Resource<List<Product>>> {
        allProductLiveData.setLoading()

        try {
            val products = productRepository.getAllProduct()
            allProductLiveData.setSuccess(products)
        } catch (ex: Exception) {
            allProductLiveData.setError(ex)
        }

        return allProductLiveData
    }


}