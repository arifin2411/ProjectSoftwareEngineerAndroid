package com.tokopedia.maps

import com.google.gson.annotations.SerializedName

data class Maps(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("capital")
    var capital: String? = "",
    @SerializedName("population")
    var population: String? = "",
    @SerializedName("callingCodes")
    var callingCodes: List<String>? = null,
    @SerializedName("latlng")
    var latlng: List<Int>? = null

)