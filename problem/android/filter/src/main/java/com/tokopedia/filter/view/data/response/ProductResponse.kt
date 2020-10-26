package com.tokopedia.filter.view.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ProductResponse(
    @SerializedName("data")
    var data: Data
)

data class Data (
    @SerializedName("products")
    val products: List<Products>
)

data class Products(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("imageUrl")
    var imageUrl: String,
    @SerializedName("priceInt")
    var priceInt: Int,
    @SerializedName("discountPercentage")
    var discountPercentage: Int,
    @SerializedName("slashedPriceInt")
    var slashedPriceInt: Int,
    @SerializedName("shop")
    var shop: Shop
)

data class Shop(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("city")
    var city: String
)