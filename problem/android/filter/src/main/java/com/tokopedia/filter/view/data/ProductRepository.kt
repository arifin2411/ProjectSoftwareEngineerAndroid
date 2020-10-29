package com.tokopedia.filter.view.data

import com.tokopedia.filter.view.data.entity.Product
import com.tokopedia.filter.view.data.response.ProductResponse
import com.tokopedia.filter.view.data.entity.Shop

class ProductRepository constructor(private val remoteDataSource: RemoteDataSource) : ProductDataSource {
    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(remoteData: RemoteDataSource): ProductRepository =
            instance ?: synchronized(this) {
                instance ?: ProductRepository(remoteData)
            }
    }

    override fun getProduct(cities: List<String>?, priceMin: Int?, priceMax:Int?, page: Int): List<Product> {
        val productResults = ArrayList<Product>()
        remoteDataSource.getProduct(cities, priceMin, priceMax, page ,object : RemoteDataSource.LoadCallback {
            override fun onAllProductReceived(productResponses: ProductResponse) {
                for (response in productResponses.data.products) {
                    val product = Product(
                            response.id,
                            response.name,
                            response.imageUrl,
                            response.priceInt,
                            response.discountPercentage,
                            response.slashedPriceInt,
                            Shop(response.shop.id, response.shop.name, response.shop.city))
                    productResults.add(product)
                }
            }
        })
        return productResults
    }

    override fun getAllProduct(): List<Product> {
        val productResults = ArrayList<Product>()
        remoteDataSource.getAllProduct(object : RemoteDataSource.LoadCallback {
            override fun onAllProductReceived(productResponses: ProductResponse) {
                for (response in productResponses.data.products) {
                    val product = Product(
                            response.id,
                            response.name,
                            response.imageUrl,
                            response.priceInt,
                            response.discountPercentage,
                            response.slashedPriceInt,
                            Shop(response.shop.id, response.shop.name, response.shop.city))
                    productResults.add(product)
                }
            }
        })
        return productResults
    }
}