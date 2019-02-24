package com.mobiotics.demo.mobioticsapp.retrofit

import com.mobiotics.demo.mobioticsapp.models.RetroVideo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class RetrofitClientInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://interview-e18de.firebaseio.com"

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }

    companion object {
        fun a(): Int = 1
    }
}

interface GetDataService {
    @get:GET("/media.json?print=pretty")
    val allPhotos: Call<List<RetroVideo>>
}

