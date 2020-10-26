package com.tokopedia.maps

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


public class ApiClient {
    var retrofit: Retrofit? = null
    val BASE_URL = "https://restcountries-v1.p.rapidapi.com"

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }
}