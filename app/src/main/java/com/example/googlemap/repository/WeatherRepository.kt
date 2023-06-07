package com.example.googlemap.repository

import com.bumptech.glide.load.HttpException
import com.example.googlemap.api.RetrofitClient
import com.example.googlemap.model.WeatherResult
import com.example.googlemap.util.ApiResponse
import java.io.IOException

object WeatherRepository {
    suspend fun getWeather(lat: String, lng: String): ApiResponse<WeatherResult> {
        return try {
            val response = RetrofitClient
                .getApi()
                .getWeather(lat, lng)

            ApiResponse.Success(response)
        } catch (e: HttpException) {
            ApiResponse.Error(exception = e)
        } catch (e: IOException) {
            ApiResponse.Error(exception = e)
        }
    }
}