package com.tokopedia.filter.view.utils

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.tokopedia.filter.view.data.entity.Product
import com.tokopedia.filter.view.data.response.Data
import com.tokopedia.filter.view.data.response.ProductResponse
import org.json.JSONException
import java.io.IOException
import java.lang.Exception
import java.util.*

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadDataDummy(cities: List<String>?, priceMin: Int?, priceMax:Int?, page: Int): ProductResponse {
        val jsonString = parsingFileToString("products.json")
        val productResponse = Gson().fromJson(jsonString, (ProductResponse::class).java)
        val products = productResponse.data.products
        val filterByCity = if (cities != null && cities.isNotEmpty()) products.filter {
            cities.contains(it.shop.city)
        } else products
        val filterByPrice = if (priceMin != null && priceMax != null) filterByCity.filter { it.priceInt <= priceMax && it.priceInt >= priceMin } else filterByCity
        val listProduct = filterByPrice.drop((page - 1) * 10).take(10)
        return ProductResponse(Data(listProduct))
    }

    fun loadAllDataDummy(): ProductResponse {
        val jsonString = parsingFileToString("products.json")
        val productResponse = Gson().fromJson(jsonString, (ProductResponse::class).java)

        return productResponse
    }
}