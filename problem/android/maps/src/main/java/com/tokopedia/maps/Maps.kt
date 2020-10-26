package com.tokopedia.maps

import com.google.gson.annotations.SerializedName

data class Maps(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("capital")
    var capital: String? = null,
    @SerializedName("population")
    var population: String? = null,
    @SerializedName("callingCodes")
    var callingCodes: List<String>? = null,
    @SerializedName("latlng")
    var latlng: List<Int>? = null

)