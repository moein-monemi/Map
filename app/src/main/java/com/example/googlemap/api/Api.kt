package com.example.googlemap.api

import com.example.googlemap.API_KEY
import com.example.googlemap.model.WeatherResult
import com.example.googlemap.util.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") appid: String = API_KEY): WeatherResult
}