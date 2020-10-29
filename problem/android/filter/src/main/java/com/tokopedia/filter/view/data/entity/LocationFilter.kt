package com.tokopedia.filter.view.data.entity

data class LocationFilter(
    var id: Int,
    var cities: List<String>,
    var isSelected: Boolean
) {
    fun getName(): String {
        return if (cities.size == 1) {
            cities.first()
        } else if (cities.isEmpty()) {
            ""
        } else "Others"
    }
}

data class SelectedFilter(
    var location: LocationResult?,
    var price: PriceResult
)

data class LocationResult(
    var filterId: Int,
    var cities: List<String>
)

data class PriceResult (
    var min: Float,
    var max: Float
)