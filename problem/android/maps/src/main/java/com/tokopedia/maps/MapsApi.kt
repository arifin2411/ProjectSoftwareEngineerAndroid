package com.tokopedia.maps

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MapsApi {
    @GET("/name/{country}")
    @Headers("x-rapidapi-host:restcountries-v1.p.rapidapi.com",
            "x-rapidapi-key:f5f6130566mshf81af8d2d9f4deap1df329jsnd994699774dd")
    fun getDataCountry(@Path("country") country :String): Call<List<Maps>>
}