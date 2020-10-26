package com.tokopedia.filter.view.data.entity

data class Product(
    var id: Int,
    var name: String,
    var imageUrl: String,
    var priceInt: Int,
    var discountPercentage: Int,
    var slashedPriceInt: Int,
    var shop: Shop
)

data class Shop(
    var id: Int,
    var name: String,
    var city: String
)