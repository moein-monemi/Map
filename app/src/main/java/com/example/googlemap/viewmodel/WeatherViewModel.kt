package com.example.googlemap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlemap.model.WeatherResult
import com.example.googlemap.repository.WeatherRepository
import com.example.googlemap.util.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private val _weatherResult = MutableLiveData<ApiResponse<WeatherResult>>()
    val weatherResult: LiveData<ApiResponse<WeatherResult>> = _weatherResult

    fun getWeather(lat: String, lng: String) {
        _weatherResult.postValue(ApiResponse.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = WeatherRepository.getWeather(lat, lng)
            _weatherResult.postValue(response)
        }
    }
}