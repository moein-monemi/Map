package com.example.googlemap.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




object RetrofitClient {


    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: Api = retrofit.create(Api::class.java)

    fun getApi (): Api {
        return api
    }


}